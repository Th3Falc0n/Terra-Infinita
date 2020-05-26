package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.entities.models.{EntityLiving, EntityThrown}
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class EntityArrow(pos: Vector2d)(implicit world: GameWorld) extends EntityThrown(pos) {
  private[entities] var stuckIn: TilePosition = _

  setGravityFactor(0.25)

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("arrow")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
  }

  override def collidesWith(e: Entity): Boolean = e.isInstanceOf[EntityLiving] && !e.isInstanceOf[Player]

  override def onTerrainCollision(tilePosition: TilePosition): Unit = {
    stuckIn = tilePosition
    setGravityFactor(0)
    //setVelocity(Vector2d(0, 0))
  }

  override def getInAirFriction: Double = 0.025
}