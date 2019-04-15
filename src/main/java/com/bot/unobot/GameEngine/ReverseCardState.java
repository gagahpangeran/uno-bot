package com.bot.unobot.GameEngine;

import com.bot.unobot.Player.Player;
import com.bot.unobot.TestCards.Card;
import com.bot.unobot.TestCards.PlusCard;

public class ReverseCardState implements State {

    public GameMaster gameMaster;
    public String display;
    public Player current_player;


    public ReverseCardState(GameMaster gameMaster){
        this.gameMaster=gameMaster;
        this.display = "";
        this.current_player = null;
    }

    public String get_current_player(){
        this.current_player = gameMaster.list_of_players.get(gameMaster.current_turn);
        this.display ="Aduhhh....\n" +
                "\n" +
                "Temen Kamu ternyata ngeluarin kartu reverse\n" +
                "\n" +
                "Yang sabar ya..... :)\n" +
                "\n" +
                "Semangat Player!!!\n" +
                "\n" +
                "\n"+
                finished_string();
        return this.current_player.getId();
    }

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
//        String current_color = this.gameMaster.stack_of_want_to_be_reused_cards.peek().getColor();
//        this.gameMaster.current_state = new UndeterminedOrdinaryCardState(current_color,this.gameMaster);
    }

    @Override
    public void take_another_card() {

    }

    @Override
    public void accept_users_card(String card_name, String card_color) {

    }

    @Override
    public void setNextColor(String color) {

    }

    @Override
    public void update(Card cad) {

    }

    @Override
    public void update() {
        String current_color = this.gameMaster.stack_of_want_to_be_reused_cards.peek().getColor();
        this.gameMaster.current_state = new UndeterminedOrdinaryCardState(current_color,this.gameMaster);
    }

    public String finished_string(){
        return "Giliran kamu udah beres!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    };

}
