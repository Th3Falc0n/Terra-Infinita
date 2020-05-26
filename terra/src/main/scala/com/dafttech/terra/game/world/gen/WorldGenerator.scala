package com.dafttech.terra.game.world.gen

import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.game.world.gen.calc.PerlinNoise
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.subtiles.SubtileBone
import com.dafttech.terra.game.world.tiles.{ Tile, TileAir, TileDirt, TileGrass, TileLog, TileStone }

class WorldGenerator(val world: GameWorld) {
  var noise: PerlinNoise = new PerlinNoise(TerraInfinita.rnd.nextInt, 9, 0.48f)

  def generateWorld(world: GameWorld): Unit = {
    for (x <- 0 until world.size.x) {
      val h: Int = math.min(world.size.y, math.max(0, ((4f + noise.perlinNoise(x / 150f)) * (world.size.y / 6)).toInt))
      //val h: Int = 500

      for (y <- 0 until world.size.y - h) {
        world.setTile(Vector2i(x, y), new TileAir, notify = false)
      }

      for (y <- world.size.y - h until world.size.y) {
        val tile: Tile =
          if (y - 1 == h) {
            if (TerraInfinita.rnd.nextInt(40) == 0)
              new TileLog().setLiving(true)
            else
              new TileGrass()
          } else {
            if (y < (world.size.y - h) / 5 + h) {
              val tile = new TileDirt()
              /*if (y - 2 == h)
                tile.addSubtile(new SubtileGrass())*/
              tile
            } else {
              val tile = new TileStone()
              if (TerraInfinita.rnd.nextDouble() < 0.004)
                tile.addSubtile(new SubtileBone())
              tile
            }
          }

        world.setTile(Vector2i(x, y), tile, notify = false)
      }
    }
  }

  def getNoise: PerlinNoise = noise
}