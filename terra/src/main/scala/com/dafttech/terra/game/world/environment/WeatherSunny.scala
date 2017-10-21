package com.dafttech.terra.game.world.environment

import com.dafttech.terra.game.world.World

class WeatherSunny extends Weather {
  override def update(world: World, delta: Float): Unit = ()

  override def getWindSpeed(world: World): Float = 0
}