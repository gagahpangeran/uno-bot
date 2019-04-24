package com.bot.unobot.gameengine;

import com.bot.unobot.player.Player;
import com.bot.unobot.card.PlusCard;

/**
 * Plus Card State
 * A class for cards that can adds cards to an opponent(+2 and +4) that implements the State Interface.
 */
public class PlusCardState implements State {

    //Variables

    /*
    * Sedikit update:
    *
    * Saya akan menambahkan attribut berupa currentColor
    * currentColor merupakan warna yang diset oleh seseorang yang mengeluarkan kartu plus
    * */
    GameMaster gameMaster;
    String display;
    Player currentPlayer;
    String currentColor;



    /**
     * Plus Card State Constructor
     * @param gameMaster
     */
    public PlusCardState(GameMaster gameMaster){
        this.gameMaster=gameMaster;
        this.display = "";
        this.currentPlayer = null;
        this.currentColor ="";

    }

    /**
     * Get Current Player
     * It returns the current player's ID playing the game.
     * @return current player's ID
     */
    public String getCurrentPlayer(){
        this.currentPlayer = gameMaster.players.get(gameMaster.currentTurn);
        this.display ="Aduh.......\n" +
                "\n" +
                "Kamu kena kartu plus ... :'(\n" +
                "\n" +
                "UNOBot turut berduka cita\n" +
                "\n" +
                "Sebagai bentuk dukacita UNOBot, UNOBot akan secara otomatis mengambilkan kamu kartu dari deck\n" +
                "\n" +
                "Semangat Player!!! " +
                "\n"+
                endTurn();
        return this.currentPlayer.getId();

    }
/*
* Card checking disini bisa berfungsi sebagai implementasi dari effect dari card plus, reverse atau skip
* Di kasus ini, dia menjadi implementasi dari effect dari card plus
*
* */

    /**
     * Card Checking
     * It can be used as an implementation from the effect of wildcards (reverse, adds, and skips).
     * @param userInput
     */
    public void cardChecking(String userInput){
        if (gameMaster.toBeReusedCardStack.peek() instanceof PlusCard){
            PlusCard lastCard = (PlusCard)gameMaster.toBeReusedCardStack.peek();
            for (int i =0;i<lastCard.getPlus();i++){
                takeAnotherCard();
            }
        }
    }

    /**
     * Take Another Card
     * It adds a new card from the card collection to the player's card collection.
     * Then it ends the player's turn directly after taking the card.
     */
    @Override
    public void takeAnotherCard(){
        this.currentPlayer.getCardsCollection().add(this.gameMaster.cardStack.pop());
        this.display = endTurn();
    }

    /**Accept User Card
     * It accepts all kinds of +2 and +4 cards.
     * @param cardName
     * @param cardColor
     */
    @Override
    public void acceptUsersCard(String cardName, String cardColor){
    }

    /**
     * End Turn
     * It returns a message saying that the player's turn has finished.
     * @return
     */
    @Override
    public String endTurn(){
        return "Giliran kamu udah beres!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    }



    @Override
    public void update() {
        this.gameMaster.currentState = this.gameMaster.undeterminedCardState;
        this.gameMaster.currentState.setCurrentColor(currentColor);
    }

    @Override
    public void setCurrentColor(String currentColor) {
        this.currentColor=currentColor;
    }
}
