package com.dafttech.terra.game.world

import java.util
import java.util.concurrent.ConcurrentHashMap

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.passes.RenderingPass
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, Vector2, Vector2i}
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.entities.{Entity, EntityItem}
import com.dafttech.terra.game.world.environment.{SunMap, Weather, WeatherRainy}
import com.dafttech.terra.game.world.gen.WorldGenerator
import com.dafttech.terra.game.world.subtiles.Subtile
import com.dafttech.terra.game.world.tiles.{Tile, TileAir}
import com.dafttech.terra.game.{Events, TimeKeeping}
import com.dafttech.terra.resources.Options.BLOCK_SIZE

import scala.collection.JavaConverters._

class World extends IDrawableInWorld {
  var size: Vector2i = new Vector2i(0, 0)
  var chunksize: Vector2i = new Vector2i(32, 32)
  var time: Float = 0
  var lastTick: Float = 0
  var tickLength: Float = 0.005f
  var gen: WorldGenerator = null
  var localChunks: util.Map[Vector2i, Chunk] = new ConcurrentHashMap[Vector2i, Chunk]
  var localPlayer: Player = null
  var weather: Weather = new WeatherRainy
  var sunmap: SunMap = new SunMap

  def this(size: Vector2) {
    this()
    this.size = Vector2i(size.x.toInt, size.y.toInt)
    gen = new WorldGenerator(this)
    println(Vector2.Null)
    localPlayer = new Player(Vector2.Null, this)
    localPlayer.setPosition(new Vector2(0, -100))
  }

  def getChunk(blockInWorldPos: Vector2i): Option[Chunk] = // TODO: Option
  Option(blockInWorldPos).flatMap {blockInWorldPos =>
    val chunkPos: Vector2i = blockInWorldPos.getChunkPos(this)
    if (localChunks.containsKey(chunkPos)) Some(localChunks.get(chunkPos)) else None
  }

  def getOrCreateChunk(blockInWorldPos: Vector2i): Chunk =
    getChunk(blockInWorldPos).getOrElse {
      val chunk = new Chunk(this, blockInWorldPos.getChunkPos(this))
      localChunks.put(blockInWorldPos.getChunkPos(this), chunk)
      chunk.fillAir()
      gen.generateChunk(chunk)
      chunk
    }

  def getChunk(blockInWorldPos: Vector2): Option[Chunk] =
    getChunk(Option(blockInWorldPos).map(_.toVector2i).orNull)

  def getOrCreateChunk(blockInWorldPos: Vector2): Chunk = {
    if (blockInWorldPos != null) getOrCreateChunk(blockInWorldPos.toVector2i) else null
  }

  def doesChunkExist(x: Int, y: Int): Boolean = getChunk(new Vector2i(x, y)).isDefined

  def getTile(x: Int, y: Int): Tile = {
    return getTile(new Vector2i(x, y))
  }

  def getTile(pos: Vector2i): Tile =
    getOrCreateChunk(pos).getTile(pos.getBlockInChunkPos(this))

  def getNextTileBelow(_x: Int, _y: Int): Option[Tile] = {
    var x = _x
    var y = _y

    y += 1
    while (doesChunkExist(x, y)) {
      if (getTile(x, y) != null && !getTile(x, y).isAir) return Some(getTile(x, y))
      y += 1
    }
    None
  }

  def getNextTileBelow(t: Vector2i): Option[Tile] = {
    getNextTileBelow(t.x, t.y)
  }

  def getNextTileAbove(_x: Int, _y: Int): Option[Tile] = {
    var x = _x
    var y = _y

    y -= 1
    while (doesChunkExist(x, y)) {
      if (getTile(x, y) != null && !getTile(x, y).isAir) return Some(getTile(x, y))
      y -= 1
    }
    None
  }

  def getNextTileAbove(t: Vector2i): Option[Tile] =
    getNextTileAbove(t.x, t.y)

  def setTile(pos: Vector2i, _tile: Tile, notify: Boolean): World = {
    var tile = _tile

    val chunk: Chunk = getOrCreateChunk(pos)
    if (chunk != null) {
      var tileIndependentSubtiles: List[Subtile] = Nil
      var notifyOldRemoval: Vector2i = null
      if (tile != null && tile.getPosition != null && (getTile(tile.getPosition) eq tile)) {
        notifyOldRemoval = tile.getPosition
        setTile(notifyOldRemoval, null, false)
      }
      if (tile == null) tile = new TileAir
      tile.setPosition(pos).setWorld(this)
      val oldTile: Tile = getTile(pos)
      if (oldTile != null) {
        sunmap.postTileRemove(this, oldTile)
        for (subtile <- oldTile.getSubtiles)
          if (subtile.isTileIndependent)
            tileIndependentSubtiles = tileIndependentSubtiles :+ subtile
        oldTile.removeAndUnlinkSubtile(tileIndependentSubtiles: _*)
      }
      chunk.setTile(pos.getBlockInChunkPos(this), tile)
      tile.addSubtile(tileIndependentSubtiles: _*)
      sunmap.postTilePlace(this, tile)
      if (notify) {
        tile.onTileSet(this)
        if (notifyOldRemoval != null) notifyNeighborTiles(notifyOldRemoval.x, notifyOldRemoval.y)
        notifyNeighborTiles(pos.x, pos.y)
      }
    }
    this
  }

  def setTile(x: Int, y: Int, tile: Tile, notify: Boolean): World =
    setTile(new Vector2i(x, y), tile, notify)

  def placeTile(x: Int, y: Int, t: Tile, causer: Entity): Boolean = {
    val tile: Tile = getTile(x, y)
    if (tile.isAir || tile.isReplacable) {
      setTile(x, y, t, notify = true)
      tile.onTilePlaced(this, causer)
      true
    } else
      false
  }

  def destroyTile(x: Int, y: Int, causer: Entity): Option[EntityItem] = {
    val tile: Tile = getTile(x, y)
    if (tile.isBreakable) {
      val entity = tile.spawnAsEntity(this)
      setTile(x, y, null, notify = true)
      tile.onTileDestroyed(this, causer)
      Some(entity)
    } else
    None
  }

  private def notifyNeighborTiles(x: Int, y: Int): Unit = {
    var tile: Tile = null
    tile = getTile(x + 1, y)
    tile.onNeighborChange(this, tile)
    tile = getTile(x, y + 1)
    tile.onNeighborChange(this, tile)
    tile = getTile(x - 1, y)
    tile.onNeighborChange(this, tile)
    tile = getTile(x, y - 1)
    tile.onNeighborChange(this, tile)
  }

  def removeEntity(entity: Entity): Unit = {
    entity.remove
  }

  def update(world: World, delta: Float): Unit = {
    time += delta
    weather.update(this, delta)
    val sx: Int = 25 + Gdx.graphics.getWidth / BLOCK_SIZE / 2
    val sy: Int = 25 + Gdx.graphics.getHeight / BLOCK_SIZE / 2
    var tile: Tile = null
    for {
      x <- (localPlayer.getPosition.x.toInt / BLOCK_SIZE - sx) until (localPlayer.getPosition.x.toInt / BLOCK_SIZE + sx)
      y <- (localPlayer.getPosition.y.toInt / BLOCK_SIZE - sy) until (localPlayer.getPosition.y.toInt / BLOCK_SIZE + sy)
    } {
      tile = getTile(x, y)
      if (tile != null) tile.update(this, delta)
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
    for (chunk <- localChunks.values.asScala) {
      for (entity <- chunk.getLocalEntities) {
        entity.update(this, delta)
      }
    }
    TimeKeeping.timeKeeping("Entity update")
    Events.EVENTMANAGER.callSync(Events.EVENT_WORLDTICK, this, (time - lastTick).asInstanceOf[AnyRef])
    lastTick = time
    TimeKeeping.timeKeeping("Tick update")
  }

  def isInRenderRange(position: Vector2): Boolean = true

  def draw(pos: Vector2, world: World, screen: AbstractScreen, pointOfView: Entity): Unit = {
    RenderingPass.rpObjects.applyPass(screen, pointOfView, this)
    TimeKeeping.timeKeeping("rpObj")
    RenderingPass.rpLighting.applyPass(screen, pointOfView, this)
    TimeKeeping.timeKeeping("rpLig")
  }
}