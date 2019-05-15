package com.bot.unobot.gameengine;


import com.bot.unobot.card.Card;
import com.bot.unobot.card.Color;
import com.bot.unobot.card.Effect;
import com.bot.unobot.player.Player;

import java.util.ArrayList;

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

//            //debug
            System.out.println("is-puttable: "+this.gameMaster.isPuttable(lastCard, cards));
            System.out.println("checkcombo: "+this.gameMaster.checkCombo(cards));


            if (this.gameMaster.isPuttable(lastCard, cards) && this.gameMaster.checkCombo(cards)) {
                ArrayList<Card> tempCards = new ArrayList<>(cards);
                this.lastCard = cards.get(cards.size()-1);
                this.gameMaster.addToTrash(cards);

                //debug


                if (this.gameMaster.getTrashCards().peek().getEffect() == Effect.PLUS) {
                    int tempIndex = getCurrPlayerIndex();
                    PlusState temp = (PlusState) this.gameMaster.getPlusState();
                    temp.setCurrPlayerIndex(tempIndex);
                    this.gameMaster.setCurrentState(temp);
                    this.gameMaster.put(cards);

                }else{
                    if (this.gameMaster.getTrashCards().peek().getEffect() == Effect.STOP){
                        //debug

                        int numberOfSkip = 0;// mengapa pakai ini? Karena bisa jadi dia mengeluarkan Skip card dengan wildcard, jadi kita akan hitung ulang jumlah stop yang dikeluarkan sehingga wildcard tidak dianggap sebagai stop
                        for (Card card:tempCards){
                            if (card.getEffect().equals(Effect.STOP)){
                                numberOfSkip+=1;
                            }
                        }
                        //debug
                        currPlayerIndex+=(1*numberOfSkip);
                        System.out.println(currPlayerIndex);


                    }else if (this.gameMaster.getTrashCards().peek().getEffect() == Effect.REVERSE){
                        int numberOfReverse = 0; // mengapa pakai ini? Karena bisa jadi dia mengeluarkan Reverse card dengan wildcard, jadi kita akan hitung ulang jumlah stop yang dikeluarkan sehingga wildcard tidak dianggap sebagai reverse
                        for (Card card:tempCards){
                            if (card.getEffect().equals(Effect.REVERSE)){
                                numberOfReverse+=1;
                            }
                        }
                        for (int i =0; i<numberOfReverse;i++){
                            this.direction = this.direction.getOpposite();
                        }

                    }
                    //debug
                    System.out.println(gameMaster.getPlayers().get(getCurrPlayerIndex()).getCardsCollection().size());
                    if (gameMaster.getPlayers().get(getCurrPlayerIndex()).getCardsCollection().isEmpty() && gameMaster.getPlayers().get(getCurrPlayerIndex()).isUNO()){
                        gameMaster.setMessageToGroup(gameMaster.putSucceed());
                        establishedWinner(gameMaster.getPlayers().get(getCurrPlayerIndex()), gameMaster.getPlayers().get(getCurrPlayerIndex()).getId());
                    }else{
                        gameMaster.setMessageToPlayer(gameMaster.putSucceed());
                        nextTurn();

                    }
                }

            }else{
                this.gameMaster.setMessageToPlayer(this.gameMaster.putFailed() + "\na");
            }

        }else{
            this.gameMaster.setMessageToPlayer(this.gameMaster.putFailed() + "\nb");
        }
    }




    // gua tambahin
    /*Draw Cards*/
    @Override
    public String draw(String playerId) {
        System.out.println("Masuk sini");
        Player currentPlayerWhoPlays = gameMaster.getPlayers().get(getCurrPlayerIndex());
        if (currentPlayerWhoPlays.getId().equals(playerId)){
            if (this.gameMaster.getNewCards().size()<1){
                this.gameMaster.recycleTrashCards();
            }

            Card newCard = this.gameMaster.getNewCards().pop();

            this.gameMaster.getPlayers().get(getCurrPlayerIndex()).getCardsCollection().add(newCard);
            nextTurn();
            return "Anda menarik kartu " + newCard.getSymbol() + " " + newCard.getColor();
        } else{
            return "Sekarang bukan giliran anda";
        }
    }

    @Override
    public void nextTurn() {
        if (direction.equals(Direction.CW)){
            currPlayerIndex = Math.floorMod(currPlayerIndex +1,this.gameMaster.getNrOfPlayers());
        }else{
            currPlayerIndex = Math.floorMod(currPlayerIndex -1,this.gameMaster.getNrOfPlayers());
        }



    }

    @Override
    public Card getLastCard() {
        return lastCard;
    }


    @Override
    public int getCurrPlayerIndex() {
        return Math.floorMod(currPlayerIndex, gameMaster.getNrOfPlayers());
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
            if (this.gameMaster.getPlayers().get(i).getCardsCollection().size()<=1 && !this.gameMaster.getPlayers().get(i).isUNO() ){
                existsAPlayerWhoseCardIsJustOne = true;
                if (this.gameMaster.getPlayers().get(i).getId().equals(playerId)){
                    winner = this.gameMaster.getPlayers().get(i);
                }
                idOfTheOneSupposedToWin = this.gameMaster.getPlayers().get(i).getId();
            }
        }
        if (existsAPlayerWhoseCardIsJustOne && !gameMaster.getSpecificPlayer(playerId).isUNO()){establishedWinner(winner, idOfTheOneSupposedToWin);}
        else if ( existsAPlayerWhoseCardIsJustOne && gameMaster.getSpecificPlayer(playerId).isUNO()){
            this.gameMaster.setMessageToGroup("Pemain "+playerId+" sudah aman woy wkwkwk");
        }
        else {this.gameMaster.setMessageToGroup("Belum ada yang UNO bang wkwkwk");}

    }

    /*
    * @param player = Player yang dihasilkan oleh method checkAndGetWinner ( Player winner pada method ini)
    * @playerIDWhoSupposedToWin = pemain yang benar benar kartunya tinggal 1.
    * Jika player null, maka ya pemain tersebut musti ambil 2 kartu karena dia telat bilang uno
    * Else:
    * - Pemain dianggap aman
    * -  Jika kartu pemain benar- benar tinggal nol, maka:
    * 1. Dia jadi juara dan di-kick dari permainan
    * 2. update posisi juara yang diperebutkan. Misal juara 1 udah ada, yaudah berarti diupdate jad 2 dst....
    *
    *
    *
    *
    * */
    @Override
    public void establishedWinner(Player player, String playerIdWhoSupposedToWin){
        if (player == null){
//debug
            System.out.println("zzzzzzz");

            for (int i=0;i<2;i++){
                if (gameMaster.getNewCards().isEmpty()) gameMaster.recycleTrashCards();
                this.gameMaster.getSpecificPlayer(playerIdWhoSupposedToWin).getCardsCollection().add(gameMaster.getNewCards().pop());
            }

//debug
            System.out.println(gameMaster.getSpecificPlayer(playerIdWhoSupposedToWin).getCardsCollection().size());
            this.gameMaster.getSpecificPlayer(playerIdWhoSupposedToWin).setUNO(false);
            this.gameMaster.setMessageToGroup(this.gameMaster.failedToWin(gameMaster.getSpecificPlayer(playerIdWhoSupposedToWin).getName()));
        }else{
            if (player.getCardsCollection().isEmpty()){
                this.gameMaster.setMessageToGroup(this.gameMaster.winnerString(gameMaster.getSpecificPlayer(player.getId()).getName()));
                this.gameMaster.getPlayers().remove(player);
                this.gameMaster.setChampionPosition(this.gameMaster.getChampionPosition()+1);


            }else{
                this.gameMaster.getSpecificPlayer(playerIdWhoSupposedToWin).setUNO(true);
                this.gameMaster.setMessageToGroup(gameMaster.unoSafeString(gameMaster.getSpecificPlayer(playerIdWhoSupposedToWin).getName()));
            }

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