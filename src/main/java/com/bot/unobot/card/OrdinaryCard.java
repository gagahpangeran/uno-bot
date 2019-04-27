package com.bot.unobot.card;

/**
 * Ordinary Card
 * A class implementing the Card interface.
 */
public class OrdinaryCard implements Card {
    //Variables
    String name;
    Color color;

    /**
     * Ordinary Card Constructor
     * @param name
     * @param color
     */
    public OrdinaryCard(String name, Color color){
        this.name = name;
        this.color = color;
    }

    /**
     * Get Name
     * Return Card's name
     * @return card's name
     */
    @Override
    public String getName() {
        return name;
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
}
