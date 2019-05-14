package com.bot.unobot.player;

import com.bot.unobot.card.Card;

import java.util.ArrayList;

/**
 * Player Class
 * id = player's Id
 * cars = koleksi kartu pemain
 * isUNO = apakah player itu lagi UNo atau tidak.
 * Definisi lagi UNO = ketika pemain tersebut kartunya tinggal satu dan dia berhasil bilang UNO
 */
public class Player {
    String id;
    ArrayList<Card> cards;
    boolean isUNO;

    /**
     * Player Constructor
     * @param id
     */
    public Player(String id){
        this.id =id;
        this.cards = new ArrayList<>();
        this.isUNO = false;
    }

    /**
     * get ID
     * return ID
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Show Player's Card
     * Return player's card in the form of a string
     * @return player's card
     */
//    public String showsPlayersCards(){
//        String cardList ="";
//        for(int i = 1; i<=this.cards.size(); i++){
//            cardList+=Integer.toString(i)+". "+this.cards.get(i-1)+" \n";
//        }
//        return cardList;
//    }

    /**
     * Get Cards Collection
     * Return player's card collection
     * @return card collection ArrayList
     */
    public ArrayList<Card> getCardsCollection(){
        return cards;
    }

    //debug

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setUNO(boolean uno) {
        isUNO = uno;
    }

    public boolean isUNO() {
        return isUNO;
    }
}
