package com.dafttech.terra.game.world.gen.biomes

import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.game.world.Chunk
import com.dafttech.terra.game.world.gen.WorldGenerator
import com.dafttech.terra.game.world.gen.calc.PerlinNoise
import com.dafttech.terra.game.world.subtiles.{SubtileBone, SubtileDryGrass}
import com.dafttech.terra.game.world.tiles.{Tile, TileSand, TileStone}

object BiomeDesert {
  var instance = new BiomeDesert("Desert")
}

class BiomeDesert(name: String) extends Biome(name) {
  override def generateChunk(gen: WorldGenerator, chunk: Chunk): Unit = {
    val noise: PerlinNoise = gen.getNoise

    val chunkPos: Vector2i = Vector2i.Null.getBlockInWorldPos(chunk)

    for (x <- chunkPos.x until chunkPos.x + chunk.world.chunksize.x) {
      val h: Int = ((1f + noise.perlinNoise(x / 150f)) * 75).toInt + 1

      for (y <- (chunkPos.y + chunk.world.chunksize.y - 1 to chunkPos.y by -1).takeWhile(y => y > h)) {
        val tile: Tile =
          if (y < (gen.world.size.y - h) / 5 + h) {
            val tile = new TileSand()
            if (y - 1 == h)
              tile.addSubtile(new SubtileDryGrass())
            if (TerraInfinita.rnd.nextDouble() < 0.002)
              tile.addSubtile(new SubtileBone())
            tile
          } else {
            val tile = new TileStone()
            if (TerraInfinita.rnd.nextDouble() < 0.004)
              tile.addSubtile(new SubtileBone())
            tile
          }

        gen.world.setTile(x, y, tile, notify = false)
      }
    }
  }

  override def populateChunk(gen: WorldGenerator, chunk: Chunk): Unit = ()
}