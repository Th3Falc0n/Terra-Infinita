package com.dafttech.terra.game.world.environment

import com.dafttech.terra.game.world.World

abstract class Weather {
  def update(world: World, delta: Float): Unit

  def getWindSpeed(world: World): Float
}