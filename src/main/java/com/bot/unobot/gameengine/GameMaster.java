package com.bot.unobot.gameengine;

import com.bot.unobot.card.*;
import com.bot.unobot.player.Player;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * GameMaster
 * A class consisting of variables and methods for running the UNO Game.
 */
public class GameMaster {
    //Variable
    OrdinaryCardState ordinaryCardState;
    PlusCardState plusCardState;
    ReverseCardState reverseCardState;
    SkipCardState skipCardState;
    UndeterminedOrdinaryCardState undeterminedCardState;
    //UNOState UNOState;
    State currentState;

    ArrayList<Player> players;
    Stack<Card> cardStack;
    Stack<Card> toBeReusedCardStack;

    int currentTurn;
    int playerSize;
    int currentChampionPosition; //to know the player's rank
    String stringOnDisplay;
    Player playerInUNOState; // diubah

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
       //this.UNOState = new UNOState(this);
       this.players =  new ArrayList<>();
       this.cardStack =  new Stack<>();
       this.toBeReusedCardStack = new Stack<>();
       this.currentTurn = 0;
       this.playerSize = 0;
       this.currentChampionPosition = 1;
       this.stringOnDisplay = "";
       this.playerInUNOState = null;

   }

    /**
     * Game Initialization
     * Initialize the game by shuffling the stacks of cards and the players turn. It also prints some messages
     * to display during the start of the game.
     */
   public void initGame(){
       fillCardStack();
       Collections.shuffle(cardStack);
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



//diubah
    /*
    * getFirsState
    * return State that will become the first state of the game
    *
    * */
   public State getFirstState(){
       Collections.shuffle(cardStack);
       toBeReusedCardStack.push(cardStack.pop());
       Card firstCard = cardStack.peek();
       if(firstCard instanceof SkipCard){
           return skipCardState;
       }else if (firstCard instanceof PlusCard){
           if(firstCard.getColor().equals("Black")){
               cardStack.push(toBeReusedCardStack.pop());
               getFirstState();
           }else{
               return plusCardState;
           }
       }else if (firstCard instanceof ReverseCard){
           return reverseCardState;
       }else if (firstCard instanceof WildCard){
           cardStack.push(toBeReusedCardStack.pop());
           getFirstState();
       }
       return ordinaryCardState ;

   }

   /*
   * fillCardStack
   * fill and shuffle the cardStack
   * */

   public void fillCardStack(){
       /*
       * Card production order:
       * 1 red
       * 2 green
       * 3 blue
       * 4 yellow
       * */

       //the making of 76 ordinary cards
       String reverse = "Reverse";
       String skip = "Skip";
       for (Color color: Color.values()){
           if (color != Color.SPECIAL) {
               cardStack.push(new OrdinaryCard(Integer.toString(0), color));

               for (int i = 0; i < 2; i++) {
                   for (int j = 1; j <= 9; j++)
                       cardStack.push(new OrdinaryCard(Integer.toString(j), color));

                   cardStack.push(new SkipCard(skip, color));
                   cardStack.push(new ReverseCard(reverse, color));
                   cardStack.push(new PlusCard("+2", color, 2));
               }
           } else {
               for (int i = 0; i < 4; i++) {
                   cardStack.push(new WildCard(color));
                   cardStack.push(new PlusCard("4", color, 4));
               }
           }
       }
   }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Add Player
     * Adding new player into the game and also adding the new player into arraylist of players.
     * @param id
     */
   public void addPlayer(String id){
       Player player = new Player( id);
       this.players.add(player);
       Collections.shuffle(this.players);

   }

   public Player findPlayer(String id) {
       for (Player player: players)
           if (id.equals(player.getId()))
               return player;
       return null;
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

    /**
     * UNO Checker
     * Checking the number of cards a certain player has.
     */
   public boolean UNOChecker(){
       // return True kalau ada yang tinggal 1 kartunya
       // return False otherwise
       for (Player player: this.players){
           if (player.getCardsCollection().size()<=1){
               playerInUNOState = player;
               return true;

           }
       }
       return false;
   }

   public String getCurrentPlayer(){
       if (currentState instanceof UNOState){
        return ((UNOState) currentState).concurrentState.getCurrentPlayer();
       }
       return currentState.getCurrentPlayer();
   }

   public void acceptUsersCard(String cardName,Color cardColor){
       if (currentState instanceof UNOState){
            ((UNOState) currentState).concurrentState.acceptUsersCard(cardName, cardColor);
       }else{
       currentState.acceptUsersCard(cardName, cardColor);
       }
   }
   public void takeAnotherCard(){
       if(currentState instanceof UNOState){
           ((UNOState) currentState).concurrentState.takeAnotherCard();
       }else{
           currentState.takeAnotherCard();
       }

   }

   public String getStringOnDisplay(){
       return stringOnDisplay;
   }

    /**
     * Update
     *
     */
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
        this.currentState.update();
        if (UNOChecker() && !playerInUNOState.equals(null)){
            UNOState UNOState = new UNOState(this);
            UNOState.setConcurrentState(currentState);
            currentState = UNOState;
        }
    }

    /*
    *Method ini digunakan untuk memberi tahu jumlah karu dari semua pemain
    * dan juga memberi tahu kartu apa yang sekarang berada di top of stack tobereused cards
    * */

    public void displayGameCondition(){
        stringOnDisplay = "Klasemen Permainan\n\n";
        for (Player player : players ){
            stringOnDisplay+=player.getId()+"\n"+
                    "Jumlah Kartu Tersisa: "+player.getCardsCollection().size()+"\n\n";
        }
        stringOnDisplay+="Kartu terakhir yang dimainkan: "+toBeReusedCardStack.peek().getName()+" "+toBeReusedCardStack.peek().getColor();
    }

    /*
    * Method ini digunakan untuk menampilkan string yang harus ditampilkan oleh bot ketika dia ngechat pemain
    * String yang diproduksi akan menampilkan daftar kartu2 yang ada di tangan pemain
    *
    * */

    public void displayCurrentPlayerStatistic(){
        Player currentPlayer = players.get(currentTurn%playerSize);
       stringOnDisplay = "Hi "+currentPlayer.getId()+"! \n"+
               "Koleksi kartu kamu sekarang adalah :\n\n";
       for (Card card: currentPlayer.getCardsCollection()){
           stringOnDisplay+=card.getName()+" "+card.getColor()+" \n";

       }
       stringOnDisplay+="Jika ingin keluarkan kartu ketik .use (Nama Kartu) (Warna Kartu)\n" +
               "Jika ingin mengambil kartu dari deck ketik .draw";
    }

    /*
    *
    *
    * */

    public void recycleToBeUsedCardStack(){
        Collections.shuffle(toBeReusedCardStack);
        cardStack = toBeReusedCardStack;
        toBeReusedCardStack = new Stack<>();
    }






}
