package com.bot.unobot.TestCards;

public class WildCard implements Card {

    String name;
    String color;

    public WildCard( String color){
        this.name="Wildcard";
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
