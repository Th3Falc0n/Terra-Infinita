package com.dafttech.terra.game.world

import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, Vector2, Vector2i}
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.gen.biomes.{Biome, BiomeDesert, BiomeGrassland}
import com.dafttech.terra.game.world.tiles.{Tile, TileAir}
import com.dafttech.terra.resources.Options.BLOCK_SIZE

import scala.util.Random

class Chunk extends IDrawableInWorld {
  var world: World = null
  var pos: Vector2i = null
  @volatile
  var map: Array[Array[Tile]] = null
  private var localEntities: Seq[Entity] = Seq.empty
  var stayLoaded: Boolean = false

  def this(world: World, chunkPos: Vector2i) {
    this()
    this.world = world
    this.pos = chunkPos
    this.map = Array.ofDim[Tile](world.chunksize.x, world.chunksize.y)
  }

  def this(world: World, chunkPos: Vector2) {
    this(world, chunkPos.toWorldPosition)
  }

  def fillAir(): Unit =
    for {
      y <- map(0).indices
      x <- map.indices
    } world.setTile(Vector2i(x, y).getBlockInWorldPos(this), new TileAir(), notify = false)

  @deprecated def update(world: World, delta: Float) {
    var tile: Tile = null

    for {
      y <- 0 until world.chunksize.y
      x <- 0 until world.chunksize.x
    } {
      tile = getTile(Vector2i(x, y))
      if (tile != null) tile.update(world, delta)
    }

    for (entity <- localEntities) {
      entity.update(world, delta)
      if (entity.getPosition.x < -100 || entity.getPosition.x > world.size.x * BLOCK_SIZE + 100 || entity.getPosition.y > world.size.y * BLOCK_SIZE + 100) {
        world.removeEntity(entity)
      }
    }
  }

  def draw(pos: Vector2, world: World, screen: AbstractScreen, pointOfView: Entity): Unit = {
    var tile: Tile = null

    for {
      y <- 0 until world.chunksize.y
      x <- 0 until world.chunksize.x
    } {
      tile = getTile(Vector2i(x, y))
      if (tile != null) tile.draw(pos, world, screen, pointOfView)
    }
  }

  @SuppressWarnings(Array("unused")) def getBiome: Biome = {
    if (Random.nextBoolean) BiomeGrassland.instance else BiomeDesert.instance
  }

  def getTile(blockInChunkPos: Vector2i): Tile = {
    if (blockInChunkPos.isInRect(0, 0, world.chunksize.x, world.chunksize.y)) {
      return map(blockInChunkPos.x)(blockInChunkPos.y)
    }
    null
  }

  def setTile(blockInChunkPos: Vector2i, tile: Tile): Chunk = {
    if (blockInChunkPos.isInRect(0, 0, world.chunksize.x, world.chunksize.y)) {
      if (!Events.EVENTMANAGER.callSync(Events.EVENT_BLOCKCHANGE, tile).isCancelled) map(blockInChunkPos.x)(blockInChunkPos.y) = tile
    }
    this
  }

  def removeEntity(entity: Entity): Boolean = {
    localEntities = localEntities.filter(_ != entity)
    true
  }

  def addEntity(entity: Entity): Boolean = {
    localEntities = localEntities :+ entity
    true
  }

  def getLocalEntities: Seq[Entity] = localEntities
}