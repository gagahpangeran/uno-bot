package com.bot.unobot.gameengine;

import com.bot.unobot.card.Color;
import com.bot.unobot.player.Player;

/**
 * Reverse Card State
 * A class for reverse cards that implements the State Interface.
 */
public class ReverseCardState implements State {

    //Variables
    GameMaster gameMaster;
    String display;
    Player currentPlayer;
    Color currentColor;

    /**
     * Reverse Card State Constructor
     * @param gameMaster
     */
    public ReverseCardState(GameMaster gameMaster){
        this.gameMaster=gameMaster;
        this.display = "";
        this.currentPlayer = null;
        this.currentColor = null;
    }

    /**
     * Get Current Player
     * It returns the current player's ID playing the game.
     * @return
     */
    @Override
    public String getCurrentPlayer(){
        this.currentPlayer = gameMaster.players.get(gameMaster.currentTurn%gameMaster.playerSize);
        this.display ="Aduhhh....\n" +
                "\n" +
                "Temen Anda ternyata mengeluarkan kartu reverse\n" +
                "\n" +
                "Yang sabar ya..... :)\n" +
                "\n" +
                "Semangat Player!!!\n" +
                "\n" +
                "\n"+
                endTurn();
        return this.currentPlayer.getId();
    }

    /**
     * Card Checking
     * It overrides the card Checking method in the State Interface.
     * It checks the card the player select with the previous card in the stack.
     * If the card has the same color or number, it will be accepted. If not, an
     * error message will be returned.
     * @param userInput
     */
    @Override
    public void cardChecking(String userInput){
        /*
        * Disini saya akan mengeksekusi effect dari karu ini
        *
        * Effect state ini:
        * a. mengganti currentTurn menjadi currentTurn-1
        * b. Men- set next state menjadi UndeterminedOrdinaryCardState
        *
        *
        * */
       /*
       Semetara, blok kodingan ini dicomment dulu
       this.gameMaster.currentTurn -=2;
        if (this.gameMaster.currentTurn <0){
            this.gameMaster.currentTurn += this.gameMaster.playerSize;
        }
        currentColor = this.gameMaster.toBeReusedCardStack.peek().getColor();
//        this.gameMaster.currentState = new UndeterminedOrdinaryCardState(currentColor);*/
    }

    /**
     * Take Another Card
     * It adds a new card from the card collection to the player's card collection.
     * Then it ends the player's turn directly after taking the card.
     */
    @Override
    public void takeAnotherCard() {
    }

    /**
     * Accept User Card
     *
     * @param cardName
     * @param cardColor
     */
    @Override
    public void acceptUsersCard(String cardName, Color cardColor) {

    }

    /**
     * End Turn
     * It returns a message saying that the player's turn has finished.
     * @return
     */
    @Override
    public String endTurn(){
        return "Giliran Anda sudah selesai!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    }

    @Override
    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;

    }

    @Override
    public void update() {
        this.gameMaster.currentTurn -=2;
        if (this.gameMaster.currentTurn <0){
            this.gameMaster.currentTurn += this.gameMaster.playerSize;
        }
        currentColor = this.gameMaster.toBeReusedCardStack.peek().getColor();
        this.gameMaster.currentState = this.gameMaster.undeterminedCardState;
        this.gameMaster.currentState.setCurrentColor(currentColor);


    }
}
