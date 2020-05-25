package com.dafttech.terra.game.world.environment

import com.badlogic.gdx.Gdx
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.particles.ParticleRain

class WeatherRainy extends Weather {
  override def update(delta: Float)(implicit world: GameWorld): Unit = {
    val top = world.localPlayer.getPosition - (0, Gdx.graphics.getHeight / 2)

    if (delta * 60f * TerraInfinita.rnd.nextFloat > 0.5f)
      new ParticleRain(
        top + ((TerraInfinita.rnd.nextFloat - 0.5f) * Gdx.graphics.getWidth, 0),
      ).setVelocity(Vector2d((TerraInfinita.rnd.nextFloat - 0.1 /* Rain direction */) * 25, (TerraInfinita.rnd.nextFloat + 5) * 15))
  }

  override def getWindSpeed(implicit world: GameWorld): Float = 0
}