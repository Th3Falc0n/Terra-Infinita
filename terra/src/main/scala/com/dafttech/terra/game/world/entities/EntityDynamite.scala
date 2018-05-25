package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.particles.ParticleExplosion
import com.dafttech.terra.resources.{Options, Resources}

class EntityDynamite(pos: Vector2, world: World, var explodeTimer: Float, var radius: Int) extends Entity(pos, world, new Vector2(1.5, 1.5)) {
  setGravityFactor(0.125)

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("dynamite")

  override def update(world: World, delta: Float): Unit = {
    super.update(world, delta)
    explodeTimer -= delta
    if (explodeTimer <= 0) {
      worldObj.removeEntity(this)
      new ParticleExplosion(getPosition + (Options.BLOCK_SIZE * 0.75f, Options.BLOCK_SIZE * 0.75f), worldObj, radius)
      val destroyPos = getPosition.toWorldPosition

      for {
        y <- -radius + 1 to radius
        x <- -radius + 1 to radius
      } worldObj.destroyTile(destroyPos.x + x, destroyPos.y + y, this)
    }
  }
}