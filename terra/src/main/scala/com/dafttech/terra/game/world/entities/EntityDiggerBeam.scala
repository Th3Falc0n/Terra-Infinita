package com.dafttech.terra.game.world.entities

import java.util.Random

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.models.EntityThrown
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class EntityDiggerBeam(pos: Vector2d)(implicit world: World) extends EntityThrown(pos, Vector2d(4f, 2f)) {
  private val light: PointLight = new PointLight(95)
  light.setColor(new Color(0, 1, 0.3f, 1))

  setGravityFactor(0)
  isDynamicEntity = true

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("beamDig")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    light.setSize(90 + new Random().nextInt(10))

    if (Math.abs(velocity.x) <= 0.1 && Math.abs(velocity.y) <= 0.1)
      world.removeEntity(this)
  }

  override def collidesWith(e: Entity): Boolean = false

  override def onTerrainCollision(t: TilePosition): Unit = if (!t.getTile.isAir) {
    world.destroyTile(Vector2i(t.pos.x, t.pos.y), this)
      .foreach(_.addVelocity(velocity.$times(-1)))
    this.remove
  }

  override def getInAirFriction: Double = 0

  override def isLightEmitter: Boolean = true

  override def getEmittedLight: PointLight = light
}