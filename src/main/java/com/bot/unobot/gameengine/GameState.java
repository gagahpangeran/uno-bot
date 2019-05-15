package com.bot.unobot.gameengine;

import com.bot.unobot.card.Card;
import com.bot.unobot.card.Color;
import com.bot.unobot.player.Player;

import java.util.ArrayList;
import java.util.List;

public interface GameState {

   // gw nangkepnya kalau dia gaada kartu gitu loh jadi musti ngambil dari deck

   // public void setColor(Color color);

//    public void put(Card[] cards);

    public void put(ArrayList<Card> cards);


    //tambahan dari gw
    public void nextTurn();

    public Card getLastCard();

    public String draw(String playerId);

    public int getCurrPlayerIndex();

    public void setCurrPlayerIndex( int currPlayerIndex);

    public void checkAndGetWinner (String playerId);

    public void establishedWinner(Player player, String playerId);

    public Direction getDirection();

    public void setLastCard(Card lastCard);



    //public ArrayList<Card> convertStringtoCards(String[] cards);
}
