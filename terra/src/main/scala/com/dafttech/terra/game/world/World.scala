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
  var chunksize: Vector2i = Vector2i(32, 32)
  var time: Float = 0
  var lastTick: Float = 0
  var tickLength: Float = 0.005f
  var gen: WorldGenerator = new WorldGenerator(this)
  private val localChunks: Atomic[Map[Vector2i, Chunk]] = Atomic(Map.empty[Vector2i, Chunk])
  var weather: Weather = new WeatherRainy()
  var sunmap: SunMap = new SunMap()

  val renderer = new TileRendererMarchingSquares()

  var localPlayer: Player = new Player(Vector2d.Zero)(this)
  localPlayer.setPosition(Vector2d(0, -100))

  def getChunks: Map[Vector2i, Chunk] = localChunks.get

  def getChunk(blockInWorldPos: Vector2i): Option[Chunk] = // TODO: Option
    Option(blockInWorldPos).flatMap { blockInWorldPos =>
      val chunkPos: Vector2i = blockInWorldPos.getChunkPos(this)
      localChunks.get.get(chunkPos)
    }

  def getOrCreateChunk(blockInWorldPos: Vector2i): Chunk =
    getChunk(blockInWorldPos).getOrElse {
      val chunk = new Chunk(this, blockInWorldPos.getChunkPos(this))
      val chunkPos = blockInWorldPos.getChunkPos(this)
      localChunks.transform(_ + (chunkPos -> chunk))
      chunk.fillAir()
      gen.generateChunk(chunk)
      chunk
    }

  def getChunk(blockInWorldPos: Vector2d): Option[Chunk] =
    getChunk(Option(blockInWorldPos).map(_.toVector2i).orNull)

  def getOrCreateChunk(blockInWorldPos: Vector2d): Chunk = {
    if (blockInWorldPos != null) getOrCreateChunk(blockInWorldPos.toVector2i) else null
  }

  def doesChunkExist(x: Int, y: Int): Boolean = getChunk(Vector2i(x, y)).isDefined

  def getTile(pos: Vector2i): Tile =
    getOrCreateChunk(pos).getTile(pos.getBlockInChunkPos(this))

  def getNextTileBelow(pos: Vector2i): Option[TilePosition] = {
    var y = pos.y

    y += 1
    while (doesChunkExist(pos.x, y)) {
      val tile = Option(getTile(Vector2i(pos.x, y))).filter(!_.isAir)
      if (tile.isDefined) return Some(TilePosition(this, Vector2i(pos.x, y)))
      y += 1
    }
    None
  }

  def getNextTileAbove(pos: Vector2i): Option[TilePosition] = {
    var y = pos.y

    y -= 1
    while (doesChunkExist(pos.x, y)) {
      if (getTile(Vector2i(pos.x, y)) != null && !getTile(Vector2i(pos.x, y)).isAir) return Some(TilePosition(this, Vector2i(pos.x, y)))
      y -= 1
    }
    None
  }

  def setTile(pos: Vector2i, _tile: Tile, notify: Boolean): World = {
    var tile = _tile

    val chunk: Chunk = getOrCreateChunk(pos)

    if (chunk != null) {
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

      chunk.setTile(pos.getBlockInChunkPos(this), tile)
      tile.addSubtile(tileIndependentSubtiles: _*)

      sunmap.postTilePlace(TilePosition(this, pos))

      if (notify) {
        tile.onTileSet(TilePosition(this, pos))

        if (notifyOldRemoval != null) notifyNeighborTiles(Vector2i(notifyOldRemoval.x, notifyOldRemoval.y))
        notifyNeighborTiles(Vector2i(pos.x, pos.y))
      }
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
    entity.remove
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

    TimeKeeping.timeKeeping("Tile update")
    for (chunk <- localChunks.get.values) {
      for (entity <- chunk.getLocalEntities) {
        entity.update(delta)(TilePosition(this, entity.getPosition.toWorldPosition))
      }
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