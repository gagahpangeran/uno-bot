package com.bot.unobot.gameengine;

public enum Direction {
    CW ("clockwise", 1),
    CCW ("counterclockwise", -1);

    private final String name;
    private final int value;

    Direction(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public Direction getOpposite() {
        return this.value == 1 ? Direction.CCW : Direction.CW;
    }
}
