package com.bot.unobot.TestCards;

/**
 * Ordinary Card
 * A class implementing the Card interface.
 */
public class OrdinaryCard implements Card {
    //Variables
    String name;
    String color;

    /**
     * Ordinary Card Constructor
     * @param name
     * @param color
     */
    public OrdinaryCard(String name, String color){
        this.name=name;
        this.color=color;
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
    public String getColor() {
        return color;
    }
}
