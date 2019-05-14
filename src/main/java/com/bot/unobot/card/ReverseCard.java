package com.bot.unobot.card;

public class ReverseCard implements Card {

    Color color;
    Effect effect;
    String symbol;

    public ReverseCard(Color color){
        this.color = color;
        this.effect = Effect.REVERSE;
        this.symbol = "Reverse";
    }

    @Override
    public Effect getEffect() {
        return effect;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
