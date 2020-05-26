package com.dafttech.terra.game.world.tiles

import java.util.Random

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.{GameWorld, TileInstance}
import com.dafttech.terra.resources.Resources
import monix.eval.Task

object TileFire {
  def createFire(tilePosition: TilePosition): Boolean = {
    val spreadTile = tilePosition.getTile

    if (spreadTile.tile.isFlammable || areSurroundingTilesFlammable(tilePosition)) {
      tilePosition.world.setTile(tilePosition.pos, TileInstance(new TileFire), notify = true)
      true
    }
    else if (spreadTile.tile.isReplacable) {
      tilePosition.world.setTile(tilePosition.pos, TileInstance(new TileFire().dontSpread), notify = true)
      true
    } else
      false
  }

  def areSurroundingTilesFlammable(tilePosition: TilePosition): Boolean =
    tilePosition.world.getTile(tilePosition.pos + (1, 0)).tile.isFlammable ||
      tilePosition.world.getTile(tilePosition.pos - (1, 0)).tile.isFlammable ||
      tilePosition.world.getTile(tilePosition.pos + (0, 1)).tile.isFlammable ||
      tilePosition.world.getTile(tilePosition.pos - (0, 1)).tile.isFlammable
}

class TileFire extends TileFalling {
  private val light = new PointLight(95)
  private var spreadCounter: Float = 0.2f
  private val spreadCounterMax: Float = 0.2f
  private var lifetime: Float = 0
  private val spreadDistance: Int = 3
  private val speedMod: Float = 0.05f
  private var spread: Boolean = true

  override val getImage: Task[TextureRegion] = Resources.TILES.getImageTask("fire")

  override def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.onTick(delta)

    if (lifetime == 0) lifetime = new Random().nextFloat * 2f + 1
    spreadCounter -= speedMod
    lifetime -= speedMod

    if (spread && spreadCounter <= 0) {
      val spreadPosition = tilePosition.pos + (new Random().nextInt(spreadDistance * 2) - spreadDistance, new Random().nextInt(spreadDistance * 2) - spreadDistance)
      val spreadTile = tilePosition.world.getTile(spreadPosition)
      if (spreadTile.tile.isFlammable) {
        spreadCounter = spreadCounterMax
        tilePosition.world.setTile(spreadPosition, TileInstance(new TileFire()), notify = true)
      }
    }
    if (lifetime <= 0) tilePosition.setTile(null)
  }

  def dontSpread: TileFire = {
    spread = false
    this
  }

  override def isOpaque: Boolean = false

  override def getTemperature: Int = 100

  override def isCollidableWith(entity: Entity): Boolean = {
    entity match {
      case entityLiving: EntityLiving =>
        entityLiving.damage(0.01f)

      case _ =>
    }
    false
  }

  override def isLightEmitter = true

  override def getEmittedLight: PointLight = light

  override def getFallSpeed(world: GameWorld): Float = 10

  override def getFallDelay(world: GameWorld): Float = 0.2F
}