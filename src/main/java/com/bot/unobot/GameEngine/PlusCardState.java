package com.bot.unobot.GameEngine;

import com.bot.unobot.Player.Player;
import com.bot.unobot.TestCards.Card;
import com.bot.unobot.TestCards.PlusCard;
import com.sun.org.apache.xpath.internal.operations.Plus;

public class PlusCardState implements State {

    GameMaster gameMaster;
    String display;
    Player current_player;

    public PlusCardState(GameMaster gameMaster){
        this.gameMaster=gameMaster;
        this.display = "";
        this.current_player = null;
    }

    public String get_current_player(){
        this.current_player = gameMaster.list_of_players.get(gameMaster.current_turn);
        this.display ="Aduh.......\n" +
                "\n" +
                "Kamu ternyata kena kartu plus ... :'(\n" +
                "\n" +
                "UNOBot turut berduka cita\n" +
                "\n" +
                "Sebagai bentuk dukacita UNOBot, UNOBot akan secara otomatis mengambilkan kamu kartu dari deck\n" +
                "\n" +
                "Semangat Player!!! " +
                "\n"+
                finished_string();
        return this.current_player.getId();

    }
/*
* Card checking disini bisa berfungsi sebagai implementasi dari effect dari card plus, reverse atau skip
* Di kasus ini, dia menjadi implementasi dari effect dari card plus
*
* */
    public void card_checking(String user_input){
        if (gameMaster.stack_of_want_to_be_reused_cards.peek() instanceof PlusCard){
            PlusCard last_card = (PlusCard)gameMaster.stack_of_want_to_be_reused_cards.peek();
            for (int i =0;i<last_card.getPlus();i++){
                take_another_card();
            }

        }

    };

    public void take_another_card(){
        this.current_player.getCards_collection().add(this.gameMaster.stack_of_cards.pop());
        this.display = finished_string();
    };

    public void accept_users_card(String card_name, String card_color){

    };

    public String finished_string(){
        return "Giliran kamu udah beres!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    };


}
