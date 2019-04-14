package com.bot.unobot.GameEngine;

import com.bot.unobot.Player.Player;
import com.bot.unobot.TestCards.Card;
import com.bot.unobot.TestCards.PlusCard;
import com.sun.org.apache.xpath.internal.operations.Plus;

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
    public String get_current_player(){
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
                end_turn();
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
    public void card_checking(String user_input){
        if (gameMaster.stack_of_want_to_be_reused_cards.peek() instanceof PlusCard){
            PlusCard last_card = (PlusCard)gameMaster.stack_of_want_to_be_reused_cards.peek();
            for (int i =0;i<last_card.getPlus();i++){
                take_another_card();
            }
        }
    }

    /**
     * Take Another Card
     * It adds a new card from the card collection to the player's card collection.
     * Then it ends the player's turn directly after taking the card.
     */
    @Override
    public void take_another_card(){
        this.current_player.getCards_collection().add(this.gameMaster.stack_of_cards.pop());
        this.display = end_turn();
    }

    /**Accept User Card
     * It accepts all kinds of +2 and +4 cards.
     * @param card_name
     * @param card_color
     */
    @Override
    public void accept_users_card(String card_name, String card_color){
    }

    /**
     * End Turn
     * It returns a message saying that the player's turn has finished.
     * @return
     */
    @Override
    public String end_turn(){
        return "Giliran kamu udah beres!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    }
}
