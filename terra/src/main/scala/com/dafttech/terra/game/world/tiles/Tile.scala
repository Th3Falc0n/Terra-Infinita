package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.renderer.{TileRenderer, TileRendererMarchingSquares}
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, Vector2, Vector2i}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.entities.{Entity, EntityItem}
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.ItemTile
import com.dafttech.terra.game.world.subtiles.Subtile
import com.dafttech.terra.resources.Options
import com.dafttech.terra.resources.Resources

abstract class Tile extends ItemTile with IDrawableInWorld {
  val pixmap = new Pixmap(Options.BLOCK_SIZE, Options.BLOCK_SIZE, Pixmap.Format.RGB888)
  var texture: Texture = null

  private var subtiles: List[Subtile] = List.empty

  private var breakingProgress: Float = 0
  private var hardness: Float = 1

  var receivesSunlight: Boolean = false
  var sunlightFilter: TilePosition = _

  /**
    * Defines whether this tile graphically connects with another one.
    * @param tile
    * @return
    */
  def connectsTo(tile: Tile): Boolean = true

  override def use(causer: EntityLiving, position: Vector2): Boolean =
    if (causer.getPosition.$minus(position).length < 100) {
      val pos = position.toWorldPosition
      causer.getWorld.placeTile(Vector2i(pos.x, pos.y), this, causer)
    } else
      false

  def isAir: Boolean = false

  def isReplacable: Boolean = isAir

  def isOpaque: Boolean = !isAir

  def isCollidableWith(entity: Entity): Boolean = !isAir

  def isFlammable: Boolean = false

  def isWaterproof: Boolean = isOpaque

  def isBreakable: Boolean = !isAir

  def getTemperature: Int = 0

  def getDroppedItem: Item = this

  override def spawnAsEntity(tilePosition: TilePosition): EntityItem = {
    val dropped = getDroppedItem
    if (dropped == null) null
    else new EntityItem(tilePosition.pos.toEntityPos + (0.5, 0.5), Vector2(0.5, 0.5), dropped)(tilePosition.world)
  }

  def getRenderer: TileRenderer = TileRendererMarchingSquares.$Instance

  def getWalkFriction: Float = 1

  def getWalkAcceleration: Float = 1

  def addSubtile(subtile: Subtile*): Tile = {
    subtiles = subtiles ++ subtile.toList
    this
  }

  def removeSubtile(subtile: Subtile*): Tile = {
    subtiles = subtiles.diff(subtile)
    this
  }

  def removeAndUnlinkSubtile(subtile: Subtile*): Tile = {
    subtiles = subtiles.diff(subtile)
    this
  }

  def getSubtiles = subtiles

  def hasSubtile(subtileClass: Class[_ <: Subtile], inherited: Boolean): Boolean =
    getSubtile(subtileClass, inherited) != null

  def getSubtile(subtileClass: Class[_ <: Subtile], inherited: Boolean): Subtile =
    subtiles.find(subtile =>
      (inherited && subtileClass.isAssignableFrom(subtile.getClass)) ||
        (!inherited && subtileClass == subtile.getClass)
    ).orNull

  override def draw(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit = {
    getRenderer.draw(screen, pointOfView)

    for (subtile <- subtiles) subtile.draw(screen, pointOfView)
  }

  final def tick(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    onTick(delta)

    subtiles.foreach(_.tick(delta))
  }

  def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = ()

  def onTileSet(implicit tilePosition: TilePosition): Unit = ()

  def onNeighborChange(changed: TilePosition)(implicit tilePosition: TilePosition): Unit = {
    texture = null
  }

  def onTileDestroyed(causer: Entity)(implicit tilePosition: TilePosition): Unit = ()

  def onTilePlaced(causer: Entity)(implicit tilePosition: TilePosition): Unit = ()

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    subtiles.foreach(_.update(delta))

    if (breakingProgress > 0) breakingProgress -= delta
  }

  def setHardness(hardness: Float): Tile = {
    this.hardness = hardness
    this
  }

  final def setReceivesSunlight(is: Boolean)(implicit tilePosition: TilePosition): Unit = {
    receivesSunlight = is
    if (!is) this.sunlightFilter = null
    if (!isOpaque)
      tilePosition.world.getNextTileBelow(tilePosition.pos).filter(_.getTile != this).foreach { b =>
        b.getTile.setReceivesSunlight(is)
        b.getTile.sunlightFilter = if (is) tilePosition else null
      }
  }

  def getFilterColor: Color = Color.WHITE

  final def getSunlightColor: Color =
    if (sunlightFilter == null) Color.WHITE
    else {
      var sunlightColor = sunlightFilter.getTile.getSunlightColor.cpy.mul(sunlightFilter.getTile.getFilterColor)

      for (subtile <- sunlightFilter.getTile.subtiles)
        if (subtile.providesSunlightFilter) sunlightColor = sunlightColor.cpy.mul(subtile.getFilterColor)

      sunlightColor
    }

  def damage(damage: Float, causer: Entity)(implicit tilePosition: TilePosition): Unit = {
    breakingProgress += damage
    if (breakingProgress > hardness) tilePosition.world.destroyTile(tilePosition.pos, causer)
  }
}