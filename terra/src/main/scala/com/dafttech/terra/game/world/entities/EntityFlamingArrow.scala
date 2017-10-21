package com.dafttech.terra.game.world.entities

import java.util.Random

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.particles.ParticleSpark
import com.dafttech.terra.game.world.tiles.{Tile, TileFire}
import com.dafttech.terra.resources.{Options, Resources}

class EntityFlamingArrow(pos: Vector2, world: World) extends EntityArrow(pos, world) {
  private val light: PointLight = new PointLight(getPosition, 95)
  light.setColor(new Color(255, 200, 40, 255))

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("arrow")

  override def update(world: World, delta: Float): Unit = {
    super.update(world, delta)

    light.setPosition(getPosition.$plus(size.x * Options.BLOCK_SIZE / 2, size.y * Options.BLOCK_SIZE / 2))
    light.setSize(90 + new Random().nextInt(10))

    for (_ <- 0 until 5)
      if (TerraInfinita.rnd.nextDouble < delta * velocity.length * 0.5)
        new ParticleSpark(getPosition, worldObj)
  }

  override def onTerrainCollision(tile: Tile): Unit = {
    super.onTerrainCollision(tile)
    placeBlockOnHit(tile.getPosition.x, tile.getPosition.y)
  }

  def placeBlockOnHit(x: Int, y: Int): Unit = TileFire.createFire(worldObj, x, y)

  override def getInAirFriction: Double = 0.025

  override def isLightEmitter: Boolean = true

  override def getEmittedLight: PointLight = light
}