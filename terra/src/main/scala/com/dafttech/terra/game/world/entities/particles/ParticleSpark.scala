package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.{Options, Resources}

class ParticleSpark(val pos: Vector2, val world: World) extends Particle(pos, world, 0.6f + (0.75f * TerraInfinita.rnd.nextFloat), new Vector2(0.5, 0.5)) {
  private val particleSize: Double = TerraInfinita.rnd.nextFloat * 0.2 + 0.1
  private val light: PointLight = new PointLight(getPosition, 55)
  light.setColor(new Color(1, 0.9f, 0.9f, 0.4f))

  setSize(new Vector2(particleSize, getSize.x))
  setHasGravity(true)
  setGravityFactor(0.05)
  setVelocity(new Vector2(4f * (0.5f - TerraInfinita.rnd.nextFloat), 4f * (0.5f - TerraInfinita.rnd.nextFloat)))

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("flame")

  override def update(world: World, delta: Float): Unit = {
    super.update(world, delta)

    setSize(new Vector2(particleSize * (lifetime / lifetimeMax), getSize.x))

    light.setSize(55 * 2 * (lifetime / lifetimeMax))
    light.setPosition(getPosition.$plus(getSize.x * Options.BLOCK_SIZE / 2, getSize.y * Options.BLOCK_SIZE / 2))
  }

  override def isLightEmitter: Boolean = true

  override def checkTerrainCollisions(world: World): Unit = ()

  override def getEmittedLight: PointLight = light
}