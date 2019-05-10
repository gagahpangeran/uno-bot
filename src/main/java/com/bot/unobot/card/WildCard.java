package com.bot.unobot.card;

public class WildCard implements Card {


    Color color;
    Effect effect;
//    Color colorSetByPlayer;
    String symbol;

    public WildCard(Color color){

        this.color=color;
        this.effect = Effect.NOTHING;
//        this.colorSetByPlayer  = null;
        this.symbol = "Wild";
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

    //    public void setColorSetByPlayer( Color colorSetByPlayer) {
//        this.colorSetByPlayer = colorSetByPlayer;
//    }

//    public Color getColorSetByPlayer() {
//        return colorSetByPlayer;
//    }

}
