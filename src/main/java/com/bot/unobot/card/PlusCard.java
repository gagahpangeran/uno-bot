package com.bot.unobot.card;

public class PlusCard implements Card {

    String name;
    Color color;
    int plus;

    public PlusCard(String name, Color color, int plus){
        this.name=name;
        this.color=color;
        this.plus = plus;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public int getPlus() {
        return plus;
    }
}
