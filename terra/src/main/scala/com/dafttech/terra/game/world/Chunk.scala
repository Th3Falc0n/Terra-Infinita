package com.dafttech.terra.game.world

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, Vector2, Vector2i}
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.gen.biomes.{Biome, BiomeDesert, BiomeGrassland}
import com.dafttech.terra.game.world.tiles.{Tile, TileAir}
import com.dafttech.terra.resources.Options.BLOCK_SIZE

import scala.util.Random

class Chunk(val world: World, val pos: Vector2i) {
  val map: Array[Array[Tile]] = Array.ofDim[Tile](world.chunksize.x, world.chunksize.y)
  private var localEntities: Seq[Entity] = Seq.empty
  var stayLoaded: Boolean = false

  def fillAir(): Unit =
    for {
      y <- map(0).indices
      x <- map.indices
    } world.setTile(Vector2i(x, y).getBlockInWorldPos(this), new TileAir, notify = false)

  @deprecated def update(delta: Float) {
    var tile: Tile = null

    for {
      y <- 0 until world.chunksize.y
      x <- 0 until world.chunksize.x
    } {
      tile = getTile(Vector2i(x, y))
      if (tile != null) tile.update(delta)(TilePosition(world, Vector2i(x, y)))
    }

    for (entity <- localEntities) {
      entity.update(delta)(TilePosition(world, entity.getPosition.toWorldPosition))
      if (entity.getPosition.x < -100 || entity.getPosition.x > world.size.x * BLOCK_SIZE + 100 || entity.getPosition.y > world.size.y * BLOCK_SIZE + 100) {
        world.removeEntity(entity)
      }
    }
  }

  def draw(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit = {
    var tile: Tile = null

    for {
      y <- 0 until world.chunksize.y
      x <- 0 until world.chunksize.x
    } {
      tile = getTile(Vector2i(x, y))
      if (tile != null) tile.draw(screen, pointOfView)(TilePosition(world, pos))
    }
  }

  @SuppressWarnings(Array("unused")) def getBiome: Biome = {
    if (Random.nextBoolean) BiomeGrassland.instance
    else BiomeDesert.instance
  }

  private def checkPosInChunk(blockInChunkPos: Vector2i): Unit =
    if (!blockInChunkPos.isInRect(0, 0, world.chunksize.x, world.chunksize.y))
      throw new RuntimeException("Position out of Chunk!")

  def getTile(blockInChunkPos: Vector2i): Tile = {
    checkPosInChunk(blockInChunkPos)

    map(blockInChunkPos.x)(blockInChunkPos.y)
  }

  def setTile(blockInChunkPos: Vector2i, tile: Tile): Chunk = {
    checkPosInChunk(blockInChunkPos)

    if (!Events.EVENTMANAGER.callSync(Events.EVENT_BLOCKCHANGE, tile).isCancelled)
      map(blockInChunkPos.x)(blockInChunkPos.y) = tile

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