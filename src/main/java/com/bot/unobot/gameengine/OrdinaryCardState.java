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
    Player current_player;

    /**
     * Ordinary Card State Constructor
     */
    public OrdinaryCardState (){

    }

    public OrdinaryCardState(GameMaster gameMaster){
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
        this.current_player = gameMaster.list_of_players.get(gameMaster.current_turn); // mungkin ini nanti masih diedit lagi, apakah di - incrementnya di sini atau di engine
        this.display =
                "Sekarang giliran Anda untuk bermain.\n" +
                "\n" +
                "Kartu Anda adalah sebagai berikut: \n"+
                        this.current_player.shows_players_cards();
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
    public void take_another_card(){
        this.current_player.getCards_collection().add(this.gameMaster.stack_of_cards.pop());
        this.display = end_turn();
    }

    /**
     * Accept User Card
     *
     * @param card_name
     * @param card_color
     */
    public void accept_users_card(String card_name, String card_color){
        Card removal_target = null;
        for (Card cards : this.current_player.getCards_collection()){
            if (cards.getColor().equals(card_color) && cards.getName().equals(card_name)){
                removal_target = cards;
            }
        }
        this.gameMaster.stack_of_want_to_be_reused_cards.push(removal_target);
        this.current_player.getCards_collection().remove(removal_target);
        this.display = "Nice Move !!!!\n" +
                "\n" +
                "\n" +
                "\n" +
                end_turn();

    }

    /**
     * End Turn
     * It returns a message saying that the player's turn has finished.
     * @return
     */
    @Override
    public String end_turn(){
        return "Giliran kamu sudah selesai!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    };
}
