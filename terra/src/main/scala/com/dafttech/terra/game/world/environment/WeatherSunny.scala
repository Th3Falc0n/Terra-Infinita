package com.dafttech.terra.game.world.environment

import com.dafttech.terra.game.world.GameWorld

class WeatherSunny extends Weather {
  override def update(delta: Float)(implicit world: GameWorld): Unit = ()

  override def getWindSpeed(implicit world: GameWorld): Float = 0
}