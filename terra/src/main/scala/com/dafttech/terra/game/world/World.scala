package com.dafttech.terra.game.world

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.passes.RenderingPass
import com.dafttech.terra.engine.renderer.TileRendererMarchingSquares
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.entities.{Entity, EntityItem}
import com.dafttech.terra.game.world.environment.{SunMap, Weather, WeatherRainy}
import com.dafttech.terra.game.world.gen.WorldGenerator
import com.dafttech.terra.game.world.items.persistence.GameObject
import com.dafttech.terra.game.world.subtiles.Subtile
import com.dafttech.terra.game.world.tiles.{Tile, TileAir}
import com.dafttech.terra.game.{Events, TimeKeeping}
import com.dafttech.terra.resources.Options.BLOCK_SIZE
import monix.execution.atomic.Atomic

class World(val size: Vector2i) extends GameObject {

  val tiles: Array[Array[Tile]] = Array.ofDim[Tile](size.x, size.y)

  var time: Float = 0
  var lastTick: Float = 0
  var tickLength: Float = 0.005f

  private var entities: Seq[Entity] = Seq.empty

  var gen: WorldGenerator = new WorldGenerator(this)

  var localPlayer: Player = new Player(Vector2d.Zero)(this)
  localPlayer.setPosition(Vector2d(0, -100))

  var weather: Weather = new WeatherRainy

  var sunmap: SunMap = new SunMap

  val renderer = new TileRendererMarchingSquares()

  gen.generateWorld(this)

  def getEntities: Seq[Entity] = entities

  def addEntity(entity: Entity) = {
    entities = entities :+ entity
  }

  def getTile(pos: Vector2i): Tile = {
    if(pos.x >= 0 && pos.x < size.x && pos.y >= 0 && pos.y < size.y) {
      tiles(pos.x)(pos.y)
    }
    else {
      new TileAir()
    }
  }

  def getNextTileBelow(pos: Vector2i): Option[TilePosition] = {
    var y = pos.y

    y += 1
    while (y < size.y) {
      val tile = Option(getTile(Vector2i(pos.x, y))).filter(!_.isAir)
      if (tile.isDefined) return Some(TilePosition(this, Vector2i(pos.x, y)))
      y += 1
    }
    None
  }

  def getNextTileAbove(pos: Vector2i): Option[TilePosition] = {
    var y = pos.y

    y -= 1
    while (y >= 0) {
      if (getTile(Vector2i(pos.x, y)) != null && !getTile(Vector2i(pos.x, y)).isAir) return Some(TilePosition(this, Vector2i(pos.x, y)))
      y -= 1
    }
    None
  }

  def setTile(pos: Vector2i, _tile: Tile, notify: Boolean): World = {
    var tile = _tile

    var tileIndependentSubtiles: List[Subtile] = Nil
    var notifyOldRemoval: Vector2i = null

    if (tile != null && pos != null && (getTile(pos) eq tile)) {
      notifyOldRemoval = pos
      setTile(notifyOldRemoval, null, false)
    }

    if (tile == null) tile = new TileAir

    val oldTile: Tile = getTile(pos)

    if (oldTile != null) {
      sunmap.postTileRemove(TilePosition(this, pos))
      for (subtile <- oldTile.getSubtiles)
        if (subtile.isTileIndependent)
          tileIndependentSubtiles = tileIndependentSubtiles :+ subtile
      oldTile.removeAndUnlinkSubtile(tileIndependentSubtiles: _*)
    }

    if (!Events.EVENTMANAGER.callSync(Events.EVENT_BLOCKCHANGE, tile).isCancelled)
      tiles(pos.x)(pos.y) = tile

    tile.addSubtile(tileIndependentSubtiles: _*)

    sunmap.postTilePlace(TilePosition(this, pos))

    if (notify) {
      tile.onTileSet(TilePosition(this, pos))

      if (notifyOldRemoval != null) notifyNeighborTiles(Vector2i(notifyOldRemoval.x, notifyOldRemoval.y))
      notifyNeighborTiles(Vector2i(pos.x, pos.y))
    }

    renderer.invalidateCache(TilePosition(this, pos))
    this
  }

  def placeTile(pos: Vector2i, t: Tile, causer: Entity): Boolean = {
    val tile: Tile = getTile(pos)
    if (tile.isAir || tile.isReplacable) {
      setTile(pos, t, notify = true)
      tile.onTilePlaced(causer)(TilePosition(this, pos))
      true
    } else
      false
  }

  def destroyTile(pos: Vector2i, causer: Entity): Option[EntityItem] = {
    val tile: Tile = getTile(pos)
    if (tile.isBreakable) {
      val entity = tile.spawnAsEntity(TilePosition(this, pos))
      setTile(pos, null, notify = true)
      tile.onTileDestroyed(causer)(TilePosition(this, pos))
      Some(entity)
    } else
      None
  }

  private def notifyNeighborTiles(pos: Vector2i): Unit = {
    var tile: Tile = null
    tile = getTile(pos + (1, 0))
    tile.onNeighborChange(TilePosition(this, pos))(TilePosition(this, pos + (1, 0)))
    tile = getTile(pos + (0, 1))
    tile.onNeighborChange(TilePosition(this, pos))(TilePosition(this, pos + (0, 1)))
    tile = getTile(pos - (1, 0))
    tile.onNeighborChange(TilePosition(this, pos))(TilePosition(this, pos - (1, 0)))
    tile = getTile(pos - (0, 1))
    tile.onNeighborChange(TilePosition(this, pos))(TilePosition(this, pos - (0, 1)))
  }

  def removeEntity(entity: Entity): Unit = {
    entities = entities.filter(_ != entity)
  }

  def update(delta: Float): Unit = {
    time += delta
    weather.update(delta)(this)
    val sx: Int = 25 + Gdx.graphics.getWidth / BLOCK_SIZE / 2
    val sy: Int = 25 + Gdx.graphics.getHeight / BLOCK_SIZE / 2
    var tile: Tile = null
    for {
      x <- (localPlayer.getPosition.x.toInt / BLOCK_SIZE - sx) until (localPlayer.getPosition.x.toInt / BLOCK_SIZE + sx)
      y <- (localPlayer.getPosition.y.toInt / BLOCK_SIZE - sy) until (localPlayer.getPosition.y.toInt / BLOCK_SIZE + sy)
    } {
      tile = getTile(Vector2i(x, y))
      if (tile != null) tile.update(delta)(TilePosition(this, Vector2i(x, y)))
    }

    /*var x: Int = localPlayer.getPosition.x.toInt / BLOCK_SIZE - sx
    while (x < localPlayer.getPosition.x.toInt / BLOCK_SIZE + sx) {
      {
        {
          var y: Int = localPlayer.getPosition.y.toInt / BLOCK_SIZE - sy
          while (y < localPlayer.getPosition.y.toInt / BLOCK_SIZE + sy) {
            {
              tile = getTile(x, y)
              if (tile != null) tile.update(this, delta)
            }
            {
              y += 1;
              y - 1
            }
          }
        }
      }
      {
        x += 1;
        x - 1
      }
    }*/
    TimeKeeping.timeKeeping("Tile update")
    for (entity <- entities) {
      entity.update(delta)(TilePosition(this, entity.getPosition.toWorldPosition))
    }

    TimeKeeping.timeKeeping("Entity update")
    Events.EVENTMANAGER.callSync(Events.EVENT_WORLDTICK, this, (time - lastTick).asInstanceOf[AnyRef])
    lastTick = time
    TimeKeeping.timeKeeping("Tick update")
  }

  def isInRenderRange(position: Vector2d): Boolean = true

  def draw(screen: AbstractScreen, pointOfView: Entity): Unit = {
    RenderingPass.rpBackground.applyPass(screen, pointOfView, this)
    TimeKeeping.timeKeeping("rpBack")

    RenderingPass.rpObjects.applyPass(screen, pointOfView, this)
    TimeKeeping.timeKeeping("rpObj")

    RenderingPass.rpLighting.applyPass(screen, pointOfView, this)
    TimeKeeping.timeKeeping("rpLig")

  }
}