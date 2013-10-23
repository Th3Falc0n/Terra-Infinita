package com.dafttech.terra.game.world;

public enum Facing {
    TOP(0, -1, 0), BOTTOM(1, 1, 0), LEFT(2, 0, -1), RIGHT(3, 0, 1);

    private final int index;
    public final int xOff, yOff;

    Facing(int index, int xOff, int yOff) {
        this.index = index;
        this.xOff = xOff;
        this.yOff = yOff;
    }

    public boolean isVertical() {
        return this == TOP || this == BOTTOM;
    }

    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }

    public Facing invert() {
        if (this == TOP) return BOTTOM;
        if (this == BOTTOM) return TOP;
        if (this == LEFT) return RIGHT;
        return LEFT;
    }

    public int getIndex() {
        return index;
    }
}
