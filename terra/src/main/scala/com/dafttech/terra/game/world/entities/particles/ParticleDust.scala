package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.World
import monix.eval.Task

class ParticleDust(pos: Vector2d, assignedTexture: Task[TextureRegion])(implicit world: World) extends Particle(pos, 0.6f + (0.75f * TerraInfinita.rnd.nextFloat), Vector2d.Zero) {

  private val particleSize: Double = TerraInfinita.rnd.nextFloat * 0.2f + 0.25f

  setVelocity(Vector2d((TerraInfinita.rnd.nextFloat - 0.5f) * 5f, (TerraInfinita.rnd.nextFloat - 1f) * 2f))
  setHasGravity(false)
  setMidPos(pos)

  override def getImage: Task[TextureRegion] = assignedTexture

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    setSize(Vector2d(particleSize * (1 - (lifetime / lifetimeMax)), getSize.x))
  }

  override def checkTerrainCollisions(world: World): Unit = ()
}