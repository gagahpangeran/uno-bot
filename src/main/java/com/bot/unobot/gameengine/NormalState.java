package com.bot.unobot.gameengine;


import com.bot.unobot.card.Card;
import com.bot.unobot.card.Color;
import com.bot.unobot.card.Effect;
import com.bot.unobot.card.PlusCard;
import com.bot.unobot.player.Player;

import java.util.ArrayList;
import java.util.Arrays;

/*
* Berikut ini gw akan ngasih dokumentasi tentang Class ini
* */

public class NormalState implements GameState {

    GameMaster gameMaster;

    Card lastCard;
    Direction direction;
    int currPlayerIndex;

    public NormalState(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
        this.direction = Direction.CW;
        this.currPlayerIndex = 0;
        lastCard = null;
    }

    @Override
    public void setLastCard(Card lastCard) {
        this.lastCard = lastCard;
    }


    //agak dimodif method isPuttable - nya

    /*
    * @param cards = ArrayList<Card> yang player keluarkan. ini merupakan ArrayList<Card> yang dihasilkan dari method GameMaster.convertStringToCards(ArrayList<sting> card)
    * Jika arraylis kosong, maka kartu yang dikeluarkan tidak valid
    * Jika sebaliknya, maka kumpulan kartu akan diterima
    *
    *
    * */

    public void put(ArrayList<Card> cards) {
        if (!cards.isEmpty()){

            if (this.gameMaster.isPuttable(lastCard, cards) &&
                    this.gameMaster.checkCombo(cards)) {
                this.lastCard = cards.get(cards.size()-1);
                this.gameMaster.addToTrash(cards);

                if (this.gameMaster.getTrashCards().peek().getEffect() == Effect.PLUS) {
                    this.gameMaster.setCurrentState(this.gameMaster.getPlusState());
                    this.gameMaster.getCurrentState().setCurrPlayerIndex(getCurrPlayerIndex());/// mempertahankan catatan currentplayerindex
                    PlusCard tempPlusCard =  (PlusCard) this.gameMaster.getTrashCards().peek();
                    PlusState temp = (PlusState) this.gameMaster.getCurrentState();
                    temp.setNumberOfCombos(tempPlusCard.getPlus());
                    this.gameMaster.setCurrentState(temp);
                }else if (this.gameMaster.getTrashCards().peek().getEffect() == Effect.STOP){
                    currPlayerIndex+=1;
                }else if (this.gameMaster.getTrashCards().peek().getEffect() == Effect.REVERSE){
                    this.direction = this.direction.getOpposite();
                }
                nextTurn();
            }else{
                this.gameMaster.setMessageToGroup(this.gameMaster.putFailed());
            }

        }else{
            this.gameMaster.setMessageToGroup(this.gameMaster.putFailed());
        }
    }


    @Override
    public void giveUp() {

    }
    // gua tambahin
    /*Draw Cards*/
    @Override
    public void draw(){
        if (this.gameMaster.getNewCards().size()<1){
            this.gameMaster.recycleTrashCards();
        }
        this.gameMaster.getPlayers().get(getCurrPlayerIndex()).getCardsCollection().add(this.gameMaster.getNewCards().pop());
        nextTurn();
    }

    @Override
    public void nextTurn() {
        if (direction.equals(Direction.CW)){
            currPlayerIndex = (currPlayerIndex +1)%this.gameMaster.getNrOfPlayers();
        }else{
            currPlayerIndex = (currPlayerIndex -1)%this.gameMaster.getNrOfPlayers();
        }



    }

    @Override
    public Card getLastCard() {
        return lastCard;
    }

    @Override
    public void plus(Card[] cards) {

    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public void wild(Card[] cards) {

    }
    @Override
    public int getCurrPlayerIndex() {
        return currPlayerIndex%this.gameMaster.getPlayers().size();
    }

    //guatambahin
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
        boolean existsAPlayerWhoseCardIsJustOne = false;
        for (int i = 0; i<this.gameMaster.getNrOfPlayers();i++){
            if (this.gameMaster.getPlayers().get(i).getCardsCollection().size()==1){
                existsAPlayerWhoseCardIsJustOne = true;
                if (this.gameMaster.getPlayers().get(i).getId().equals(playerId)){
                    winner = this.gameMaster.getPlayers().get(i);
                }
                idOfTheOneSupposedToWin = this.gameMaster.getPlayers().get(i).getId();
            }
        }
        if (existsAPlayerWhoseCardIsJustOne){establishedWinner(winner, idOfTheOneSupposedToWin);}
        else {this.gameMaster.setMessageToGroup("Belum ada yang UNO bang wkwkwk");}

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
    public void setCurrPlayerIndex(int currPlayerIndex) {
        this.currPlayerIndex = currPlayerIndex;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }


}