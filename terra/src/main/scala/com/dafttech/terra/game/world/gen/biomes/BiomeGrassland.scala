package com.dafttech.terra.game.world.gen.biomes

import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.game.world.Chunk
import com.dafttech.terra.game.world.gen.WorldGenerator
import com.dafttech.terra.game.world.subtiles.{SubtileBone, SubtileGrass}
import com.dafttech.terra.game.world.tiles._

object BiomeGrassland {
  var instance = new BiomeGrassland("Grassland")
}

class BiomeGrassland(name: String) extends Biome(name) {
  override def generateChunk(gen: WorldGenerator, chunk: Chunk): Unit = {
    val noise = gen.getNoise
    val chunkPos = Vector2i.Zero.getBlockInWorldPos(chunk)

    for (x <- chunkPos.x until chunkPos.x + chunk.world.chunksize.x) {
      val h: Int = ((1f + noise.perlinNoise(x / 150f)) * 75).toInt

      for (y <- (chunkPos.y + chunk.world.chunksize.y - 1 to chunkPos.y by -1).takeWhile(y => y > h)) {
        val tile: Tile =
          if (y - 1 == h) {
            if (TerraInfinita.rnd.nextInt(40) == 0)
              new TileLog().setLiving(true)
            else
              new TileGrass()
          } else {
            if (y < (gen.world.size.y - h) / 5 + h) {
              val tile = new TileDirt()
              if (y - 2 == h)
                tile.addSubtile(new SubtileGrass())
              tile
            } else {
              val tile = new TileStone()
              if (TerraInfinita.rnd.nextDouble() < 0.004)
                tile.addSubtile(new SubtileBone())
              tile
            }
          }

        gen.world.setTile(Vector2i(x, y), tile, notify = false)
      }
    }
  }

  override def populateChunk(gen: WorldGenerator, chunk: Chunk): Unit = ()
}