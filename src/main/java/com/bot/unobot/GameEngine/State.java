
package com.bot.unobot.GameEngine;

import com.bot.unobot.TestCards.Card;

public interface State {




    /**
     * Assumption : User's Id will be in the form of String
     * Return the id of the user who will have the opportunity to play his cards
     *
     */
     String get_current_player();

     void card_checking(String user_input);

     void take_another_card();

      void accept_users_card(String card_name, String card_color);

     String finished_string();

     void update();
     void update(Card cad);
     void setNextColor(String color);



}
