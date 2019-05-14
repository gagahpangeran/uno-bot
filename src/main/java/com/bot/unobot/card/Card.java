package com.bot.unobot.card;

/**
 * UNO Card's Interface
 */
public interface Card {

    // gua nampilin getter untuk Card
    Color getColor();
    Effect getEffect();
    String getSymbol();
    // selain itu, gua juga nambahin setter untuk Card
    void setColor (Color color);

}
