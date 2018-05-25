package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World

class ParticleDust(pos: Vector2, assignedTexture: TextureRegion)(implicit world: World) extends Particle(pos, 0.6f + (0.75f * TerraInfinita.rnd.nextFloat), Vector2.Null) {

  private val particleSize: Double = TerraInfinita.rnd.nextFloat * 0.2f + 0.25f

  setVelocity(Vector2((TerraInfinita.rnd.nextFloat - 0.5f) * 5f, (TerraInfinita.rnd.nextFloat - 1f) * 2f))
  setHasGravity(false)
  setMidPos(pos)

  override def getImage: TextureRegion = assignedTexture

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    setSize(Vector2(particleSize * (1 - (lifetime / lifetimeMax)), getSize.x))
  }

  override def checkTerrainCollisions(world: World): Unit = ()
}