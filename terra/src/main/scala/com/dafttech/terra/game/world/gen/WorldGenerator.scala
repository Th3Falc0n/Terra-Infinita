package com.dafttech.terra.game.world.gen

import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.game.world.{Chunk, World}
import com.dafttech.terra.game.world.gen.calc.PerlinNoise

class WorldGenerator(val world: World) {
  var noise: PerlinNoise = new PerlinNoise(TerraInfinita.rnd.nextInt, 9, 0.48f)

  def generateChunk(chunk: Chunk): Unit = {
    chunk.getBiome.generateChunk(this, chunk)
    chunk.getBiome.populateChunk(this, chunk)
  }

  def getNoise: PerlinNoise = noise
}