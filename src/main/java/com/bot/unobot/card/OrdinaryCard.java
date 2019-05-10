package com.bot.unobot.card;

/**
 * Ordinary Card
 * A class implementing the Card interface.
 */
public class OrdinaryCard implements Card {
    //Variables
    String symbol;
    Color color;
    Effect effect;

    /**
     * Ordinary Card Constructor
     * @param symbol
     * @param color
     */
    public OrdinaryCard(String symbol, Color color){
        this.symbol = symbol;
        this.color = color;
        this.effect = Effect.NOTHING;
    }

    /**
     * Get Name
     * Return Card's name
     * @return card's name
     */
    @Override
    public Effect getEffect() {
        return effect;
    }

    /**
     * Get Color
     * Return card's color
     * @return card's color String
     */
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
