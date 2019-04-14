package com.bot.unobot.TestCards;

public class ReverseCard implements Card {
    String name;
    String color;

    public ReverseCard(String name, String color){
        this.name=name;
        this.color=color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColor() {
        return color;
    }
}
