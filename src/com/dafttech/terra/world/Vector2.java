package com.dafttech.terra.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

public class Vector2 extends com.badlogic.gdx.math.Vector2 {
    private static final long serialVersionUID = -7851950266404157426L;

    public Vector2(float nx, float ny) {
        x = nx;
        y = ny;
    }

    public Position toWorldPosition() {
        return new Position((int) x / BLOCK_SIZE, (int) y / BLOCK_SIZE);
    }
}
