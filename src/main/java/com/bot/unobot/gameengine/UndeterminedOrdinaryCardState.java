package com.bot.unobot.gameengine;

import com.bot.unobot.player.Player;
import com.bot.unobot.card.Card;

/**
 * Undetermined Ordinary Card State
 */
public class UndeterminedOrdinaryCardState implements State {

    //Variables
    String color;
    String display;
    Player current_player;
    GameMaster gameMaster;

    /**
     * Undetermined Ordinary Card State Constructor
     */
    public UndeterminedOrdinaryCardState ( ){}

    public UndeterminedOrdinaryCardState ( String color){
        this.color = color;
        this.display = "";
        this.current_player = null;
    }

    public UndeterminedOrdinaryCardState ( String color, GameMaster gameMaster){
        this.color = color;
        this.gameMaster=gameMaster;
        this.display = "";
        this.current_player = null;
    }

    /**
     * Get Current Player
     * It returns the current player's ID playing the game.
     * @return current player's ID
     */
    @Override
    public String get_current_player() {
        this.current_player = gameMaster.list_of_players.get(gameMaster.current_turn); // mungkin ini nanti masih diedit lagi, apakah di - incrementnya di sini atau di engine
        this.display =
                "Selamat! Kamu mendapatkan rejeki!\n" +
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

    /**
     * Card Checking
     * It overrides the card Checking method in the State Interface.
     * It checks the card the player select with the previous card in the stack.
     * If the card has the same color or number, it will be accepted. If not, an
     * error message will be returned.
     * @param user_input
     */
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
                    end_turn();
        }
        else{
            this.display ="Maaf, Kartu yang Anda keluarkan tidak cocok\n" +
                    "\n" +
                    "Silahkan ketik .ambil untuk mengambil dari deck";
        }
    }

    /**
     * Accept User Card
     *
     * @param card_name
     * @param card_color
     */
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

    /**
     * Take Another Card
     * It adds a new card from the card collection to the player's card collection.
     * Then it ends the player's turn directly after taking the card.
     */
    @Override
    public void take_another_card() {
        this.current_player.getCards_collection().add(this.gameMaster.stack_of_cards.pop());
//        this.current_player.getCards_collection().add(this.gameMaster.stack_of_cards.get(this.gameMaster.stack_of_cards.size()-1));
//        this.gameMaster.stack_of_cards.remove(this.gameMaster.stack_of_cards.get(this.gameMaster.stack_of_cards.size()-1));
        this.display = end_turn();
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
