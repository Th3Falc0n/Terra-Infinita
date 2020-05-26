package com.dafttech.terra.game.world.entities

import java.util.Random

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.models.EntityThrown
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class EntityDiggerBeam(pos: Vector2d)(implicit world: GameWorld) extends EntityThrown(pos) {
  private val light: PointLight = new PointLight(6)
  light.setColor(new Color(0, 1, 0.3f, 1))

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("beamDig")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    light.setSize(90 + new Random().nextInt(10))

    if (Math.abs(body.linVelWorld.x) <= 0.1 && Math.abs(body.linVelWorld.y) <= 0.1)
      world.removeEntity(this)
  }

  override def onTerrainCollision(t: TilePosition): Unit = if (!t.getTile.isAir) {
    world.destroyTile(Vector2i(t.pos.x, t.pos.y), this)
      //.foreach(_.addVelocity(velocity.$times(-1)))
    world.removeEntity(this)
  }

  override def getEmittedLight: PointLight = light
}