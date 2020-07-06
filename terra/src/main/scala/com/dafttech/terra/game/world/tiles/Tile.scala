package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.game.world.entities.{Entity, EntityItem}
import com.dafttech.terra.game.world.items.{Item, TileItem}
import com.dafttech.terra.game.world.items.persistence.GameObject
import com.dafttech.terra.game.world.{GameWorld, TileInstance}
import io.circe.{Decoder, Encoder}
import monix.eval.Task

abstract class Tile[Props] extends GameObject {
  implicit def propsEncoder: Encoder[Props]
  implicit def propsDecoder: Decoder[Props]

  def defaultProps: Props

  val defaultInstance: TileInstance[Props] = TileInstance(this, defaultProps)

  def isLightEmitter(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Boolean = false

  def getEmittedLight(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): PointLight = null

  def getFilterColor(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Color = Color.WHITE

  def getImage(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Task[TextureRegion]

  /**
    * Defines whether this tile graphically connects with another one.
    *
    * @param tile
    * @return
    */
  def connectsTo(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(tile: Tile[_]): Boolean = true

  def isAir(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Boolean = false

  def isReplacable(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Boolean = isAir(instance, world, pos)

  def isOpaque(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Boolean = !isAir(instance, world, pos)

  def isCollidableWith(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(entity: Entity): Boolean = !isAir(instance, world, pos)

  def isFlammable(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Boolean = false

  def isWaterproof(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Boolean = isOpaque(instance, world, pos)

  def isBreakable(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Boolean = !isAir(instance, world, pos)

  def getTemperature(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Int = 0

  def getDroppedItems(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Seq[Item] = List(TileItem(this))

  def spawnAsEntity(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(tilePosition: TilePosition): Seq[EntityItem] = {
    getDroppedItems.map { item =>
      new EntityItem(tilePosition.pos.toEntityPos + (0.5, 0.5), item)(tilePosition.world)
    }
  }

  def getWalkFriction: Float = 1

  def getWalkAcceleration: Float = 1


  def draw(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit = {
    for (subtile <- instance.subtiles) subtile.draw(screen, pointOfView)
  }

  final def tick(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    onTick(delta)

    instance.subtiles.foreach(_.tick(delta))
  }

  def onTick(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(delta: Float): Unit = ()

  def onTileSet(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Unit = ()

  def onNeighborChange(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(changed: TilePosition): Unit = ()

  def onTileDestroyed(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(causer: Entity): Unit = ()

  def onTilePlaced(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(causer: Entity): Unit = ()

  def update(instance: TileInstance[Props], world: GameWorld, pos: Vector2i)(delta: Float): Unit = {
    instance.subtiles.foreach(_.update(delta))

    if (instance.breakingProgress > 0) instance.breakingProgress -= delta
  }

  private var _hardness: Float = 1

  def hardness(instance: TileInstance[Props], world: GameWorld, pos: Vector2i): Float = _hardness

  def setHardness(hardness: Float): Tile[Props] = {
    _hardness = hardness
    this
  }
}