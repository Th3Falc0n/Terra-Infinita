package com.dafttech.terra.game.world.entities

import java.util.Random

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.{Options, Resources}

class EntityGlowstick(pos: Vector2, world: World) extends Entity(pos, world, Vector2(1.5, 1.5)) {
  private val light: PointLight = new PointLight(getPosition, 95)
  light.setColor(new Color(0.1f, 1, 0.1f, 1))

  private[entities] var gsRotation: Double = 0

  setGravityFactor(0.125)

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("glowstick")

  override def collidesWith(e: Entity): Boolean = false

  override def update(world: World, delta: Float): Unit = {
    super.update(world, delta)

    gsRotation += velocity.x * delta * 50

    light.setSize(90 + new Random().nextInt(10))
    light.setPosition(getPosition.$plus(size.x * Options.BLOCK_SIZE / 2, size.y * Options.BLOCK_SIZE / 2))

    setRotation((velocity.rotation + Math.PI / 2 + gsRotation).toFloat)
  }

  override def getInAirFriction: Double = 0.025

  override def isLightEmitter: Boolean = true

  override def getEmittedLight: PointLight = light
}