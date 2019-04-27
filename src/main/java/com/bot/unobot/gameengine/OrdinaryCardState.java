package com.bot.unobot.gameengine;

import com.bot.unobot.card.*;
import com.bot.unobot.player.Player;

/**
 * Ordinary Card State
 * A class for ordinary cards (0, 1, 2, 3, 4, .. ,9) that implements the State Interface.
 */
public class OrdinaryCardState implements State {

    //Variables
    GameMaster gameMaster;
    String display;
    Player currentPlayer;
    Color currentColor;

    /**
     * Ordinary Card State Constructor
     */

    public OrdinaryCardState(GameMaster gameMaster){
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
        Color cardColor = Color.SPECIAL; // TODO: covert user input string to Color enum
        Card lastCard = gameMaster.toBeReusedCardStack.peek();

        if (cardColor.equals(lastCard.getColor())&&cardName.equals(lastCard.getName())){
            acceptUsersCard(cardName, cardColor);
        }
        else  if (cardColor.equals(Color.SPECIAL)){
            acceptUsersCard(cardName, Color.SPECIAL);
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
    public void acceptUsersCard(String cardName, Color cardColor){
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

    @Override
    /*
    * How does update() work?
    *
    * algonya adalah dia akan melihat top of stack dari tobereusedcard
    * dari situ dia akan beraksi, if branching berdasarkan kondisi top of stack
    *
    * tapi bagaimana kalau dia uno state?
    * 1. Update dulu
    * 2. Abis diupdate cek apakah dia UNO dan ada player yang di phase UNO atau tidak
    * 3. Ikutin snippet code di bawah ini
    *
    *
    * current_state.update()
    if (UNO && playerInUNOState){
    UNOState unostate = new UNOState(this,player,current_state)
    currentstate = UNOstate
    }
    *
    * */
    public void update() {
        Card cardOnTopOfStack = this.gameMaster.toBeReusedCardStack.peek();
        if (cardOnTopOfStack instanceof OrdinaryCard){
            this.gameMaster.currentState = this.gameMaster.ordinaryCardState;
        }else if (cardOnTopOfStack instanceof PlusCard){
            this.gameMaster.currentState =  this.gameMaster.plusCardState;
        }else if (cardOnTopOfStack instanceof ReverseCard){
            this.gameMaster.currentState = this.gameMaster.reverseCardState;
        }else if (cardOnTopOfStack instanceof SkipCard){
            this.gameMaster.currentState = this.gameMaster.skipCardState;
        }
        this.gameMaster.currentTurn+=1;

    }

    @Override
    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }
}
