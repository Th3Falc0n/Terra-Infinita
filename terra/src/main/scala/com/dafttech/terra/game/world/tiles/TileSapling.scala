package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.Resources

class TileSapling extends TileFalling {
  private var grothDelay: Float = 4

  override def getImage: TextureRegion = Resources.TILES.getImage("sapling")

  override def onTick(world: World, delta: Float): Unit = {
    super.onTick(world, delta)
    if (grothDelay <= 0) world.setTile(getPosition, new TileLog().setLiving(true), notify = true)
    else grothDelay -= delta
  }

  override def getNextUseDelay(causer: EntityLiving, position: Vector2, leftClick: Boolean): Double = 0.2

  override def isFlammable: Boolean = true

  override def isOpaque: Boolean = false

  override def isCollidableWith(entity: Entity): Boolean = false

  override def getFallSpeed(world: World): Float = 10

  override def getFallDelay(world: World): Float = 0.2f
}