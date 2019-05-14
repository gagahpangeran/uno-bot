package com.bot.unobot.card;

/**
 * UNO Card's Interface
 */
public interface Card {

    Color getColor();
    Effect getEffect();
    String getSymbol();
    // gua nambahin setter untuk Card
    void setColor (Color color);

}
