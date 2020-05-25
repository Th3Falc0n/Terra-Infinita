package com.dafttech.terra.game.world.gen.biomes

import com.dafttech.terra.game.world.gen.WorldGenerator
import com.dafttech.terra.game.world.gen.biomes.Biome._

abstract class Biome(val name: String) {
  biomes = biomes :+ this

  def generateChunk(gen: WorldGenerator): Unit

  def populateChunk(gen: WorldGenerator): Unit
}

object Biome {
  var biomes: List[Biome] = List.empty
}
