package com.bot.unobot.gameengine;


import com.bot.unobot.card.Card;
import com.bot.unobot.card.Color;
import com.bot.unobot.card.Effect;
import com.bot.unobot.card.PlusCard;
import com.bot.unobot.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlusState implements GameState {

    GameMaster gameMaster;

    Card lastCard;
    Direction direction;
    int currPlayerIndex;
    int numberOfCombos;
    //addnumberofcombos


    public PlusState(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
        this.direction = Direction.CW;
        this.currPlayerIndex = 0;
        lastCard = null;
        this.numberOfCombos = 0;
    }

    @Override
    public void setLastCard(Card lastCard) {
        this.lastCard = lastCard;
    }

    @Override
    public void setCurrPlayerIndex(int currPlayerIndex) {
        this.currPlayerIndex = currPlayerIndex;
    }


    @Override
    public int getCurrPlayerIndex() {
        return Math.floorMod(currPlayerIndex, gameMaster.getNrOfPlayers());
    }



    @Override
    public Card getLastCard() {
        return lastCard;
    }

    @Override

    /*
     * @param cards = ArrayList<Card> yang player keluarkan. ini merupakan ArrayList<Card> yang dihasilkan dari method GameMaster.convertStringToCards(ArrayList<sting> card)
     * Jika arraylis kosong, maka kartu yang dikeluarkan tidak valid
     * Jika sebaliknya, maka kumpulan kartu akan diterima
     *
     *
     * */


    public void put(ArrayList<Card> cards) {
        if (!cards.isEmpty()){

//            //debug
//            System.out.println("x is-puttable: "+isPuttableForPlusCards(cards));
//            System.out.println(" x checkcombo: "+this.gameMaster.isPuttable(lastCard, cards));

            if (isPuttableForPlusCards(cards) && this.gameMaster.checkCombo((ArrayList<Card>) cards)) {
                this.lastCard = cards.get(cards.size()-1);
                this.gameMaster.addToTrash(cards); /// ketika di add to trash maka dia dikeluarin dari kartu pemain
                this.numberOfCombos+=countCombos(cards);
                //debug
                System.out.println("last card: "+lastCard.getSymbol()+" "+lastCard.getColor());
                this.gameMaster.setMessageToGroup(this.gameMaster.putSucceed());
                nextTurn();
            }else{
                this.gameMaster.setMessageToGroup(this.gameMaster.putFailed());
            }


        }else{
            this.gameMaster.setMessageToGroup(this.gameMaster.putFailed());
        }

    }

    /*
    * IsPuttablenya puat plus state agak beda, karena hanya kartu plus saja yang diterima, selain itu ya dia gak terima
    *
    * */

    public boolean isPuttableForPlusCards(ArrayList<Card> cards){
        for (Card card: cards){
            if (card.getEffect() != Effect.PLUS){
                return false;
            }
        }
        return true;
    }

    public int countCombos(ArrayList<Card> cards){
        int noOfCombos = 0;
        for (Card card:cards){
            if (card instanceof PlusCard){
                PlusCard temp = (PlusCard) card;
                noOfCombos+= temp.getPlus();
            }
        }
        return noOfCombos;
    }

    public void drawCardsForVictim(){



    }



    @Override
    public void nextTurn() {
        if (direction.equals(Direction.CW)){
            currPlayerIndex = Math.floorMod(currPlayerIndex +1,this.gameMaster.getNrOfPlayers());
        }else{
            currPlayerIndex = Math.floorMod(currPlayerIndex -1,this.gameMaster.getNrOfPlayers());
        }
    }

    /*
    * Draw kartu bagi korban :X
    *
    * */

    @Override
    public String draw(String playerId) {
        for ( int i = 0; i<numberOfCombos;i++){
            if (this.gameMaster.getNewCards().isEmpty()){
                this.gameMaster.recycleTrashCards();
            }
            this.gameMaster.getPlayers().get(getCurrPlayerIndex()).getCardsCollection().add(this.gameMaster.getNewCards().pop());
        }
        numberOfCombos = 0;
        this.gameMaster.setCurrentState(gameMaster.getNormalState());
        this.gameMaster.getCurrentState().setCurrPlayerIndex(Math.floorMod(getCurrPlayerIndex()+1, this.gameMaster.getNrOfPlayers()));
        this.gameMaster.getCurrentState().setLastCard(this.gameMaster.getTrashCards().peek());

        //nextTurn();
        return "hehe";
    }

    public int getNumberOfCombos() {
        return numberOfCombos;
    }

    public void setNumberOfCombos(int numberOfCombos) {
        this.numberOfCombos = numberOfCombos;
    }

    /*
     * @param playerId = id player yang bilang UNo
     * Jadi cara kerja method ini adalah:
     * 1. Pertama dicek apakah dari semua pemain ada yang benar2 kartunya cuma 1
     * 2. Misalnya udah ketemu pemain yang kartunya cuma 1, dicek apakah idnya dia sama kayak id yang bilang uno
     * 3. Jika iya maka winner di set menjadi pemain tersebut, else di set null
     * 4. Diceknya menggunakan method establishedWinner
     * Jika ada yang bilaing UNO, namun belum ada yang kartunya tinggal 1, maka akan dikasih tau kalau belum ada yang UNO
     * */

    @Override
    public void checkAndGetWinner (String playerId){
        Player winner =  null;
        String idOfTheOneSupposedToWin = null;
        for (int i = 0; i<this.gameMaster.getNrOfPlayers();i++){
            if (this.gameMaster.getPlayers().get(i).getCardsCollection().size()==1){
                if (this.gameMaster.getPlayers().get(i).getId().equals(playerId)){
                    winner = this.gameMaster.getPlayers().get(i);
                }
                idOfTheOneSupposedToWin = this.gameMaster.getPlayers().get(i).getId();
            }
        }
        establishedWinner(winner, idOfTheOneSupposedToWin);

    }

    /*
     * @param player = Player yang dihasilkan oleh method checkAndGetWinner ( Player winner pada method ini)
     * @playerIDWhoSupposedToWin = pemain yang benar benar kartunya tinggal 1.
     * Jika player null, maka ya pemain tersebut musti ambil 2 kartu karena dia telat bilang uno
     * Else:
     * - Kick pemain dari grup
     * - update posisi juara yang diperebutkan. Misal juara 1 udah ada, yaudah berarti diupdate jad 2 dst....
     *
     *
     *
     *
     * */

    @Override
    public void establishedWinner(Player player, String playerIdWhoSupposedToWin){
        if (player == null){
            this.gameMaster.setMessageToGroup(this.gameMaster.failedToWin(playerIdWhoSupposedToWin));
        }else{
            this.gameMaster.setMessageToGroup(this.gameMaster.winnerString(player.getId()));
            this.gameMaster.getPlayers().remove(player);
            this.gameMaster.setChampionPosition(this.gameMaster.getChampionPosition()+1);
        }
    }

    @Override
    public Direction getDirection() {
        return direction;
    }


}
