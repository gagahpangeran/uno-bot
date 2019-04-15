package com.bot.unobot.gameengine;

/**
 * State Interface
 * An Interface containing methods of playing the game.
 */
public interface State {

    /**
     * Assumption : User's Id will be in the form of String
     * Return the id of the user who will have the opportunity to play his cards
     *
     */
     String getCurrentPlayer();

     void cardChecking(String user_input);

     void takeAnotherCard();

     void acceptUsersCard(String card_name, String card_color);

     String endTurn();



}
