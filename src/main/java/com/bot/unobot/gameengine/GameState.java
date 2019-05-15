package com.bot.unobot.gameengine;

import com.bot.unobot.card.Card;
import com.bot.unobot.card.Color;
import com.bot.unobot.player.Player;

import java.util.ArrayList;
import java.util.List;

public interface GameState {

    public void plus(Card[] cards);

    public void wild(Card[] cards);

    public void giveUp(); // gw nangkepnya kalau dia gaada kartu gitu loh jadi musti ngambil dari deck

    public void setColor(Color color);

//    public void put(Card[] cards);

    public void put(List<Card> cards);


    //tambahan dari gw
    public void nextTurn();

    public Card getLastCard();

    public void draw();

    public int getCurrPlayerIndex();

    public void setCurrPlayerIndex( int currPlayerIndex);

    public void checkAndGetWinner (String playerId);

    public void establishedWinner(Player player, String playerId);

    public Direction getDirection();

    public void setLastCard(Card lastCard);



    //public ArrayList<Card> convertStringtoCards(String[] cards);
}
