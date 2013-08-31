package com.dafttech.terra.engine.lighting;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.resources.Resources;

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

        screen.batch.begin();

        screen.batch.setColor(color);
        screen.batch.draw(Resources.LIGHT.getImage("pointlight"), p.x - size, p.y - size, size * 2, size * 2);

        screen.batch.end();
    }

    public Vector2 getRenderPosition() {
        return position;
    }

}
