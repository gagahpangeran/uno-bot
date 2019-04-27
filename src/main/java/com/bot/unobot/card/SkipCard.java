package com.bot.unobot.card;

public class SkipCard implements Card {

    String name;
    Color color;

    public SkipCard(String name, Color color){
        this.name=name;
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
