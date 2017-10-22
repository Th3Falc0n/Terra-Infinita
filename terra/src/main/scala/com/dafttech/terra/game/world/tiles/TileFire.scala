package com.dafttech.terra.game.world.tiles

import java.util.Random

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.{Options, Resources}

object TileFire {
  def createFire(world: World, x: Int, y: Int): Boolean = {
    val spreadTile = world.getTile(x, y)

    if (spreadTile.isFlammable || areSurroundingTilesFlammable(world, x, y)) {
      world.setTile(x, y, new TileFire, notify = true)
      true
    }
    else if (spreadTile.isReplacable) {
      world.setTile(x, y, new TileFire().dontSpread, notify = true)
      true
    } else
      false
  }

  def areSurroundingTilesFlammable(world: World, x: Int, y: Int): Boolean =
    world.getTile(x + 1, y).isFlammable ||
      world.getTile(x - 1, y).isFlammable ||
      world.getTile(x, y + 1).isFlammable ||
      world.getTile(x, y - 1).isFlammable
}

class TileFire extends TileFalling {
  private val light = new PointLight(getPosition.toEntityPos, 95)
  private var spreadCounter: Float = 0.2f
  private val spreadCounterMax: Float = 0.2f
  private var lifetime: Float = 0
  private val spreadDistance: Int = 3
  private val speedMod: Float = 0.05f
  private var spread: Boolean = true

  override def getImage: TextureRegion = Resources.TILES.getImage("fire")

  override def onTick(world: World, delta: Float): Unit = {
    super.onTick(world, delta)

    if (lifetime == 0) lifetime = new Random().nextFloat * 2f + 1
    spreadCounter -= speedMod
    lifetime -= speedMod

    if (spread && spreadCounter <= 0) {
      val spreadPosition = getPosition + (new Random().nextInt(spreadDistance * 2) - spreadDistance, new Random().nextInt(spreadDistance * 2) - spreadDistance)
      val spreadTile = world.getTile(spreadPosition)
      if (spreadTile.isFlammable) {
        spreadCounter = spreadCounterMax
        world.setTile(spreadPosition, new TileFire, notify = true)
      }
    }
    if (lifetime <= 0) world.setTile(getPosition, null, notify = true)

    light.setPosition(getPosition.toEntityPos + (Options.BLOCK_SIZE / 2, Options.BLOCK_SIZE / 2))
  }

  def dontSpread: TileFire = {
    spread = false
    this
  }

  override def isOpaque: Boolean = false

  override def getTemperature: Int = 100

  override def isCollidableWith(entity: Entity): Boolean = {
    if (entity.isInstanceOf[EntityLiving]) entity.asInstanceOf[EntityLiving].damage(0.01f)
    false
  }

  override def isLightEmitter = true

  override def getEmittedLight: PointLight = light

  override def getFallSpeed(world: World): Float = 10

  override def getFallDelay(world: World): Float = 0.2F
}