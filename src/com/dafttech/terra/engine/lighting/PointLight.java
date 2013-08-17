package com.dafttech.terra.engine.lighting;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Entity;

public class PointLight extends Light {

    Vector2 position = new Vector2(0, 0);
    float size;

    public PointLight(Vector2 p, float s) {
        position.set(p);
        size = s;
    }

    @Override
    public void setPosition(Vector2 p) {
        position.set(p);
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public void drawToLightmap(AbstractScreen screen, Entity pointOfView) {
        Vector2 p = position.toRenderPosition(pointOfView.getPosition());

        screen.shr.setColor(Color.WHITE);
        screen.shr.begin(ShapeType.FilledTriangle);

        float angleA = 0;
        float angleB;

        for (int i = 0; i < 47; i++) {
            angleB = angleA;
            angleA = i * 3.14159f / 23;

            float dxA = (float) Math.cos(angleA);
            float dyA = (float) Math.sin(angleA);

            float dxB = (float) Math.cos(angleB);
            float dyB = (float) Math.sin(angleB);

            screen.shr.filledTriangle(p.x, p.y, p.x + dxA * size, p.y + dyA * size, p.x + dxB * size, p.y + dyB * size);
        }

        screen.shr.end();
    }

}
