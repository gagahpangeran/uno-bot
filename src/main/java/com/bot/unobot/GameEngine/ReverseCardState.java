package com.bot.unobot.GameEngine;

import com.bot.unobot.Player.Player;
import com.bot.unobot.TestCards.PlusCard;

/**
 * Reverse Card State
 * A class for reverse cards that implements the State Interface.
 */
public class ReverseCardState implements State {

    //Variables
    GameMaster gameMaster;
    String display;
    Player current_player;

    /**
     * Reverse Card State Constructor
     * @param gameMaster
     */
    public ReverseCardState(GameMaster gameMaster){
        this.gameMaster=gameMaster;
        this.display = "";
        this.current_player = null;
    }

    /**
     * Get Current Player
     * It returns the current player's ID playing the game.
     * @return
     */
    @Override
    public String get_current_player(){
        this.current_player = gameMaster.list_of_players.get(gameMaster.current_turn);
        this.display ="Aduhhh....\n" +
                "\n" +
                "Temen Anda ternyata mengeluarkan kartu reverse\n" +
                "\n" +
                "Yang sabar ya..... :)\n" +
                "\n" +
                "Semangat Player!!!\n" +
                "\n" +
                "\n"+
                end_turn();
        return this.current_player.getId();
    }

    /**
     * Card Checking
     * It overrides the card Checking method in the State Interface.
     * It checks the card the player select with the previous card in the stack.
     * If the card has the same color or number, it will be accepted. If not, an
     * error message will be returned.
     * @param user_input
     */
    @Override
    public void card_checking(String user_input){
        /*
        * Disini saya akan mengeksekusi effect dari karu ini
        *
        * Effect state ini:
        * a. mengganti current_turn menjadi current_turn-1
        * b. Men- set next state menjadi UndeterminedOrdinaryCardState
        *
        *
        * */
        this.gameMaster.current_turn -=2;
        if (this.gameMaster.current_turn<0){
            this.gameMaster.current_turn += this.gameMaster.player_size;
        }
        String current_color = this.gameMaster.stack_of_want_to_be_reused_cards.peek().getColor();
        this.gameMaster.current_state = new UndeterminedOrdinaryCardState(current_color);
    }

    /**
     * Take Another Card
     * It adds a new card from the card collection to the player's card collection.
     * Then it ends the player's turn directly after taking the card.
     */
    @Override
    public void take_another_card() {
    }

    /**
     * Accept User Card
     *
     * @param card_name
     * @param card_color
     */
    @Override
    public void accept_users_card(String card_name, String card_color) {

    }

    /**
     * End Turn
     * It returns a message saying that the player's turn has finished.
     * @return
     */
    @Override
    public String end_turn(){
        return "Giliran Anda sudah selesai!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    }
}
