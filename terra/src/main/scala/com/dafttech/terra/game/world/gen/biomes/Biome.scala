package com.dafttech.terra.game.world.gen.biomes

import com.dafttech.terra.game.world.Chunk
import com.dafttech.terra.game.world.gen.WorldGenerator
import com.dafttech.terra.game.world.gen.biomes.Biome._

abstract class Biome(val name: String) {
  biomes = biomes :+ this

  def generateChunk(gen: WorldGenerator, chunk: Chunk): Unit

  def populateChunk(gen: WorldGenerator, chunk: Chunk): Unit
}

object Biome {
  var biomes: List[Biome] = List.empty
}
