package com.bot.unobot.gameengine;

import com.bot.unobot.card.*;
import com.bot.unobot.player.Player;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 * GameMaster
 * A class consisting of variables and methods for running the UNO Game.
 */

/*
* Disini gw bakal nulis dokumentasi + keterangan + logic untuk masing - masing method
* Jika ada yang kurang jelas langsung kontak gw aja, telepon juga boleh wkwkwk
* */
public class GameMaster {
    private GameState normalState; // Normal State adalah state dimana ketika kartu yang terakhir ditaruh kartu biasa (Bukan kartu plus)
    //private GameState wildState;   wildState gak akan gw pake. Mengapa? akan gw jelaskan nanti
    private GameState plusState; // Plus State adalah state dimana ketika kartu yang terakhir ditaruh kartu plus
    private GameState currentState;
    private Stack<Card> trashCards; // stack of cards yang merupakan kartu yang "dibuang" pemain ketika dia mengeluarkan kartu
    private  Stack<Card> newCards; // stack of cards yang merupakan kartu yang belum pernah masuk ke tangan pemain
    private String messageToGroup; // string yang ditampilkan oleh bot ke grup
    private String messageToPlayer; // string yang ditampilkan oleh bot ke player individually
    private int championPosition;// Posisi juara yang diperebutkan. Misalnya ketika belum ada yang menang, berarti yang diperebutkan juara 1, ketika juara 1 udah ada, yang diperebutkan juara 2 dst...
    private String ruleString;
    private ArrayList<Player> players; // ArrayList isinya pemain - pemain yang akan bergabung dalam permainan

    /*
    * Class Constructor -- gak perlu dijelasin lagi lah ya :)
    * */
    public GameMaster() {
        this.normalState = new NormalState(this);
        //this.wildState = new WildState(this);
        this.plusState = new PlusState(this);
        this.currentState = this.normalState;
        this.players = new ArrayList<>();
        this.championPosition = 1;
        this.messageToGroup ="";
        this.messageToPlayer="";
        this.newCards = new Stack<Card>();
        this.trashCards  =  new Stack<Card>();
        this.ruleString = "FINALRULE!!!\n" + "- Syarat kartu anda diterima:\n" + "1. kartu anda memiliki warna yang sama atau symbol yang sama dengan apa yang ditaruh pemain sebelumnya\n" + "2. kartu angka tidak bisa dicombo.\n" + "3. kartu yang bisa dicombo hanyalah plus,reverse,dan skip.\n" + "4. Combo hanya berlaku jika dia sejenis. Jika tidak maka akan ditolak\n" + "5. \n" + "- Jika Pemain terkena skip, namun dia punya skip, dia tetap gabisa main dan tetap di skip\n" + "- Jika pemain gak punya kartu, maka di harus draw. Setelah draw pemain tidak bisa jalan lagi. Harus tunggu gilirannya lagi.\n" + "- Pemain yang kartunya abis duluan, dialah pemenangnya\n" + "- Aturan Khusus WildCard:\n" + "\n" + "1. Jika anda mendapatkan WildCard atau Plus 4 maka cara menggunakannya adalah dengan mengetik set;[warna yang diinginkan] di akhir kalimat\n" + "\n" + "put Wildcard;special set;green\n" + "put Wildcard;special 7;blue set blue\n" + "put +2;green +2;yellow +4;special set;blue\n" + "\n" + "Untuk Wildcard selalu ketik di awal kalimat\n" + "Untuk +4 selalu ketik di akhir kalimat\n" + "\n" + "Misal kartu terakhir yang dikeluarkan : 7 Red\n" + "Kartu yang ada punya: 6 Yellow dan WildCard\n" + "Cara memakai : put Wildcard;special 6;yellow set;yellow\n";
    }

    /*Beberapa setter and getter*/


//    public void setColor(Color color) {
//        this.currentState.setColor(color);
//    }

    public void put(ArrayList<Card> cards) {
        this.currentState.put(cards);
    }

    public String getMessageToGroup() {
        return messageToGroup;
    }

    public String getMessageToPlayer() {
        return messageToPlayer;
    }

    public void setMessageToGroup(String messageToGroup) {
        this.messageToGroup = messageToGroup;
    }

    public void setMessageToPlayer(String messageToPlayer) {
        this.messageToPlayer = messageToPlayer;
    }

    public void setChampionPosition(int championPosition) {
        this.championPosition = championPosition;
    }

    public int getChampionPosition() {
        return championPosition;
    }

    /*beberapa getter*/

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public GameState getNormalState() {
        return normalState;
    }

    public GameState getPlusState() {
        return plusState;
    }



    public int getNrOfPlayers(){
        return players.size();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Stack<Card> getNewCards() {
        return newCards;
    }

    public Stack<Card> getTrashCards() {
        return trashCards;
    }


/*
* @param prevCard = Kartu terakhir yang ditaruh oleh player yang terakhir jalan
* @param cards = Kartu yang dikeluarkan oleh player yang lagi jalan. Berbentuk arraylist karena kartu yang dikeluarkan player bisa lebih dari 1 (ingat kan ada kombo)
* @return boolean = Kartu akan dianggap bisa ditaruh jika warna sama atau simbol sama atau dia merupakan wildcard
* */
    public boolean isPuttable(Card prevCard, ArrayList<Card> cards) {
        Card currentCard = cards.get(0);


        return ((prevCard.getColor() == currentCard.getColor()) || (prevCard.getSymbol().equals(currentCard.getSymbol())) || currentCard instanceof WildCard); }

    /*
    * @param card = Jadi, karena player akan memberi inputan berupa string, input user perlu dikonversi menjadi sebuah object berupa ArrayList<Card>
    *  Tujuan dari method ini adalah mengkonversi ArrayList of string yang merupakan kartu - kartu yang dikeluarkan oleh player. Mengapa string? karena user inputnya kan berupa string(?)
    *  Cara kerja:
    *  ArrayList<String> card akan berisi string yang formatnya = [symbol_kartu];[warna_kartu]
    *  String ini secara satu persatu akan di split, dan [symbol_kartu] dan [warna_kartu] akan dicocokkan dengan kartu kartu yang benar benar ada di tangan player
    *
    *  Jika cocok, maka kartu akan ditambahkan ke ArrayList<Card> convertedCards
    *  jika tidak cocok, maka tidak akan ditambahkan ke ArrayList<Card> convertedCards
    *
    * Jika ArrayList<Card> convertedCards.size() == ArrayList<String> card.size() maka kartu yang diberikan player valid dan fungsi akan
    * mereturn ArrayList<Card> convertedCards yang isinya kumpulan kartu yang hendak dikeluarkan oleh user
    *
    * Jika tidak, maka ArrayList<Card> convertedCards akan dihapus, dan akan mereturn ArrayList<Card> kosong
    * */


    public ArrayList<Card> converStringstoCards(ArrayList<String> card) {

        ArrayList<Card> convertedCards = new ArrayList<>();
        for (String cardInString : card){
            Color colorOfCardInString = null;
            String[] cardInStringIndentity = cardInString.split(";");
            switch (cardInStringIndentity[1].toUpperCase()){
                case "RED":
                    colorOfCardInString = Color.RED;
                    break;
                case "YELLOW":
                    colorOfCardInString = Color.YELLOW;
                    break;
                case "GREEN":
                    colorOfCardInString = Color.GREEN;
                    break;
                case "BLUE":
                    colorOfCardInString = Color.BLUE;
                    break;
//                case "SPECIAL":
//                    colorOfCardInString = Color.SPECIAL;
//                    break;
            }
            for (Card card1: getPlayers().get(this.currentState.getCurrPlayerIndex()).getCardsCollection()){

                if (cardInStringIndentity[0].toUpperCase().equals(card1.getSymbol().toUpperCase())&&colorOfCardInString.equals(card1.getColor())){
                    convertedCards.add(card1);
                }
            }
        }
        if (convertedCards.size() != card.size()){
            convertedCards.clear();
        }
        return convertedCards;

    }

    /*
    * @param card = arraylist string yang isinya masing masing adalah string dengan format [symbol_kartu];[warna_kartu]
    * @param colorSetByPlayer = warna yang di ingin di set oleh pemain yang mengeluarkan wildcard
    *
    * Jadi method ini merupakan method pengganti WildCard state
    * Jadi ketika user mengeluarkan wildcard, maka yang dilakukan method ini adalah mengubah warna si wildcard menjadi warna yang dikehendaki user
    * Jadi dengan begitu, warna yang diinginkan user akan tersimpan di wildcard
    *
    * Sisanya sama seperti method yang di atas
    *
    * @return convertedCards = inputan user yang sudah dikonversi ke ArraList<Card>
    *
    *
    * */

    public ArrayList<Card> converStringstoCards(ArrayList<String> card, String colorSetByPlayer) {
        ArrayList<Card> convertedCards = new ArrayList<>();
        for (String cardInString : card){
            Color colorOfCardInString = null;
            String[] cardInStringIndentity = cardInString.split(";");
            switch (cardInStringIndentity[1].toUpperCase()){
                case "RED":
                    colorOfCardInString = Color.RED;
                    break;
                case "YELLOW":
                    colorOfCardInString = Color.YELLOW;
                    break;
                case "GREEN":
                    colorOfCardInString = Color.GREEN;
                    break;
                case "BLUE":
                    colorOfCardInString = Color.BLUE;
                    break;
                case "SPECIAL":
                    colorOfCardInString = Color.SPECIAL;
                    break;
                    default:

            }
            for (Card card1: getPlayers().get(this.currentState.getCurrPlayerIndex()).getCardsCollection()){


                if (cardInStringIndentity[0].toLowerCase().equals(card1.getSymbol().toLowerCase())&&colorOfCardInString.equals(card1.getColor())){
                    if (card1.getColor().equals(Color.SPECIAL)){

                        switch (colorSetByPlayer.toUpperCase()){
                            case "RED":
                                card1.setColor(Color.RED);
                                break;
                            case "YELLOW":
                                card1.setColor(Color.YELLOW);
                                break;
                            case "GREEN":
                                card1.setColor(Color.GREEN);
                                break;
                            case "BLUE":
                                card1.setColor(Color.BLUE);
                                break;
                                default:
                        }

                    }
                    convertedCards.add(card1);
                }
            }
        }


        if (convertedCards.size() != card.size()){
            convertedCards.clear();
        }
        //debug
        System.out.println(convertedCards.size());
        return convertedCards;



    }



    /*
    * @param cards = ArrayList<Card> yang merupakan kartu kartu yang dikeluarkan player
    * @return boolean = apakah koleksi kartu tersebut valid atau tidak
    *
    * Syarat kombinasi kartu yang bersangkutan valid:
    * 1. Jika simbol semua kartu sama
    * 2. Jika kartu merupakan wildcard
    *
    *
    * */


    public boolean checkCombo(ArrayList<Card> cards) {
        String comboSymbol = cards.get(0).getSymbol();
        if (cards.get(0) instanceof WildCard){
            if (cards.size()<2){ return true;}
            else {
                comboSymbol = cards.get(1).getSymbol();
            }
        }
        if (cards.get(0) instanceof PlusCard){
            for (Card card: cards) {
                if (!card.getEffect().equals(cards.get(0).getEffect())&&!(card instanceof WildCard)) {
                    return false;
                }
            }

        }else{
            for (Card card: cards) {
                if (!card.getSymbol().equals(comboSymbol)&&!(card instanceof WildCard)) {
                    return false;
                }
            }
        }


        return true;
    }

    /*method ini digunakan untuk mengenerate kartu baru yang akan digunakan dalam game. Total ada 108 kartu. Jangan liat logicnya, memang masih berantkan
    * But this is the least I can do :(
    * */

    public void createNewCards(){

        for (int i =1;i<5;i++) {
            Color color;
            if (i==1){
                color = Color.RED;
            }else if (i==2){
                color = Color.YELLOW;
            }else if (i==3){
                color = Color.GREEN;
            }else  {
                color = Color.BLUE;
            }
            for (int j =1;j<=19;j++) {
                String cardName = Integer.toString(j%10);
                OrdinaryCard ordinaryCard = new OrdinaryCard(cardName,color);
                newCards.push(ordinaryCard);
            }

            for ( int k =1; k<=6; k++) {
                if (1<=k && k<=2) {
                    PlusCard card = new PlusCard(color,2);
                    newCards.push(card);

                }else if (3<=k && k<=4){
                    SkipCard card = new SkipCard(color);
                    newCards.push(card);
                }else {
                    ReverseCard card = new ReverseCard(color);
                    newCards.push(card);
                }

            }

        }

        for (int l=1;l<9;l++){
            if (l<5){
                WildCard card =  new WildCard(Color.SPECIAL);
                newCards.push(card);
            }else{
                PlusCard card =  new PlusCard(Color.SPECIAL,4);
                newCards.push(card);
            }
        }
        //debug
       Collections.shuffle(newCards);

    }

    /*
    * Ketika stack newCards, stack dimana user mengambik "kartu baru" habis, maka kartu dari stack trashCard
    * akan di recycle ke menjadi stack newCards.
    *
    * */

    public void recycleTrashCards(){

        while (!trashCards.empty()){
            newCards.push(trashCards.pop());
        }
        Collections.shuffle(newCards);
    }




    public Player getSpecificPlayer(String playerId){
        for (Player player: players){
            if (player.getId().equals(playerId)) return player;
        }
        return null;
    }

    /*
    * @param cards = ArrayList<Card> yang merupakan kartu kartu yang dikeluarkan player
    * Memasukkan kartu kartu yang dikeluarkan player ke stack trashCards
    * */

    public void addToTrash(ArrayList<Card> cards){
        ArrayList<Card> temp = (ArrayList<Card>)cards.clone();
        for (Card card: cards){
            trashCards.push(card);
        }
        for (Card card: temp){
            this.getPlayers().get(getCurrentState().getCurrPlayerIndex()).getCardsCollection().remove(card);
        }
    }



    /*
    *Method yang dieksekusi untuk memulai permainan
    * Yang dilakukan ;
    * 1. createNewCards() --> kalau gak tau baca dokumentasi methodnya lagi
    * 2. Membagikan masing masing pemain 7 kartu secara acak
    * 3. Mengeluarkan 1 kartu patokan yang menjadi kartu pertama permainan
    *
    * */

    public void initGame(){
        createNewCards();
        //debug
        Collections.shuffle(newCards);
        for (Player player : players){
            for (int i=0;i<7;i++){
                player.getCardsCollection().add(newCards.pop());
            }
        }
        while (!newCards.peek().getEffect().equals(Effect.NOTHING)){
            Collections.shuffle(newCards);
        }

        trashCards.push(newCards.pop());
        this.currentState.setLastCard(trashCards.peek());
        setMessageToGroup("Game sudah dimulai!!!!");

    }


    /*
    * @param playerId = playerId
    * Menambah pemain ke game
    * */
    public void addPlayer (String playerId){
        players.add(new Player(playerId));
        //debug
        System.out.println(playerId+" "+"terdaftar!");
    }

    /*Beberapa Sting yang akan digenerate untuk ditampilkan
    * ----------------------------------------------------------------------------------------------------------------------------------------------------------------
    * */

    /**/

    public String getInfo(){
        String info = "Daftar pemain dan kartunya:\n\n";
        for (Player player: players){
            info+=player.getId()+"\n"+"jumlah kartu = "+player.getCardsCollection().size()+"\n\n";
        }
        if (this.currentState.getDirection() == Direction.CW){
            info+="Reverse:\nTrue\n\n\n";
        }else{
            info+="Reverse:\nFalse\n\n\n";
        }
        //debug
        if (currentState.getLastCard() == null && currentState instanceof PlusState) System.out.println("ngix");

        info+="Kartu yang terakhir dimainkan: "+currentState.getLastCard().getSymbol()+" "+currentState.getLastCard().getColor()+"\n";
        info+="Giliran sekarang : "+players.get(currentState.getCurrPlayerIndex()).getId();

        return info;
    }

    public String getMessageForPlayer(String playerId){
        Player target = null;
        String message = "Kartu kamu sekarang:\n";
        for (Player player : players){
            if (player.getId().equals(playerId)){
                target = player;

            }
        }

       if(target == (null)) return "aaaaa ngebuggggg!!!!";
        for (Card card: target.getCardsCollection()){
            message+=card.getSymbol()+" "+card.getColor()+" \n";
        }
        message+="Kartu yang terakhir dimainkan: "+this.currentState.getLastCard().getSymbol()+" "+currentState.getLastCard().getColor()+" \n";
        message+="jika ingin mengeluarkan ketik : put[spasi]namakartu1;warnakartu1[spasi]namakartu2;warnakartu2dst...\n" + "jika tidak punya kartu dan ingin ngedraw ketik : draw";
        return message; }

    public String putSucceed(){ return "Sukses meletakkan kartu!"; }

    public String putFailed(){ return "Kartu yang kamu letakkan tidak valid, coba ketik ulang, atau kalau emang kamu bohong, ketik draw saja :)"; }



    public String winnerString(String playerId){ return "Selamat... pemain "+playerId+" berhasil meraih peringkat - "+championPosition+"\n" + "Game akan secara otomatis meng-kick pemain "+playerId; }

    public String failedToWin(String playerId){ return "karena pemain "+playerId+" telat bilang uno... jadi otomatis dia dapet dua kartu deh\n" + "Makanya jangan telat bos! ngohahahahahaha"; }

    public String unoSafeString(String playerId){ return "Selamat .... Player "+playerId+" aman. Anda tidak perlu ambil 2 kartu karena sudah bilang UNO"; }


    public String getRule() { return ruleString; }
}
