package com.bot.unobot.player;

import com.bot.unobot.card.Card;

import java.util.ArrayList;

/**
 * Player Class
 */
public class Player {
    //Variables
    String id;
    ArrayList<Card> cards_collection;

    /**
     * Player Constructor
     * @param id
     */
    public Player(String id){
        this.id =id;
        this.cards_collection = new ArrayList<>();
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
    public String showsPlayersCards(){
        String card_list ="";
        for(int i =1;i<=this.cards_collection.size();i++){
            card_list+=Integer.toString(i)+". "+this.cards_collection.get(i-1)+" \n";
        }
        return card_list;
    }

    /**
     * Get Cards Collection
     * Return player's card collection
     * @return card collection ArrayList
     */
    public ArrayList<Card> getCardsCollection(){
        return cards_collection;
    }
}
