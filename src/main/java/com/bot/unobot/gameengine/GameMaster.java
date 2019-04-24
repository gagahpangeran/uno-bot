package com.bot.unobot.gameengine;

import com.bot.unobot.player.Player;
import com.bot.unobot.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * GameMaster
 * A class consisting of variables and methods for running the UNO Game.
 */
public class GameMaster {
    //Variable
    State ordinaryCardState;
    State plusCardState;
    State reverseCardState;
    State skipCardState;
    State undeterminedCardState;
    State UNOState;
    State currentState;

    ArrayList<Player> players;
    Stack<Card> cardStack;
    Stack<Card> toBeReusedCardStack;

    int currentTurn;
    int playerSize;
    int currentChampionPosition; //to know the player's rank
    String stringOnDisplay;

    /**
     * Game Master Constructor
     */
   public GameMaster(){
       this.ordinaryCardState =  new OrdinaryCardState(this);
       this.plusCardState =  new PlusCardState(this);
       this.reverseCardState = new ReverseCardState(this);
       this.skipCardState = new SkipCardState(this);
       this.undeterminedCardState =  new UndeterminedOrdinaryCardState();

       this.currentState = null;
       this.UNOState = new UNOState(this);
       this.players =  new ArrayList<>();
       this.cardStack =  new Stack<>();
       this.toBeReusedCardStack = new Stack<>();
       this.currentTurn = 0;
       this.playerSize = 0;
       this.currentChampionPosition = 1;
       this.stringOnDisplay = "";
   }

    /**
     * Game Initialization
     * Initialize the game by shuffling the stacks of cards and the players turn. It also prints some messages
     * to display during the start of the game.
     */
   public void initGame(){
       Collections.shuffle(cardStack);
       Collections.shuffle(players);
       this.stringOnDisplay = "Selamat bergabung di Game UNO dengan kearifan lokal by UNO Bot\n" +
               "\n" +
               "Kenapa ada kearifan lokalnya? Karena Game UNO ini diutak-atik sedikit peraturannya untuk menyesuaikan kebiasaan orang Indonesia \"Yang Menang Yang Keluar Dulu\" :v\n" +
               "\n" +
               "Untuk bergabung menjadi player, ketik .join\n" +
               "Untuk keluar dari permainan ketik .forfeit\n" +
               "Untuk mengetahui status game saat ini, ketik .status\n" +
               "Untuk mengatakan \"UNO!\", ketik .uno\n" +
               "Untuk mengeluarkan kartu, silahkan ketik [Nama_Kartu] spasi [Warna_Kartu]\n" +
               "Untuk menampilkan bantuan, ketik .help\n" +
               "Untuk menampilkan peraturan permainan,ketik .rules\n" +
               "Untuk memenangkan permainan, Anda harus jago (dan HOKI) tentunya :v\n" +
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

//    /**
//     * Update
//     *
//     */
//   public void update(){
//       // nanti di update, current playernya i increment 1 :)
//       /*
//       * Beberapa notulensi:
//       * 1. Pas reverse state, abis situ state selanjutnya adalah UndeterminedOrdinaryCardState
//       * 2. Begitu juga habis plus card
//       *
//       * asumsi awal : kartu ordinary bisa dilawan dengan plus card
//       *
//       * beberapa metode update ke next state yang akan diterapkan:
//       *
//       * yang pake if :
//       * - ordinary
//       *
//       * - (mungkin) UNO
//       * - (mungkin) Winner
//       * - (mungkin) WInnerState
//       * - (mungkin) WildCard
//       *
//       * yang enggak :
//       * - reverse
//       * - skip
//       * - pluscard
//       * disini nanti diupdate scara otomatis di statenya.
//       *
//       * */
//   }

    /**
     * Add Player
     * Adding new player into the game and also adding the new player into arraylist of players.
     * @param name
     */
   public void addPlayer(String name){
       Player player = new Player(name);
       this.players.add(player);
   }

    /**
     * Remove Player
     * Removing a certain player from the game and from arraylist of players.
     * @param player
     */
   public void removePlayer(Player player){
       this.players.remove(player);
       this.playerSize = this.players.size();
   }

//    /**
//     * UNO Checker
//     * Checking the number of cards a certain player has.
//     */
//   public void UNOChecker(){
//       // return True kalau ada yang tinggal 1 kartunya
//       // return False otherwise
//   }

   public ArrayList<Player> getPlayers() {
       return players;
   }

   public String getStringOnDisplay() {
       return stringOnDisplay;
   }

   public Player findPlayer(String name) {
       for (Player player: players) {
           if(player.getId().equals(name)) {
               return player;
           }
       }
       return null;
   }

}
