package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.renderer.{TileRenderer, TileRendererBlock}
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, Vector2, Vector2i}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.entities.{Entity, EntityItem}
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.subtiles.Subtile

abstract class Tile extends Item with IDrawableInWorld {
  private var world: World = _
  private var position: Vector2i = Vector2i.Null
  private var subtiles: List[Subtile] = List.empty

  private var breakingProgress: Float = 0
  private var hardness: Float = 1

  var receivesSunlight: Boolean = false
  var sunlightFilter: Tile = _

  override def use(causer: EntityLiving, position: Vector2): Boolean =
    if (causer.getPosition.$minus(position).length < 100) {
      val pos = position.toWorldPosition
      causer.getWorld.placeTile(pos.x, pos.y, this, causer)
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

  def spawnAsEntity(world: World): EntityItem = {
    val dropped = getDroppedItem
    if (dropped == null) null
    else new EntityItem(getPosition.toEntityPos + (0.5, 0.5), world, new Vector2(0.5, 0.5), dropped)
  }

  def getRenderer: TileRenderer = TileRendererBlock.$Instance

  def getWalkFriction: Float = 1

  def getWalkAcceleration: Float = 1

  def getWorld: World = world

  def addSubtile(subtile: Subtile*): Tile = {
    for (s <- subtile) if (s != null) s.setTile(this)
    subtiles = subtiles ++ subtile.toList
    this
  }

  def removeSubtile(subtile: Subtile*): Tile = {
    subtiles = subtiles.diff(subtile)
    this
  }

  def removeAndUnlinkSubtile(subtile: Subtile*): Tile = {
    for (s <- subtile) if (s != null) s.setTile(null)
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

  def setWorld(world: World): Tile = {
    this.world = world
    this
  }

  def setPosition(position: Vector2i): Tile = {
    this.position = position
    this
  }

  override def draw(pos: Vector2, world: World, screen: AbstractScreen, pointOfView: Entity): Unit = {
    getRenderer.draw2(pos, world, screen, this, pointOfView)

    for (subtile <- subtiles) subtile.draw(pos, world, screen, pointOfView)
  }

  final def tick(world: World, delta: Float): Unit = {
    onTick(world, delta)

    subtiles.foreach(_.tick(world, delta))
  }

  def onTick(world: World, delta: Float): Unit = ()

  def onNeighborChange(world: World, changed: Tile): Unit = ()

  def onTileDestroyed(world: World, causer: Entity): Unit = ()

  def onTilePlaced(world: World, causer: Entity): Unit = ()

  def onTileSet(world: World): Unit = ()

  override def update(world: World, delta: Float): Unit = {
    subtiles.foreach(_.update(delta)) // TODO Why no world???

    if (breakingProgress > 0) breakingProgress -= delta
  }

  override def update(delta: Float): Unit = ()

  def setHardness(hardness: Float): Tile = {
    this.hardness = hardness
    this
  }

  final def setReceivesSunlight(world: World, is: Boolean): Unit = {
    receivesSunlight = is
    if (!is) this.sunlightFilter = null
    if (!isOpaque) {
      val b = world.getNextTileBelow(getPosition)
      if (b != null && (b != this)) {
        b.setReceivesSunlight(world, is)
        b.sunlightFilter = if (is) this
        else null
      }
    }
  }

  def getFilterColor: Color = Color.WHITE

  final def getSunlightColor: Color =
    if (sunlightFilter == null) Color.WHITE
    else {
      var sunlightColor = sunlightFilter.getSunlightColor.cpy.mul(sunlightFilter.getFilterColor)

      for (subtile <- sunlightFilter.subtiles)
        if (subtile.providesSunlightFilter) sunlightColor = sunlightColor.cpy.mul(subtile.getFilterColor)

      sunlightColor
    }

  def damage(world: World, damage: Float, causer: Entity): Unit = {
    breakingProgress += damage
    if (breakingProgress > hardness) world.destroyTile(getPosition.x, getPosition.y, causer)
  }

  def getPosition: Vector2i = position
}