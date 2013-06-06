package com.dafttech.terra.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawable;
import com.dafttech.terra.world.entities.Player;

public abstract class Entity implements IDrawable {
    Vector2 position = new Vector2(0, 0);
    Vector2 velocity = new Vector2(0, 0);
    Vector2 accelleration = new Vector2(0, 0);
    Vector2 size;
    World worldObj;

    boolean inAir = false;

    public Entity(Vector2 pos, World world) {
        position = pos;
        worldObj = world;
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
        rend.setColor(color);

        rend.begin(ShapeType.FilledRectangle);
        rend.filledRect(rect.x, rect.y, rect.width, rect.height);
        
        rend.flush();

        rend.end();
    }
    
    public void drawCollisionBoxes(World world) {
        ShapeRenderer rend = new ShapeRenderer();
        
        Position mid = position.toWorldPosition();

        Rectangle playerRect = new Rectangle(position.x, position.y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);

        Rectangle prLeft   = new Rectangle(playerRect.x - 1, playerRect.y, 1, playerRect.height);
        Rectangle prRight  = new Rectangle(playerRect.x + playerRect.width + 1, playerRect.y, 1, playerRect.height);
        Rectangle prBottom = new Rectangle(playerRect.x, playerRect.y - 1, playerRect.width, 1);
        Rectangle prTop    = new Rectangle(playerRect.x, playerRect.y + playerRect.height + 1, playerRect.width, 1); 
   
        
        for (int x = mid.getX() - 1; x <= mid.getX() + 1 + size.x; x++) {
            for (int y = mid.getY() - 1; y <= mid.getY() + 1 + size.y; y++) {
                if (world.getTile(x, y) != null && world.getTile(x, y).onCollisionWith(this)) {
                    Rectangle rect = new Rectangle(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    drawRect(rect, rend, Color.BLUE);
                }
            }
        }
        
        drawRect(prLeft, rend, Color.WHITE);
        drawRect(prRight, rend, Color.WHITE);
        drawRect(prBottom, rend, Color.WHITE);
        drawRect(prTop, rend, Color.WHITE);
    }
    
    public void checkTerrainCollisions(World world) {
        Position mid = position.toWorldPosition();

        Rectangle playerRect = new Rectangle(position.x, position.y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);

        Rectangle prLeft   = new Rectangle(playerRect.x - 1, playerRect.y, 1, playerRect.height);
        Rectangle prRight  = new Rectangle(playerRect.x + playerRect.width + 1, playerRect.y, 1, playerRect.height);
        Rectangle prBottom = new Rectangle(playerRect.x, playerRect.y - 1, playerRect.width, 1);
        Rectangle prTop    = new Rectangle(playerRect.x, playerRect.y + playerRect.height + 1, playerRect.width, 1);
        
        for (int x = mid.getX() - 1; x <= mid.getX() + 1 + size.x; x++) {
            for (int y = mid.getY() - 1; y <= mid.getY() + 1 + size.y; y++) {
                if (world.getTile(x, y) != null && world.getTile(x, y).onCollisionWith(this)) {
                    Rectangle rect = new Rectangle(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    
                    if (prLeft.overlaps(rect)) {
                        onCollisionLeft(rect.x + rect.width);
                    }

                    if (prRight.overlaps(rect)) {
                        onCollisionRight(rect.x - playerRect.width);
                    }

                    if (prBottom.overlaps(rect)) {
                        onCollisionBottom(rect.y + rect.height);
                    }

                    if (prTop.contains(rect)) {
                        onCollisionTop(rect.y - playerRect.height);
                    }
                }
            }
        }
    }

    void onCollisionTop(float y) {
        if (velocity.y > 0) {
            velocity.y = 0;
            position.y = y;
        }
    }

    void onCollisionBottom(float y) {
        if (velocity.y < 0) {
            velocity.y = 0;
            position.y = y;
            inAir = false;
        }
    }

    void onCollisionLeft(float x) {
        if (velocity.x < 0) {
            velocity.x = 0;
            position.x = x;
        }
    }

    void onCollisionRight(float x) {
        if (velocity.x > 0) {
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
        addForce(new Vector2(0, -20f));

        velocity.x += accelleration.x * delta;
        velocity.y += accelleration.y * delta;

        accelleration.setNull();

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        inAir = true;
        checkTerrainCollisions(player.getWorld());

        velocity.y *= 1 - 0.1f * delta;
        velocity.x *= 1 - getUndergroundFriction() * delta;
    }

    public Tile getUndergroundTile() {
        Position pos = position.toWorldPosition();
        return worldObj.getTile(pos.x, pos.y - 1);
    }

    public float getUndergroundFriction() {
        if (getUndergroundTile() != null) {
            return getUndergroundTile().getWalkFriction();
        }
        return 1;
    }

    public float getUndergroundAcceleration() {
        if (getUndergroundTile() != null) {
            return getUndergroundTile().getWalkAcceleration();
        }
        return 1;
    }

    public boolean isInRenderRange(Player player) {
        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        if (position.x >= player.getPosition().x / BLOCK_SIZE - sx && position.x <= player.getPosition().x / BLOCK_SIZE + sx
                && position.y >= player.getPosition().y / BLOCK_SIZE - sy && position.y <= player.getPosition().y / BLOCK_SIZE + sy) return true;
        return false;
    }
}
