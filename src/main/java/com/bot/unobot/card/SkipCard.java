package com.bot.unobot.card;

public class SkipCard implements Card {


    Color color;
    Effect effect;
    String symbol;

    public SkipCard( Color color){

        this.color=color;
        this.effect = Effect.STOP;
        this.symbol = "Skip";

    }

    @Override
    public Effect getEffect() {
        return effect;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
