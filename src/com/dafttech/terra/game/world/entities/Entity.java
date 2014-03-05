package com.dafttech.terra.game.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInWorld;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.Chunk;
import com.dafttech.terra.game.world.Facing;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.items.persistence.GameObject;
import com.dafttech.terra.game.world.items.persistence.Persistent;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class Entity extends GameObject implements IDrawableInWorld {
    Chunk chunk = null;
    @Persistent
    protected Vector2 position = new Vector2();

    @Persistent
    protected Vector2 velocity = new Vector2();

    protected Vector2 accelleration = new Vector2();

    @Persistent
    protected Vector2 size = new Vector2();

    @Persistent
    protected float rotation;

    public World worldObj;

    protected Color color = Color.WHITE;

    float gravityFactor = 1f;

    protected boolean inAir = false;
    protected boolean inWorld = true;

    public Entity(Vector2 pos, World world, Vector2 s) {
        worldObj = world;
        addToWorld(world, pos);
        setPosition(pos);
        size = s;
    }

    protected Entity() {

    }

    public void setMidPos(Vector2 pos) {
        setPosition(pos.addNew(-size.x * BLOCK_SIZE / 2f, -size.y * BLOCK_SIZE / 2f));
    }

    public Vector2 getMidPos() {
        return getPosition().add(size.x * BLOCK_SIZE / 2f, size.y * BLOCK_SIZE / 2f);
    }

    public void setColor(Color clr) {
        color = clr;
    }

    public void setAlpha(float v) {
        color.a = v;
    }

    public void setRotation(float angle) {
        rotation = angle;
    }

    public Vector2 getPosition() {
        return position.clone();
    }

    public Entity setPosition(Vector2 pos) {
        Chunk newChunk = worldObj.getOrCreateChunk(pos);
        if (newChunk != null && chunk != newChunk) {
            addToWorld(newChunk, pos);
            onRechunk(newChunk, pos);
        }
        position.set(pos);
        return this;
    }

    public void onRechunk(Chunk newChunk, Vector2 pos) {

    }

    public boolean remove() {
        if (chunk != null) {
            inWorld = false;
            return chunk.removeEntity(this);
        }
        return false;
    }

    private void addToWorld(World world, Vector2 pos) {
        addToWorld(world.getOrCreateChunk(pos), pos);
    }

    private void addToWorld(Chunk chunk, Vector2 pos) {
        remove();
        inWorld = true;
        if (chunk.addEntity(this)) this.chunk = chunk;
    }

    public World getWorld() {
        return worldObj;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void setHasGravity(boolean v) {
        if (!v) gravityFactor = 0;
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
        v2 = v2.toRenderPosition(getPosition());

        rend.filledRect(v2.x, v2.y, rect.width, rect.height);

        rend.flush();

        rend.end();
    }

    public boolean collidesWith(Entity e) {
        return true;
    }

    public void checkEntityCollisions() {
        Rectangle playerRect, otherRect;

        Vector2 oVel = velocity.clone();

        for (Entity entity : chunk.getLocalEntities()) {
            if (entity == this || !(entity.collidesWith(this) && this.collidesWith(entity)) || velocity.len2() < entity.velocity.len2()) {
                continue;
            }
            otherRect = new Rectangle(entity.getPosition().x, entity.getPosition().y, entity.getSize().x * BLOCK_SIZE, entity.getSize().y
                    * BLOCK_SIZE);
            playerRect = new Rectangle(getPosition().x, getPosition().y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);

            if (collisionDetect(oVel, playerRect, otherRect)) {
                onEntityCollision(entity);
            }
        }
    }

    public void checkTerrainCollisions(World world) {
        Vector2i mid = getPosition().toWorldPosition();

        Rectangle playerRect, tileRect;

        Vector2 oVel = velocity.clone();

        for (int x = mid.getX() - 1; x <= mid.getX() + 2 + size.x; x++) {
            for (int y = mid.getY() - 1; y <= mid.getY() + 2 + size.y; y++) {
                if (world.getTile(x, y) != null && world.getTile(x, y).isCollidableWith(this)) {
                    tileRect = new Rectangle(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    playerRect = new Rectangle(getPosition().x, getPosition().y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);

                    if (collisionDetect(oVel, playerRect, tileRect)) {
                        onTerrainCollision(world.getTile(x, y));
                    }
                }
            }
        }
    }

    public void onTerrainCollision(Tile t) {

    }

    public void onEntityCollision(Entity e) {

    }

    public boolean collisionDetect(Vector2 oVel, Rectangle a, Rectangle b) {
        if (a.overlaps(b)) {
            Facing fVertical = null, fHorizontal = null;
            float distVertical = 0, distHorizontal = 0;
            float posVertical = 0, posHorizontal = 0;
            
            boolean hcv = false, hch = false;

            if (oVel.y > 0) {
                fVertical = Facing.BOTTOM;
                distVertical = (a.y + a.height) - b.y;
                posVertical = b.y - 0.01f - a.height;
                
                hcv = true;
            } else if (oVel.y < 0) {
                fVertical = Facing.TOP;
                distVertical = (b.y + b.height) - a.y;
                posVertical = (b.y + b.height) + 0.01f;
                
                hcv = true;
            }

            if (oVel.x > 0) {
                fHorizontal = Facing.RIGHT;
                distHorizontal = (a.x + a.width) - b.x;
                posHorizontal = b.x - 0.01f - a.width;
                
                hch = true;
            } else if(oVel.x < 0) {
                fHorizontal = Facing.LEFT;
                distHorizontal = (b.x + b.width) - a.x;
                posHorizontal = (b.x + b.width) + 0.01f;
                
                hch = true;
            }
            
            if((hcv && !hch) || ((hcv && hch) && distVertical < distHorizontal)) {
                collisionResponse(fVertical, posVertical);
                return true;
            }
            
            if((hch && !hcv) || ((hcv && hch) && !(distVertical < distHorizontal))) {
                collisionResponse(fHorizontal, posHorizontal);
                return true;
            }
        }

        return false;
    }

    public void collisionResponse(Facing facing, float val) {
        if (facing.isVertical()) {
            velocity.y = 0;
            setPosition(getPosition().setY(val));
        } else {
            velocity.x = 0;
            setPosition(getPosition().setX(val));
        }
        if (facing == Facing.BOTTOM) inAir = false;
    }

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Entity pointOfView) {
        Vector2 screenVec = this.getPosition().toRenderPosition(pointOfView.getPosition());

        screen.batch.setColor(color);
        screen.batch.draw(this.getImage(), screenVec.x, screenVec.y, BLOCK_SIZE * size.x / 2, BLOCK_SIZE * size.y / 2, BLOCK_SIZE * size.x,
                BLOCK_SIZE * size.y, 1, 1, rotation);
    }

    public abstract TextureRegion getImage();

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
    public void update(World world, float delta) {
        delta *= BLOCK_SIZE;

        if (gravityFactor != 0) addForce(new Vector2(0, 9.81f * gravityFactor));

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

                setPosition(getPosition().add(velocity.mulNew(asl)));

                if (!inWorld) {
                    return;
                }

                checkTerrainCollisions(worldObj.localPlayer.getWorld());
                checkEntityCollisions();
            }
        }

        velocity.y *= 1 - 0.025f * delta;
        velocity.x *= 1 - getCurrentFriction() * delta;
    }

    public Tile getUndergroundTile() {
        Vector2i pos = position.addNew(size.x * BLOCK_SIZE, size.y * BLOCK_SIZE).toWorldPosition();
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

    public final void tick(World world) {
        onTick(world);
    }

    public void onTick(World world) {

    }
}
