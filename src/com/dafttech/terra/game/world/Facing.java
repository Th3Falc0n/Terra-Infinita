package com.dafttech.terra.game.world;

public enum Facing {
    TOP(0, 0, -1), BOTTOM(1, 0, 1), LEFT(2, -1, 0), RIGHT(3, 1, 0);

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
