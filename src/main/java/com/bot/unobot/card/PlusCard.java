package com.bot.unobot.card;

public class PlusCard implements Card {


    Color color;
    int plus;
    Effect effect;
    String symbol;

    public PlusCard( Color color, int plus){

        this.color=color;
        this.plus = plus;
        this.effect = Effect.PLUS;
        symbol = "+"+Integer.toString(plus);
    }

    @Override
    public Effect getEffect() {
        return effect;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public int getPlus() {
        return plus;
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
