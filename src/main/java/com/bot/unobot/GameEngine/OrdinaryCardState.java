package com.bot.unobot.GameEngine;

import com.bot.unobot.Player.Player;
import com.bot.unobot.TestCards.*;

public class OrdinaryCardState implements State {

    public GameMaster gameMaster;
    public String display;
    public Player current_player;
    public Card card_placed_by_player;
    public String color_set_by_player= "";

    public OrdinaryCardState (){

    }

    public OrdinaryCardState(GameMaster gameMaster){
        this.gameMaster=gameMaster;
        this.display = "";
        this.current_player = null;
        this.card_placed_by_player = null;
        this.color_set_by_player= "";
    }


    public String get_current_player(){
        this.current_player = gameMaster.list_of_players.get(gameMaster.current_turn); // mungkin ini nanti masih diedit lagi, apakah di - incrementnya di sini atau di engine
        this.display =
                "Kini giliran Anda untuk bermain :\n" +
                "\n" +
                "Kartu Anda adalah sebagai berikut: \n"+
                        this.current_player.shows_players_cards();
        return this.current_player.getId();

    }

    public void card_checking(String user_input){
        String[] temp = user_input.split(" ");
        String card_name =  temp[0];
        String card_color = temp[1];
        Card last_card = gameMaster.stack_of_want_to_be_reused_cards.peek();

        if (card_color.equals(last_card.getColor())&&card_name.equals(last_card.getName())){
            accept_users_card(card_name, card_color);

        }
        else  if (card_color.equals("Black")){
            accept_users_card(card_name, "Black");
        }
        else{
            this.display ="Maaf, Kartu yang kamu keluarin gak cocok\n" +
                    "\n" +
                    "Silahkan ketik .ambil untuk mengambil dari deck";
        }


    };





    public void take_another_card(){
        this.current_player.getCards_collection().add(this.gameMaster.stack_of_cards.pop());
        this.display = finished_string();
    };

    public void accept_users_card(String card_name, String card_color){
        Card removal_target = null;
        for (Card cards : this.current_player.getCards_collection()){
            if (cards.getColor().equals(card_color) && cards.getName().equals(card_name)){
                removal_target = cards;
            }
        }
        this.gameMaster.stack_of_want_to_be_reused_cards.push(removal_target);
        this.card_placed_by_player = removal_target;
        this.current_player.getCards_collection().remove(removal_target);
        this.display = "Nice Move !!!!\n" +
                "\n" +
                "\n" +
                "\n" +
                finished_string();

    };

    public String finished_string(){
        return "Giliran kamu udah beres!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    };

    @Override
    public void update() {
        if(this.card_placed_by_player instanceof OrdinaryCard){
            this.gameMaster.current_state = new OrdinaryCardState(this.gameMaster);


        }
        else if(this.card_placed_by_player instanceof PlusCard){
            this.gameMaster.current_state =  new PlusCardState(this.gameMaster);
            if(this.card_placed_by_player.getColor().equals("Black")){
                this.gameMaster.current_state.setNextColor(this.color_set_by_player);
            }else{
                this.gameMaster.current_state.setNextColor(this.gameMaster.stack_of_want_to_be_reused_cards.peek().getColor());
            }

        }
        else if (this.card_placed_by_player instanceof ReverseCard){
            this.gameMaster.current_state = new ReverseCardState(this.gameMaster);
        }
        else if (this.card_placed_by_player instanceof SkipCard){
            this.gameMaster.current_state =  new SkipCardState(this.gameMaster);
        }
        this.gameMaster.current_turn+=1;

    }

    @Override
    public void setNextColor(String color) {
        this.color_set_by_player = color;
    }

    @Override
    public void update(Card cad) {



    }


}