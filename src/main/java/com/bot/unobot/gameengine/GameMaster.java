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

//Nanti akan ada method nextTurn yang akan mengupdate state sama turn for each player
//Plizzz dong jangan diprotes... ku sudah capek ngoding gameEngine :(
public class GameMaster {
    private GameState normalState;
    private GameState wildState;
    private GameState plusState;
    private GameState currentState;
    private Stack<Card> trashCards;
    private Stack<Card> newCards;
    private String messageToGroup;
    private String messageToPlayer;
    private int championPosition;

    private ArrayList<Player> players;

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
    }

    public void plus(Card[] cards) {
        this.currentState.plus(cards);
    }

    public void wild(Card[] cards) {
        this.currentState.wild(cards);
    }

    public void giveUp() {
        this.currentState.giveUp();
    }

    public void setColor(Color color) {
        this.currentState.setColor(color);
    }

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



    public boolean isPuttable(Card prevCard, ArrayList<Card> cards) {
        Card currentCard = cards.get(0);

//        boolean isValid = checkCardsIntegrity(cards);
//        if (prevCard instanceof WildCard){
//            return ((((WildCard) prevCard).getColor() == currentCard.getColor()) ||
//                    (prevCard.getSymbol().equals(currentCard.getSymbol())));
//        }
        //debug
        System.out.println("prevcard symbol: "+prevCard.getSymbol());
        System.out.println("currentcard symbol: "+currentCard.getSymbol());
        System.out.println((prevCard.getColor() == currentCard.getColor()) ||
                (prevCard.getSymbol().equals(currentCard.getSymbol()) || currentCard.getColor() == Color.SPECIAL));
        return ((prevCard.getColor() == currentCard.getColor()) ||
                (prevCard.getSymbol().equals(currentCard.getSymbol())  || currentCard.getColor() == Color.SPECIAL));
    }

    //Logic : jika konversi berhasil return Card[] else return null
    public ArrayList<Card> converStringstoCards(ArrayList<String> card) {
        //System.out.println("heheheh");
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
            }
            for (Card card1: getPlayers().get(this.currentState.getCurrPlayerIndex()).getCardsCollection()){
                if (cardInStringIndentity[0].equals(card1.getSymbol())&&colorOfCardInString.equals(card1.getColor())){
                    convertedCards.add(card1);
                }
            }
        }
        if (convertedCards.size() == card.size()){
            for (Card card1: convertedCards){
                getPlayers().get(this.currentState.getCurrPlayerIndex()).getCardsCollection().remove(card1);
            }
            //debug
//            for (Card card1: convertedCards){
//                System.out.println(card1.getSymbol()+"ss"+card1.getColor());
//            }
            return convertedCards;

        }
        System.out.println("bangsat");
        convertedCards.clear();
        return convertedCards;

    }

    public ArrayList<Card> converStringstoCards(ArrayList<String> card, String colorSetByPlayer) {
        //System.out.println("heheheh");
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
            }
            for (Card card1: getPlayers().get(this.currentState.getCurrPlayerIndex()).getCardsCollection()){
                if (cardInStringIndentity[0].equals(card1.getSymbol())&&colorOfCardInString.equals(card1.getColor())){
                    if (card1.getEffect().equals(Color.SPECIAL)){
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
                        }

                    }
                    convertedCards.add(card1);
                }
            }
        }
        if (convertedCards.size() == card.size()){
            for (Card card1: convertedCards){
                getPlayers().get(this.currentState.getCurrPlayerIndex()).getCardsCollection().remove(card1);
            }
            //debug
//            for (Card card1: convertedCards){
//                System.out.println(card1.getSymbol()+"ss"+card1.getColor());
//            }
            return convertedCards;

        }
        System.out.println("bangsat");
        convertedCards.clear();
        return convertedCards;

    }



    // gua tambahin

    /*
    * Method ini buat mastiin bahwa kartu yang dikeluarin user beneran kartu yang dia punya dan bukan kartu yang dia gak punya
    * */

//    public boolean checkCardsIntegrity(Card[] cards){
//        for (int i=0;i<cards.length;i++){
//            boolean cardIsThere = this.players.get(this.currentState.getCurrPlayerIndex()).getCardsCollection().contains(cards[i]);
//            if (!cardIsThere) return false;
//        }
//        return true;
//    }

    //Untuk sementara symbol dibikin string


    public boolean checkCombo(ArrayList<Card> cards) {
        String comboSymbol = cards.get(0).getSymbol();

        if (cards.get(0) instanceof WildCard){
            if (cards.size()==1) return true;
            else{
                ArrayList<Card> tempCards = (ArrayList<Card>) cards.subList(1, cards.size());
                for (Card card:tempCards){
                    if (!card.getSymbol().equals(comboSymbol)) {
                        return false;
                    }
                }

            }
        }


        for (Card card: cards) {
            if (!card.getSymbol().equals(comboSymbol)) {
                return false;
            }
        }

        return true;
    }

    /*method ini */

    public void createNewCards(){

        for (int i =1;i<5;i++) {
            Color color;
            if (i==1){
                color = Color.RED;
            }else if (i==2){
                color = Color.YELLOW;
            }else if (i==3){
                color = Color.GREEN;
            }else {
                color = Color.BLUE;
            }
            for (int j =1;j<20;j++) {
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
            }

        }
        Collections.shuffle(newCards);

    }

    public void recycleTrashCards(){
        for (int i =0;i<trashCards.size();i++){
            newCards.push(trashCards.pop());
        }
        Collections.shuffle(newCards);
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

    public void addToTrash(ArrayList<Card> cards){
        for (Card card: cards){
            players.get(this.currentState.getCurrPlayerIndex()).getCardsCollection().remove(card);
            trashCards.push(card);
        }
    }

    public ArrayList<Card> setColorFromWildCardByPlayer (ArrayList<Card> cardArrayList,String color){
        color = color.toLowerCase();
       if (this.currentState.getLastCard().getColor() == Color.SPECIAL){
           switch (color){
               case "red":
                   cardArrayList.get(cardArrayList.size()-1).setColor(Color.RED);
                   //this.currentState.getLastCard().setColor(Color.RED);
                   break;
               case "yellow":
                   cardArrayList.get(cardArrayList.size()-1).setColor(Color.YELLOW);
                   //this.currentState.getLastCard().setColor(Color.YELLOW);
                   break;
               case "green":
                   cardArrayList.get(cardArrayList.size()-1).setColor(Color.GREEN);
                   //this.currentState.getLastCard().setColor(Color.GREEN);
                   break;
               case "blue":
                   cardArrayList.get(cardArrayList.size()-1).setColor(Color.BLUE);
                   //this.currentState.getLastCard().setColor(Color.BLUE);
                   break;
                   default:

           }
       }
       return cardArrayList;
    }

    public void initGame(){
        createNewCards();
        Collections.shuffle(newCards);
        for (Player player : players){
            for (int i=0;i<7;i++){
                player.getCardsCollection().add(newCards.pop());
            }
        }
        trashCards.push(newCards.pop());
        Card lastCard = trashCards.peek();
        this.currentState.setLastCard(lastCard);
        while (lastCard.getEffect() != Effect.NOTHING){
            newCards.push(trashCards.pop());
            Collections.shuffle(newCards);
            trashCards.push(newCards.pop());
            lastCard = trashCards.peek();
        }
        //for debugging
        System.out.println("Game sudah dimulai");

    }

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

    public void addPlayer(String playerId){
        players.add(new Player(playerId));
        //debug
        System.out.println(playerId+" terdaftar!");
    }

    /*Beberapa String yang akan digenerate untuk ditampilkan
    * ----------------------------------------------------------------------------------------------------------------------------------------------------------------
    * */

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
        info+="Kartu yang terakhir dimainkan: "+trashCards.peek().getSymbol()+" "+trashCards.peek().getColor()+"\n";
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

       if(target.equals(null)) return "aaaaa ngebuggggg!!!!";
        for (Card card: target.getCardsCollection()){
            message+=card.getSymbol()+" "+card.getColor()+" \n";
        }
        message+="jika ingin mengeluarkan ketik : put[spasi]namakartu1;warnakartu1[spasi]namakartu2;warnakartu2dst...\n" +
                "jika tidak punya kartu dan ingin ngedraw ketik : draw";
        return message;
    }

    public String putSucceed(){
        return "Sukses meletakkan kartu!";
    }

    public String putFailed(){
        return "Kartu yang kamu letakkan tidak valid, coba ketik ulang, atau kalau emang kamu bohong, ketik draw saja :)";
    }

    public String nextTurnString(){
        return "Giliran kamu udah beres, tunggu giliranmu selanjutnya ya";
    }

    public String winnerString(String playerId){

        return "Selamat... pemain "+playerId+" berhasil meraih peringkat - "+championPosition+"\n" +
                "Game akan secara otomatis meng-kick pemain "+playerId;
    }

    public String failedToWin(String playerId){
        for (Player player: players){
            if (playerId.equals(playerId)){
                for (int i=0;i<2;i++){
                    player.getCardsCollection().add(newCards.pop());
                }
            }
        }
        return "karena pemain "+playerId+" telat bilang uno... jadi otomatis dia dapet dua kartu deh\n" +
                "Makanya jangan telat bos! ngohahahahahaha";
    }


    public String getRule() {
        return "FINALRULE!!!\n" +
                "- Syarat kartu anda diterima:\n" +
                "1. kartu anda memiliki warna yang sama atau symbol yang sama dengan apa yang ditaruh pemain sebelumnya\n" +
                "2. kartu angka tidak bisa dicombo.\n" +
                "3. kartu yang bisa dicombo hanyalah plus,reverse,dan skip.\n" +
                "4. Combo hanya berlaku jika dia sejenis. Jika tidak maka akan ditolak\n" +
                "5. \n" +
                "- Jika Pemain terkena skip, namun dia punya skip, dia tetap gabisa main dan tetap di skip\n" +
                "- Jika pemain gak punya kartu, maka di harus draw. Setelah draw pemain tidak bisa jalan lagi. Harus tunggu gilirannya lagi.\n" +
                "- Pemain yang kartunya abis duluan, dialah pemenangnya\n" +
                "- Aturan Khusus WildCard:\n" +
                "\n" +
                "1. Jika anda mendapatkan WildCard atau Plus 4 maka cara menggunakannya adalah dengan mengetik set;[warna yang diinginkan] di akhir kalimat\n" +
                "\n" +
                "put Wildcard;special set;green\n" +
                "put Wildcard;special 7;blue set blue\n" +
                "put +2;green +2;yellow +4;special set;blue\n" +
                "\n" +
                "Untuk Wildcard selalu ketik di awal kalimat\n" +
                "Untuk +4 selalu ketik di akhir kalimat\n" +
                "\n" +
                "Misal kartu terakhir yang dikeluarkan : 7 Red\n" +
                "Kartu yang ada punya: 6 Yellow dan WildCard\n" +
                "Cara memakai : put Wildcard;special 6;yellow set;yellow\n";
    }
}
