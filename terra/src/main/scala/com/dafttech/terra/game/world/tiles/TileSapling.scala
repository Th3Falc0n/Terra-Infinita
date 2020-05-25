package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class TileSapling extends TileFalling {
  private var grothDelay: Float = 4

  override val getImage: Task[TextureRegion] = Resources.TILES.getImageTask("sapling")

  override def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.onTick(delta)
    if (grothDelay <= 0) tilePosition.world.setTile(tilePosition.pos, new TileLog().setLiving(true), notify = true)
    else grothDelay -= delta
  }

  override def getNextUseDelay(causer: EntityLiving, position: Vector2d, leftClick: Boolean): Double = 0.2

  override def isFlammable: Boolean = true

  override def isOpaque: Boolean = false

  override def isCollidableWith(entity: Entity): Boolean = false

  override def getFallSpeed(world: GameWorld): Float = 10

  override def getFallDelay(world: GameWorld): Float = 0.2f
}