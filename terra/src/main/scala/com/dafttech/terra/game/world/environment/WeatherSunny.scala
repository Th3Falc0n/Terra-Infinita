package com.dafttech.terra.game.world.environment

import com.dafttech.terra.game.world.World

class WeatherSunny extends Weather {
  override def update(delta: Float)(implicit world: World): Unit = ()

  override def getWindSpeed(implicit world: World): Float = 0
}