package com.bot.unobot.Player;

import com.bot.unobot.TestCards.Card;

import java.util.ArrayList;

public class Player {
    String id;
    ArrayList<Card> cards_collection;

    public Player(String id){
        this.id =id;
        this.cards_collection = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String shows_players_cards(){
        String card_list ="";
        for(int i =1;i<=this.cards_collection.size();i++){
            card_list+=Integer.toString(i)+". "+this.cards_collection.get(i-1)+" \n";
        }
        return card_list;
    }

    public ArrayList<Card> getCards_collection(){
        return cards_collection;
    }
}
