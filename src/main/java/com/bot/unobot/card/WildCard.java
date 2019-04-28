package com.bot.unobot.card;

public class WildCard implements Card {

    String name;
    Color color;

    public WildCard(Color color){
        this.name="Wildcard";
        this.color=color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
