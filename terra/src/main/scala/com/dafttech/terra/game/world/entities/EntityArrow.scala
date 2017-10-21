package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.entities.models.{EntityLiving, EntityThrown}
import com.dafttech.terra.game.world.tiles.{Tile, TileAir}
import com.dafttech.terra.resources.Resources

class EntityArrow(pos: Vector2, world: World) extends EntityThrown(pos, world, new Vector2(2, 0.6f)) {
  private[entities] var stuckIn: Tile = new TileAir

  setGravityFactor(0.25)

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("arrow")

  override def update(world: World, delta: Float): Unit = {
    stuckIn = world.getTile(stuckIn.getPosition)
    if (stuckIn.isAir || !stuckIn.isCollidableWith(this)) setGravityFactor(0.25f)
    super.update(world, delta)
  }

  override def collidesWith(e: Entity): Boolean = e.isInstanceOf[EntityLiving] && !e.isInstanceOf[Player]

  override def onTerrainCollision(tile: Tile): Unit = {
    stuckIn = tile
    setGravityFactor(0)
    setVelocity(new Vector2(0, 0))
  }

  override def getInAirFriction: Double = 0.025
}