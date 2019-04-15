package com.bot.unobot.GameEngine;

import com.bot.unobot.Player.Player;
import com.bot.unobot.TestCards.Card;




import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class GameMaster {
    State ordinary_card_state;
    State plus_card_state;
    State reverse_card_state;
    State skip_card_state;
    State undetermined_card_state;
    State UNO_state;
    State current_state;
    ArrayList<Player> list_of_players;
    Stack<Card> stack_of_cards;
    Stack<Card> stack_of_want_to_be_reused_cards;
    int current_turn;
    int player_size;
    int current_champion_position ; /// untuk mengetahui dia juara ke berapa
    String string_on_display;

   public GameMaster(){
       this.ordinary_card_state =  new OrdinaryCardState(this);
       this.plus_card_state =  new PlusCardState(this);
       this.reverse_card_state = new ReverseCardState(this);
       this.skip_card_state = new SkipCardState(this);
       this.undetermined_card_state =  new UndeterminedOrdinaryCardState();

       this.current_state = null;
       this.UNO_state = new UNOState(this);
       this.list_of_players =  new ArrayList<>();
       this.stack_of_cards =  new Stack<>();
       this.stack_of_want_to_be_reused_cards = new Stack<>();
       this.current_turn = 0;
       this.player_size = 0;
       this.current_champion_position = 1;
       this.string_on_display = "";


   }

   public void init_game(){
       Collections.shuffle(stack_of_cards);
       Collections.shuffle(list_of_players);
       this.string_on_display = "Selamat bergabung di Game UNO dengan Kearifan Lokal by UNOBot\n" +
               "\n" +
               "Kenapa ada Kearifan Lokanya? Karena Game UNO ini diutak-atik sedikit peraturannya untuk menyesuaikan kebiasaan orang Indonesia \"Yang Menang Yang Keluar Dulu\" :v\n" +
               "\n" +
               "Untuk bergabung menjadi player ketik .join\n" +
               "Untuk keluar permainan ketik .forfeit\n" +
               "Untuk mengetahui status game saat ini, ketik saja .status\n" +
               "Untuk mengatakan \"UNO!\" ketik .uno\n" +
               "Untuk mengeluarkan kartu, silahkan ketik [Nama_Kartu] spasi [Warna_Kartu]\n" +
               "Untuk menampilkan kembali tampilan ini ketik .help\n" +
               "Untuk memenangkan permainan, anda harus jago (dan HOKI) tentunya :v\n" +
               "\n" +
               "Peraturan UNO sama dengan peraturan originalnya, hanya saja ketentuan pemenang diganti \"Yang Menang Yang Habis Duluan\"\n" +
               "\n" +
               "Selain itu semuanya sama\n" +
               "\n" +
               "Selamat bermain semuanya!\n" +
               "\n" +
               "\n"
               ;


   }


   public void update(){
       // nanti di update, current playernya i increment 1 :)
       /*
       * Beberapa notulensi:
       * 1. Pas reverse state, abis situ state selanjutnya adalah UndeterminedOrdinaryCardState
       * 2. Begitu juga habis plus card
       *
       * asumsi awal : kartu ordinary bisa dilawan dengan plus card
       *
       * beberapa metode update ke next state yang akan diterapkan:
       *
       * yang pake if :
       * - ordinary
       *
       * - (mungkin) UNO
       * - (mungkin) Winner
       * - (mungkin) WInnerState
       * - (mungkin) WildCard
       *
       * yang enggak :
       * - reverse
       * - skip
       * - pluscard
       * disini nanti diupdate scara otomatis di statenya.
       *
       * */

   }

   public void add_player( Player player){
       this.list_of_players.add(player);
   }

   public void remove_player(Player player){
       this.list_of_players.remove(player);
       this.player_size=this.list_of_players.size();



   }

   public boolean UNO_Checker(){
       for (Player player : this.list_of_players){
           if (player.getCards_collection().size()==1){
               return true;
           }
       }
       return false;
   }

   public String getString_on_display(){
       return this.string_on_display;
   }

    public ArrayList<Player> getList_of_players() {
        return list_of_players;
    }
}