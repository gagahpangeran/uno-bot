package com.bot.unobot.TestCards;

public class SkipCard implements Card {

    String name;
    String color;

    public SkipCard(String name, String color){
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
