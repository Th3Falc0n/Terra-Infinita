package com.dafttech.terra.game.world.entities

import java.util.Random

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.models.EntityThrown
import com.dafttech.terra.resources.{Options, Resources}

class EntityRainbow(pos: Vector2, world: World) extends EntityThrown(pos, world, Vector2(1.5, 1.5)) {
  private val light: PointLight = new PointLight(95)
  light.setColor(new Color(1, 1, 1, 1))

  setGravityFactor(0.125)
  isDynamicEntity = true

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("rainbow")

  override def collidesWith(e: Entity): Boolean = false

  override def update(world: World, delta: Float): Unit = {
    super.update(world, delta)

    light.setSize(90 + new Random().nextInt(10))

    if (Math.abs(velocity.x) <= 0.1 && Math.abs(velocity.y) <= 0.1) worldObj.removeEntity(this)
  }

  override def isLightEmitter: Boolean = true

  override def getEmittedLight: PointLight = light
}