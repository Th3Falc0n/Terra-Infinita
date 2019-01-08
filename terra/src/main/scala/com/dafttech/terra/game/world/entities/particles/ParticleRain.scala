package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class ParticleRain(pos: Vector2d)(implicit world: World) extends Particle(pos, 4, Vector2d(0.1, 1)) {
  setHasGravity(true)
  setGravityFactor(0.05)

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    if (!inAir) world.removeEntity(this)
  }

  override def alignToVelocity: Boolean = true

  override def getVelocityOffsetAngle: Double = 90

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImage("raindrop")
}