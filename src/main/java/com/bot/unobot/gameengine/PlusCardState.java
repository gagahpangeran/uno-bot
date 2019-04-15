package com.bot.unobot.gameengine;

import com.bot.unobot.player.Player;
import com.bot.unobot.card.PlusCard;

/**
 * Plus Card State
 * A class for cards that can adds cards to an opponent(+2 and +4) that implements the State Interface.
 */
public class PlusCardState implements State {

    //Variables
    GameMaster gameMaster;
    String display;
    Player current_player;

    /**
     * Plus Card State Constructor
     * @param gameMaster
     */
    public PlusCardState(GameMaster gameMaster){
        this.gameMaster=gameMaster;
        this.display = "";
        this.current_player = null;
    }

    /**
     * Get Current Player
     * It returns the current player's ID playing the game.
     * @return current player's ID
     */
    public String getCurrentPlayer(){
        this.current_player = gameMaster.list_of_players.get(gameMaster.current_turn);
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
        return this.current_player.getId();

    }
/*
* Card checking disini bisa berfungsi sebagai implementasi dari effect dari card plus, reverse atau skip
* Di kasus ini, dia menjadi implementasi dari effect dari card plus
*
* */

    /**
     * Card Checking
     * It can be used as an implementation from the effect of wildcards (reverse, adds, and skips).
     * @param user_input
     */
    public void cardChecking(String user_input){
        if (gameMaster.stack_of_want_to_be_reused_cards.peek() instanceof PlusCard){
            PlusCard last_card = (PlusCard)gameMaster.stack_of_want_to_be_reused_cards.peek();
            for (int i =0;i<last_card.getPlus();i++){
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
        this.current_player.getCardsCollection().add(this.gameMaster.stack_of_cards.pop());
        this.display = endTurn();
    }

    /**Accept User Card
     * It accepts all kinds of +2 and +4 cards.
     * @param card_name
     * @param card_color
     */
    @Override
    public void acceptUsersCard(String card_name, String card_color){
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
}
