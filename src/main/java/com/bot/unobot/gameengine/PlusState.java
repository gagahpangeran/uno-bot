package com.bot.unobot.gameengine;


import com.bot.unobot.card.Card;
import com.bot.unobot.card.Color;
import com.bot.unobot.card.Effect;
import com.bot.unobot.card.PlusCard;
import com.bot.unobot.player.Player;

import java.util.ArrayList;

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
    public void wild(Card[] cards) {

    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public int getCurrPlayerIndex() {
        return currPlayerIndex%this.gameMaster.getNrOfPlayers();
    }

    @Override
    public void plus(Card[] cards) {

    }

    @Override
    public Card getLastCard() {
        return lastCard;
    }

    @Override
    public void put(ArrayList<Card> cards) {
        if (!cards.isEmpty()){

            if (isPuttableForPlusCards(cards) &&
                    this.gameMaster.checkCombo(cards)) {
                this.lastCard = cards.get(cards.size()-1);
                this.gameMaster.addToTrash(cards); /// ketika di add to trash maka dia dikeluarin dari kartu pemain
                this.numberOfCombos+=countCombos(cards);
                nextTurn();
                this.gameMaster.setMessageToGroup(this.gameMaster.putSucceed());
            }else{
                this.gameMaster.setMessageToGroup(this.gameMaster.putFailed());
            }
            nextTurn();

        }else{
            this.gameMaster.setMessageToGroup(this.gameMaster.putFailed());
        }

    }

    public boolean isPuttableForPlusCards(ArrayList<Card> cards){
        for (Card card: cards){
            if (card.getEffect() != Effect.PLUS){
                return false;
            }
        }
        return true;
    }

    public int countCombos(ArrayList<Card> cards){
        int numberOfCombos = 0;
        for (Card card:cards){
            if (card instanceof PlusCard){
                PlusCard temp = (PlusCard) card;
                numberOfCombos+= temp.getPlus();
            }
        }
        return numberOfCombos;
    }

    public void drawCardsForVictim(){



    }

    @Override
    public void giveUp() {

    }

    @Override
    public void nextTurn() {
        if (direction.equals(Direction.CW)){
            currPlayerIndex = (currPlayerIndex +1)%this.gameMaster.getNrOfPlayers();
        }else{
            currPlayerIndex = (currPlayerIndex -1)%this.gameMaster.getNrOfPlayers();
        }
        //this.gameMaster.setMessageToGroup(this.gameMaster.nextTurnString());

    }

    @Override
    public void draw() {
        for ( int i = 0; i<numberOfCombos;i++){
            if (this.gameMaster.getNewCards().size()<1){
                this.gameMaster.recycleTrashCards();
            }
            this.gameMaster.getPlayers().get(getCurrPlayerIndex()).getCardsCollection().add(this.gameMaster.getNewCards().pop());
        }
        nextTurn();


    }

    public int getNumberOfCombos() {
        return numberOfCombos;
    }

    public void setNumberOfCombos(int numberOfCombos) {
        this.numberOfCombos = numberOfCombos;
    }

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

    @Override
    public void establishedWinner(Player player, String playerIdWhoSupposedToWin){
        if (player.equals(null)){
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
