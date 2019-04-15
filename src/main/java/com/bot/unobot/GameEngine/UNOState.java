package com.bot.unobot.GameEngine;

import com.bot.unobot.Player.Player;
import com.bot.unobot.TestCards.Card;

public class UNOState implements State {

    GameMaster gameMaster;
    String display;
    Player current_player;

    public UNOState(GameMaster gameMaster){}

    public UNOState(GameMaster gameMaster,Player player){
        this.gameMaster=gameMaster;
        this.display = "";
        this.current_player = player;
    }

    /*
    * Cara Kerja UNO State
    *
    * UNO State ini beda dengan state lainnya karena UNO state tidak butuh user untuk ngeluarin kartu
    *
    * Mekanisme kerja UNO State:
    *
    * Jika terdapat 1 player yang kartunya tinggal 1, maka akan langsung masuk ke UNO State ( Ini bakal di cek di game engine)
    * Ketika di initiate, langsung ditampilin display berupa nama pemain dan jumlah kartu
    * Kemudian Handler akan mendapatkan Id pemain yang bilang UNO duluan
    * Id dicocokkan dengan  pemain yang jumlah kartunya 1
    * Jika ya, maka pemain tersebut menang
    * Jika tidak, maka pemain tersebut ambil 2 kartu
    *
    * Dia bakal nerima argumen berupa
    *
    *
    * */

    public String get_current_player(){
        return this.current_player.getId();
    }

    @Override
    public String finished_string() {
        return "Giliran kamu udah beres!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    }

    @Override
    public void accept_users_card(String card_name, String card_color) {

    }

    @Override
    public void take_another_card() {
        this.current_player.getCards_collection().add(this.gameMaster.stack_of_cards.pop());

    }


    /*
    * handler bisa menggunakan method ini denganc cara memasukan string berupa id user yang bilang UNO
    *
    * */
    @Override
    public void card_checking(String user_input) {
        String UNO_Id = user_input;
        Player Winner = null;

        for (Player player : this.gameMaster.list_of_players){
            if (player.getId().equals(UNO_Id)){
                Winner = player;
            }
        }

        if (!Winner.equals(null)){
            this.gameMaster.remove_player(Winner);
            this.display = "Selamat!!! Ternyata Kartu Kamu udah habis\n" +
                    "\n" +
                    "Itu artinya kamu menjadi pemenang!\n" +
                    "\n" +
                    "Kamu mendapatkan Juara ";
        }else{
            for(int i=0; i<2;i++){
                take_another_card();
            }
            this.display = "Aduh....... Kamu telat Bilang UNO! \n" +
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

        }







    }

    @Override
    public void update(Card cad) {

    }

    @Override
    public void update() {

    }

    @Override
    public void setNextColor(String color) {

    }


}
