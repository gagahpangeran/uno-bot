package com.bot.unobot.card;

public class PlusCard implements Card {

    String name;
    String color;
    int plus;

    public PlusCard(String name, String color, int plus){
        this.name=name;
        this.color=color;
        this.plus = plus;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColor() {
        return color;
    }

    public int getPlus() {
        return plus;
    }
}
