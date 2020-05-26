package com.dafttech.terra.game.world.entities

import java.util.Random

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class EntityGlowstick(pos: Vector2d)(implicit world: GameWorld) extends Entity(pos) {
  private val light: PointLight = new PointLight(7)
  light.setColor(new Color(0.1f, 1, 0.1f, 1))

  private[entities] var gsRotation: Double = 0

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("glowstick")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    light.setSize(90 + new Random().nextInt(10))
  }

  override def getEmittedLight: PointLight = light
}