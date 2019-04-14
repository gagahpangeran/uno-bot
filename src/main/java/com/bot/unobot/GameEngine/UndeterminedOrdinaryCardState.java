package com.bot.unobot.GameEngine;

import com.bot.unobot.Player.Player;
import com.bot.unobot.TestCards.Card;

public class UndeterminedOrdinaryCardState implements State {

    String warna;
    String display;
    Player current_player;
    GameMaster gameMaster;

    public UndeterminedOrdinaryCardState ( ){}

    public UndeterminedOrdinaryCardState ( String warna, GameMaster gameMaster){
        this.warna = warna;
        this.gameMaster=gameMaster;
        this.display = "";
        this.current_player = null;
    }

    @Override
    public String get_current_player() {
        this.current_player = gameMaster.list_of_players.get(gameMaster.current_turn); // mungkin ini nanti masih diedit lagi, apakah di - incrementnya di sini atau di engine
        this.display =
                "Selamat! Kamu Dapat Rejeki!\n" +
                        "\n" +
                        "Salah satu teman kamu barusan ngeluarin kartu reverse atau skip\n" +
                        "\n" +
                        "Kini giliran Anda untuk bermain \n" +
                        "\n" +
                        "Kartu Anda adalah sebagai berikut:\n" +
                        "\n"+
                        this.current_player.shows_players_cards();
        return this.current_player.getId();
    }

    @Override
    public void card_checking(String user_input) {
        String[] temp = user_input.split(" ");
        String card_name =  temp[0];
        String card_color = temp[1];
        String last_color = gameMaster.stack_of_want_to_be_reused_cards.peek().getColor();
        if (card_color.equals(last_color)){
            accept_users_card(card_name, card_color);
            this.display = "Nice Move !!!!\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    finished_string();
        }
        else{
            this.display ="Maaf, Kartu yang kamu keluarin gak cocok\n" +
                    "\n" +
                    "Silahkan ketik .ambil untuk mengambil dari deck";
        }

    }

    @Override
    public void accept_users_card(String card_name, String card_color) {
        Card removal_target = null;
        for (Card cards : this.current_player.getCards_collection()){
            if (cards.getColor().equals(card_color) && cards.getName().equals(card_name)){
                removal_target = cards;
            }
        }
        this.gameMaster.stack_of_want_to_be_reused_cards.push(removal_target);
        this.current_player.getCards_collection().remove(removal_target);
    }

    @Override
    public void take_another_card() {
        this.current_player.getCards_collection().add(this.gameMaster.stack_of_cards.pop());
//        this.current_player.getCards_collection().add(this.gameMaster.stack_of_cards.get(this.gameMaster.stack_of_cards.size()-1));
//        this.gameMaster.stack_of_cards.remove(this.gameMaster.stack_of_cards.get(this.gameMaster.stack_of_cards.size()-1));
        this.display = finished_string();
    }

    public String finished_string(){
        return "Giliran kamu udah beres!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    };

    @Override
    public void setNextColor(String color) {

    }

    @Override
    public void update() {

    }

    @Override
    public void update(Card cad) {

    }
}
