package com.bot.unobot.card;

public class ReverseCard implements Card {
    String name;
    Color color;

    public ReverseCard(String name, Color color){
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
