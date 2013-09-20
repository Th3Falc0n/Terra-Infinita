package com.dafttech.terra.game.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawable;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class Entity implements IDrawable {
    protected Vector2 position = new Vector2(0, 0);
    Vector2 velocity = new Vector2(0, 0);
    Vector2 accelleration = new Vector2(0, 0);
    Vector2 size;
    public World worldObj;

    Color color = Color.WHITE;

    boolean hasGravity = true;
    float gravityFactor = 1f;

    boolean inAir = false;

    public Entity(Vector2 pos, World world, Vector2 s) {
        position = pos;
        worldObj = world;
        size = s;
        world.localEntities.add(this);
    }

    public void setMidPos(Vector2 pos) {
        position = pos.addNew(-size.x * BLOCK_SIZE / 2f, -size.y * BLOCK_SIZE / 2f);
    }

    public Vector2 getMidPos() {
        return position.addNew(size.x * BLOCK_SIZE / 2f, size.y * BLOCK_SIZE / 2f);
    }

    public void setColor(Color clr) {
        color = clr;
    }

    public void setAlpha(float v) {
        color.a = v;
    }

    public Vector2 getPosition() {
        return position;
    }

    public World getWorld() {
        return worldObj;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void setHasGravity(boolean v) {
        hasGravity = v;
    }

    public void setGravityFactor(float f) {
        gravityFactor = f;
    }

    public void setSize(float x, float y) {
        size = new Vector2(x, y);
    }

    @SuppressWarnings("unused")
    private void drawRect(Rectangle rect, ShapeRenderer rend, Color color) {
        rend.begin(ShapeType.FilledRectangle);

        rend.setColor(color.r, color.g, color.b, 1);

        Vector2 v2 = new Vector2(rect.x, rect.y);
        v2 = v2.toRenderPosition(position);

        rend.filledRect(v2.x, v2.y, rect.width, rect.height);

        rend.flush();

        rend.end();
    }

    public void checkTerrainCollisions(World world) {
        Vector2i mid = position.toWorldPosition();

        Rectangle playerRect = new Rectangle(position.x, position.y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);

        for (int x = mid.getX() - 1; x <= mid.getX() + 2 + size.x; x++) {
            for (int y = mid.getY() - 1; y <= mid.getY() + 2 + size.y; y++) {
                if (world.getTile(x, y) != null && world.getTile(x, y).onCollisionWith(this)) {
                    Rectangle prLeft = new Rectangle(playerRect.x - 1, playerRect.y, 1, playerRect.height);
                    Rectangle prRight = new Rectangle(playerRect.x + playerRect.width, playerRect.y, 1, playerRect.height);
                    Rectangle prTop = new Rectangle(playerRect.x, playerRect.y - 1, playerRect.width, 1);
                    Rectangle prBottom = new Rectangle(playerRect.x, playerRect.y + playerRect.height, playerRect.width, 1);

                    Rectangle rect = new Rectangle(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

                    boolean cTop = prTop.overlaps(rect);
                    boolean cBottom = prBottom.overlaps(rect);
                    boolean cLeft = prLeft.overlaps(rect);
                    boolean cRight = prRight.overlaps(rect);

                    if (cTop && rect.y - prTop.y < rect.y - prLeft.y) {
                        onTerrainCollisionTop(rect.y + rect.height + 1);
                    }

                    if (cBottom && rect.y - prTop.y > rect.y - prLeft.y) {
                        onTerrainCollisionBottom(rect.y - playerRect.height - 1);
                    }

                    if (cLeft && rect.x - prLeft.x < rect.x - prTop.x) {
                        onTerrainCollisionLeft(rect.x + rect.width + 1);
                    }

                    if (cRight && rect.x - prLeft.x > rect.x - prTop.x) {
                        onTerrainCollisionRight(rect.x - playerRect.width - 1);
                    }
                }
            }
        }
    }

    void onTerrainCollisionBottom(float y) {
        if (velocity.y > 0) {
            velocity.y = 0;
            position.y = y;
            inAir = false;
        }
    }

    void onTerrainCollisionTop(float y) {
        if (velocity.y < 0) {
            velocity.y = 0;
            position.y = y;
        }
    }

    void onTerrainCollisionRight(float x) {
        if (velocity.x > 0) {
            velocity.x = 0;
            position.x = x;
        }
    }

    void onTerrainCollisionLeft(float x) {
        if (velocity.x < 0) {
            velocity.x = 0;
            position.x = x;
        }
    }

    @Override
    public void draw(AbstractScreen screen, Entity pointOfView) {
        Vector2 screenVec = this.getPosition().toRenderPosition(pointOfView.getPosition());

        screen.batch.setColor(color);
        screen.batch.draw(this.getImage(), screenVec.x, screenVec.y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);
        screen.batch.flush();
    }

    public abstract TextureRegion getImage();

    public Entity setPosition(Vector2 position) {
        this.position = position;
        return this;
    }

    public Entity setVelocity(Vector2 velocity) {
        this.velocity = velocity;
        return this;
    }

    public Entity addForce(Vector2 f) {
        this.accelleration.add(f);
        return this;
    }

    public Entity addVelocity(Vector2 v) {
        this.velocity.add(v);
        return this;
    }

    @Override
    public void update(float delta) {
        delta *= BLOCK_SIZE;

        if (hasGravity) addForce(new Vector2(0, 9.81f * gravityFactor));

        velocity.x += accelleration.x * delta;
        velocity.y += accelleration.y * delta;

        accelleration.setNull();

        if (velocity.len() > 0) {
            float stepLength = 5f / velocity.len();

            inAir = true;

            for (float i = 0; i < delta; i += stepLength) {
                float asl = stepLength;
                if (i + asl > delta) {
                    asl -= (i + asl) - delta;
                }

                position.x += velocity.x * asl;
                position.y += velocity.y * asl;

                checkTerrainCollisions(worldObj.localPlayer.getWorld());
            }
        }

        velocity.y *= 1 - 0.025f * delta;
        velocity.x *= 1 - getCurrentFriction() * delta;
    }

    public Tile getUndergroundTile() {
        Vector2i pos = position.toWorldPosition();
        return worldObj.getTile(pos.x, pos.y + 1);
    }

    public float getCurrentFriction() {
        if (getUndergroundTile() != null && !inAir) {
            return getUndergroundTile().getWalkFriction();
        }
        return getInAirFriction();
    }

    public float getCurrentAcceleration() {
        if (getUndergroundTile() != null && !inAir) {
            return getUndergroundTile().getWalkAcceleration();
        }
        return 1;
    }

    public float getInAirFriction() {
        return 1;
    }

    public boolean isInRenderRange(Player player) {
        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        if (position.x >= player.getPosition().x / BLOCK_SIZE - sx && position.x <= player.getPosition().x / BLOCK_SIZE + sx
                && position.y >= player.getPosition().y / BLOCK_SIZE - sy && position.y <= player.getPosition().y / BLOCK_SIZE + sy) return true;
        return false;
    }

    public boolean isLightEmitter() {
        return false;
    }

    public PointLight getEmittedLight() {
        return null;
    }

    public Vector2 getSize() {
        return size;
    }
}
