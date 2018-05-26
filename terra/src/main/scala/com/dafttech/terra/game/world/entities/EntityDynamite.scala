package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.particles.ParticleExplosion
import com.dafttech.terra.resources.{Options, Resources}
import monix.eval.Task

class EntityDynamite(pos: Vector2, var explodeTimer: Float, var radius: Int)(implicit world: World) extends Entity(pos, Vector2(1.5, 1.5)) {
  setGravityFactor(0.125)

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImage("dynamite")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    explodeTimer -= delta
    if (explodeTimer <= 0) {
      world.removeEntity(this)
      new ParticleExplosion(getPosition + (Options.BLOCK_SIZE * 0.75f, Options.BLOCK_SIZE * 0.75f), radius)
      val destroyPos = getPosition.toWorldPosition

      for {
        y <- -radius + 1 to radius
        x <- -radius + 1 to radius
      } world.destroyTile(Vector2i(destroyPos.x + x, destroyPos.y + y), this)
    }
  }
}