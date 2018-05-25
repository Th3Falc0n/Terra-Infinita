package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Resources

class ParticleRain(pos: Vector2)(implicit world: World) extends Particle(pos, 4, Vector2(0.1, 1)) {
  setHasGravity(true)
  setGravityFactor(0.05)

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    if (!inAir) world.removeEntity(this)
  }

  override def alignToVelocity: Boolean = true

  override def getVelocityOffsetAngle: Double = 90

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("raindrop")
}