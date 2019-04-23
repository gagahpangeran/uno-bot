package com.bot.unobot.gameengine;

import com.bot.unobot.player.Player;
import com.bot.unobot.card.Card;

/**
 * Ordinary Card State
 * A class for ordinary cards (0, 1, 2, 3, 4, .. ,9) that implements the State Interface.
 */
public class OrdinaryCardState implements State {

    //Variables
    GameMaster gameMaster;
    String display;
    Player currentPlayer;

    /**
     * Ordinary Card State Constructor
     */

    public OrdinaryCardState(GameMaster gameMaster){
        this.gameMaster=gameMaster;
        this.display = "";
        this.currentPlayer = null;
    }

    /**
     * Get Current Player
     * It returns the current player's ID playing the game.
     * @return
     */
    @Override
    public String getCurrentPlayer(){
        this.currentPlayer = gameMaster.players.get(gameMaster.currentTurn); // mungkin ini nanti masih diedit lagi, apakah di - incrementnya di sini atau di engine
        this.display =
                "Sekarang giliran Anda untuk bermain.\n" +
                "\n" +
                "Kartu Anda adalah sebagai berikut: \n"+
                        this.currentPlayer.showsPlayersCards();
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
        String[] temp = userInput.split(" ");
        String cardName =  temp[0];
        String cardColor = temp[1];
        Card lastCard = gameMaster.toBeReusedCardStack.peek();

        if (cardColor.equals(lastCard.getColor())&&cardName.equals(lastCard.getName())){
            acceptUsersCard(cardName, cardColor);
        }
        else  if (cardColor.equals("Black")){
            acceptUsersCard(cardName, "Black");
        }
        else{
            this.display ="Maaf, Kartu yang Anda keluarkan tidak cocok\n" +
                    "\n" +
                    "Silahkan ketik .ambil untuk mengambil kartu baru dari deck";
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

    /**
     * Accept User Card
     *
     * @param cardName
     * @param cardColor
     */
    public void acceptUsersCard(String cardName, String cardColor){
        Card removalTarget = null;
        for (Card cards : this.currentPlayer.getCardsCollection()){
            if (cards.getColor().equals(cardColor) && cards.getName().equals(cardName)){
                removalTarget = cards;
            }
        }
        this.gameMaster.toBeReusedCardStack.push(removalTarget);
        this.currentPlayer.getCardsCollection().remove(removalTarget);
        this.display = "Nice Move !!!!\n" +
                "\n" +
                "\n" +
                "\n" +
                endTurn();

    }

    /**
     * End Turn
     * It returns a message saying that the player's turn has finished.
     * @return
     */
    @Override
    public String endTurn(){
        return "Giliran kamu sudah selesai!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    }
}
