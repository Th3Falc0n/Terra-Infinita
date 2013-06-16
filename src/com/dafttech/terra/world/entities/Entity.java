package com.dafttech.terra.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawable;
import com.dafttech.terra.graphics.lighting.Light;
import com.dafttech.terra.graphics.lighting.PointLight;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.tiles.Tile;

public abstract class Entity implements IDrawable {
    protected Vector2 position = new Vector2(0, 0);
    Vector2 velocity = new Vector2(0, 0);
    Vector2 accelleration = new Vector2(0, 0);
    Vector2 size;
    World worldObj;

    boolean inAir = false;

    public Entity(Vector2 pos, World world, Vector2 s) {
        position = pos;
        worldObj = world;
        size = s;
        world.localEntities.add(this);
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

    public void setSize(float x, float y) {
        size = new Vector2(x, y);
    }

    private void drawRect(Rectangle rect, ShapeRenderer rend, Color color) {
        rend.begin(ShapeType.FilledRectangle);

        rend.setColor(color.r, color.g, color.b, 1);

        Vector2 v2 = new Vector2(rect.x, rect.y);
        v2 = v2.toRenderPosition(position);

        rend.filledRect(v2.x, v2.y, rect.width, rect.height);

        rend.flush();

        rend.end();
    }

    public void drawCollisionBoxes(World world) {
        ShapeRenderer rend = new ShapeRenderer();

        Position mid = position.toWorldPosition();

        Rectangle playerRect = new Rectangle(position.x, position.y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);

        Rectangle prLeft = new Rectangle(playerRect.x - 1, playerRect.y, 1, playerRect.height);
        Rectangle prRight = new Rectangle(playerRect.x + playerRect.width, playerRect.y, 1, playerRect.height);
        Rectangle prBottom = new Rectangle(playerRect.x, playerRect.y - 1, playerRect.width, 1);
        Rectangle prTop = new Rectangle(playerRect.x, playerRect.y + playerRect.height, playerRect.width, 1);

        Color lC = Color.WHITE;
        Color rC = Color.WHITE;
        Color bC = Color.WHITE;
        Color tC = Color.WHITE;

        for (int x = mid.getX() - 1; x <= mid.getX() + 2 + size.x; x++) {
            for (int y = mid.getY() - 1; y <= mid.getY() + 2 + size.y; y++) {
                if (world.getTile(x, y) != null && world.getTile(x, y).onCollisionWith(this)) {
                    Rectangle rect = new Rectangle(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

                    Vector2 pvMid = new Vector2(playerRect.x + playerRect.width / 2, playerRect.y + playerRect.height / 2);
                    Vector2 cvMid = new Vector2(rect.x + rect.width / 2, rect.y + rect.height / 2);
                    Vector2 mDis = (Vector2) pvMid.sub(cvMid);
                    mDis.x = Math.abs(mDis.x);
                    mDis.y = Math.abs(mDis.y);

                    Color c = Color.BLUE;

                    if (prLeft.overlaps(rect)) {
                        lC = Color.CYAN;
                    }

                    if (prRight.overlaps(rect)) {
                        rC = Color.CYAN;
                    }

                    if (prBottom.overlaps(rect)) {
                        bC = Color.CYAN;
                    }

                    if (prTop.overlaps(rect)) {
                        tC = Color.CYAN;
                    }

                    if (mDis.y + 1 >= mDis.x) {
                        c = Color.YELLOW;
                    }

                    if (mDis.y - 1 < mDis.x) {
                        c = Color.GREEN;
                    }

                    drawRect(rect, rend, c);
                }
            }
        }

        drawRect(prLeft, rend, lC);
        drawRect(prRight, rend, rC);
        drawRect(prBottom, rend, bC);
        drawRect(prTop, rend, tC);
    }

    public void checkTerrainCollisions(World world) {
        Position mid = position.toWorldPosition();

        Rectangle playerRect = new Rectangle(position.x, position.y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);

        for (int x = mid.getX() - 1; x <= mid.getX() + 2 + size.x; x++) {
            for (int y = mid.getY() - 1; y <= mid.getY() + 2 + size.y; y++) {
                if (world.getTile(x, y) != null && world.getTile(x, y).onCollisionWith(this)) {
                    Rectangle prLeft = new Rectangle(playerRect.x - 1, playerRect.y, 1, playerRect.height);
                    Rectangle prRight = new Rectangle(playerRect.x + playerRect.width, playerRect.y, 1, playerRect.height);
                    Rectangle prBottom = new Rectangle(playerRect.x, playerRect.y - 1, playerRect.width, 1);
                    Rectangle prTop = new Rectangle(playerRect.x, playerRect.y + playerRect.height, playerRect.width, 1);

                    Rectangle rect = new Rectangle(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

                    Vector2 pvMid = new Vector2(playerRect.x + playerRect.width / 2, playerRect.y + playerRect.height / 2);
                    Vector2 cvMid = new Vector2(rect.x + rect.width / 2, rect.y + rect.height / 2);
                    Vector2 mDis = (Vector2) pvMid.sub(cvMid);
                    mDis.x = Math.abs(mDis.x);
                    mDis.y = Math.abs(mDis.y);

                    if (prBottom.overlaps(rect) && (mDis.y + 1 > mDis.x)) {
                        onTerrainCollisionTop(rect.y + rect.height + 1);
                    }

                    if (prTop.overlaps(rect) && (mDis.y + 1 > mDis.x)) {
                        onTerrainCollisionBottom(rect.y - playerRect.height - 1);
                    }

                    if (prLeft.overlaps(rect) && (mDis.y - 1 < mDis.x)) {
                        onTerrainCollisionRight(rect.x + rect.width + 1);
                    }

                    if (prRight.overlaps(rect) && (mDis.y - 1 < mDis.x)) {
                        onTerrainCollisionLeft(rect.x - playerRect.width - 1);
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

    void onTerrainCollisionLeft(float x) {
        if (velocity.x > 0) {
            velocity.x = 0;
            position.x = x;
        }
    }

    void onTerrainCollisionRight(float x) {
        if (velocity.x < 0) {
            velocity.x = 0;
            position.x = x;
        }
    }

    @Override
    public void draw(AbstractScreen screen, Player player) {
        Vector2 screenVec = this.getPosition().toRenderPosition(player.getPosition());

        screen.batch.draw(this.getImage(), screenVec.x, screenVec.y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);
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
    public void update(Player player, float delta) {
        addForce(new Vector2(0, 20f));

        velocity.x += accelleration.x * delta;
        velocity.y += accelleration.y * delta;

        accelleration.setNull();

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        inAir = true;
        checkTerrainCollisions(player.getWorld());

        velocity.y *= 1 - 0.025f * delta;
        velocity.x *= 1 - getCurrentFriction() * delta;
    }

    public Tile getUndergroundTile() {
        Position pos = position.toWorldPosition();
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
