package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.models.EntityThrown
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class EntityRainbow(pos: Vector2d)(implicit world: GameWorld) extends EntityThrown(pos) {
  //private val light: PointLight = new PointLight(120)
  //light.setColor(new Color(1, 1, 1, 1))
  var lightColor: Color = Color.WHITE

  setGravityFactor(0.125)
  isDynamicEntity = true

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("rainbow")

  override def collidesWith(e: Entity): Boolean = false

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    //light.setSize(90 + new Random().nextInt(10))

    if (Math.abs(body.getLinearVelocity.x) <= 0.1 && Math.abs(body.getLinearVelocity.y) <= 0.1) world.removeEntity(this)
  }

  override def isLightEmitter: Boolean = true

  override def getEmittedLight: PointLight = {
    val tmpLight = new PointLight(0)
    tmpLight.setSize(Math.min(body.getLinearVelocity.len2() * 2, 120))
    tmpLight.setColor(lightColor)
    tmpLight
  }
}