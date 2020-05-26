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
  var lightColor: Color = Color.WHITE

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("rainbow")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    if (Math.abs(body.getLinearVelocity.x) <= 0.1 && Math.abs(body.getLinearVelocity.y) <= 0.1) world.removeEntity(this)
  }

  override def getEmittedLight: PointLight = {
    val tmpLight = new PointLight(0)
    tmpLight.setSize(Math.min(body.getLinearVelocity.len2() * 2, 120))
    tmpLight.setColor(lightColor)
    tmpLight
  }
}