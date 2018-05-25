package com.dafttech.terra.game.world.entities

import java.util.Random

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.models.EntityThrown
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.{Options, Resources}

class EntityDiggerBeam(pos: Vector2, world: World) extends EntityThrown(pos, world, new Vector2(4f, 2f)) {
  private val light: PointLight = new PointLight(getPosition, 95)
  light.setColor(new Color(0, 1, 0.3f, 1))

  setGravityFactor(0)
  isDynamicEntity = true

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("beamDig")

  override def update(world: World, delta: Float): Unit = {
    super.update(world, delta)

    light.setSize(90 + new Random().nextInt(10))
    light.setPosition(getPosition + (size.x * Options.BLOCK_SIZE / 2, size.y * Options.BLOCK_SIZE / 2))

    if (Math.abs(velocity.x) <= 0.1 && Math.abs(velocity.y) <= 0.1)
      worldObj.removeEntity(this)
  }

  override def collidesWith(e: Entity): Boolean = false

  override def onTerrainCollision(tile: Tile): Unit = if (!tile.isAir) {
    worldObj.destroyTile(tile.getPosition.x, tile.getPosition.y, this)
      .foreach(_.addVelocity(velocity.$times(-1)))
    this.remove
  }

  override def getInAirFriction: Double = 0

  override def isLightEmitter: Boolean = true

  override def getEmittedLight: PointLight = light
}