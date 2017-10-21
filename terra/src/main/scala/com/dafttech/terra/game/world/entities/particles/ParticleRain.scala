package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Resources

class ParticleRain(pos: Vector2, world: World) extends Particle(pos, world, 4, new Vector2(0.1, 1)) {
  setHasGravity(true)
  setGravityFactor(0.05)

  override def update(world: World, delta: Float): Unit = {
    super.update(world, delta)
    if (!inAir) world.removeEntity(this)
  }

  override def alignToVelocity: Boolean = true

  override def getVelocityOffsetAngle: Double = 90

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("raindrop")
}