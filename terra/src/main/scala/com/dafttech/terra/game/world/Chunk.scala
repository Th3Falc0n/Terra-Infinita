package com.dafttech.terra.game.world

import java.util.{ArrayList, List, Random}

import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, Vector2}
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.gen.biomes.{Biome, BiomeDesert, BiomeGrassland}
import com.dafttech.terra.game.world.tiles.{Tile, TileAir}
import com.dafttech.terra.resources.Options.BLOCK_SIZE

class Chunk extends IDrawableInWorld {
  var world: World = null
  var pos: Vector2i = null
  @volatile
  var map: Array[Array[Tile]] = null
  private var localEntities: List[Entity] = new ArrayList[Entity]
  var stayLoaded: Boolean = false

  def this(world: World, chunkPos: Vector2i) {
    this()
    this.world = world
    this.pos = chunkPos
    this.map = Array.ofDim[Tile](world.chunksize.x, world.chunksize.y)
  }

  def this(world: World, chunkPos: Vector2) {
    this(world, new Vector2i(chunkPos))
  }

  def fillAir {
    {
      var y: Int = 0
      while (y < map(0).length) {
        {
          {
            var x: Int = 0
            while (x < map.length) {
              {
                world.setTile(new Vector2i(x, y).getBlockInWorldPos(this), new TileAir, false)
              }
              ({
                x += 1;
                x - 1
              })
            }
          }
        }
        ({
          y += 1;
          y - 1
        })
      }
    }
  }

  @deprecated def update(world: World, delta: Float) {
    var tile: Tile = null
    val pos: Vector2i = new Vector2i
    pos.y = 0
    while (pos.y < world.chunksize.y) {
      pos.x = 0
      while (pos.x < world.chunksize.x) {
        tile = getTile(pos)
        if (tile != null) tile.update(world, delta)

        pos.x += 1
        pos.x - 1
      }
      pos.y += 1
      pos.y - 1
    }

    import scala.collection.JavaConversions._
    for (entity <- localEntities) {
      entity.update(world, delta)
      if (entity.getPosition.x < -100 || entity.getPosition.x > world.size.x * BLOCK_SIZE + 100 || entity.getPosition.y > world.size.y * BLOCK_SIZE + 100) {
        world.removeEntity(entity)
      }
    }
  }

  def draw(pos: Vector2, world: World, screen: AbstractScreen, pointOfView: Entity) {
    var tile: Tile = null
    val tilePos: Vector2i = new Vector2i {
      y = 0

      while (y < world.chunksize.y) {
        {
          {
            x = 0
            while (x < world.chunksize.x) {
              {
                tile = getTile(this)
                if (tile != null) tile.draw(pos, world, screen, pointOfView)
              }
              ({
                x += 1;
                x - 1
              })
            }
          }
        }
        ({
          y += 1;
          y - 1
        })
      }
    }
  }

  @SuppressWarnings(Array("unused")) def getBiome: Biome = {
    return if (new Random().nextBoolean || true) BiomeGrassland.instance else BiomeDesert.instance
  }

  def getTile(blockInChunkPos: Vector2i): Tile = {
    if (blockInChunkPos.isInRect(0, 0, world.chunksize.x, world.chunksize.y)) {
      return map(blockInChunkPos.x)(blockInChunkPos.y)
    }
    return null
  }

  def setTile(blockInChunkPos: Vector2i, tile: Tile): Chunk = {
    if (blockInChunkPos.isInRect(0, 0, world.chunksize.x, world.chunksize.y)) {
      if (!Events.EVENTMANAGER.callSync(Events.EVENT_BLOCKCHANGE, tile).isCancelled) map(blockInChunkPos.x)(blockInChunkPos.y) = tile
    }
    return this
  }

  def removeEntity(entity: Entity): Boolean = {
    return localEntities.remove(entity)
  }

  def addEntity(entity: Entity): Boolean = {
    return localEntities.add(entity)
  }

  def getLocalEntities: Array[Entity] = {
    return localEntities.toArray(new Array[Entity](localEntities.size))
  }
}