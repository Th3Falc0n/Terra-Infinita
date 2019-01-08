package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class ParticleSpark(val pos: Vector2d)(implicit world: World) extends Particle(pos, 0.6f + (0.75f * TerraInfinita.rnd.nextFloat), Vector2d(0.5, 0.5)) {
  private val particleSize: Double = TerraInfinita.rnd.nextFloat * 0.2 + 0.1
  private val light: PointLight = new PointLight(55)
  light.setColor(new Color(1, 0.9f, 0.9f, 0.4f))

  setSize(Vector2d(particleSize, getSize.x))
  setHasGravity(true)
  setGravityFactor(0.05)
  setVelocity(Vector2d(4f * (0.5f - TerraInfinita.rnd.nextFloat), 4f * (0.5f - TerraInfinita.rnd.nextFloat)))

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImage("flame")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    setSize(Vector2d(particleSize * (lifetime / lifetimeMax), getSize.x))

    light.setSize(55 * 2 * (lifetime / lifetimeMax))
  }

  override def isLightEmitter: Boolean = true

  override def checkTerrainCollisions(world: World): Unit = ()

  override def getEmittedLight: PointLight = light
}