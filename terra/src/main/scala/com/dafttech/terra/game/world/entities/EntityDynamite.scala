package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.particles.ParticleExplosion
import com.dafttech.terra.resources.{Options, Resources}
import monix.eval.Task

class EntityDynamite(pos: Vector2d, var explodeTimer: Float, var radius: Int)(implicit world: GameWorld) extends Entity(pos) {
  setGravityFactor(0.125)

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("dynamite")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    explodeTimer -= delta
    if (explodeTimer <= 0) {
      world.removeEntity(this)
      new ParticleExplosion(getPosition + (Options.METERS_PER_BLOCK * 0.75f, Options.METERS_PER_BLOCK * 0.75f), radius)
      val destroyPos = getPosition.toWorldPosition

      for {
        y <- -radius + 1 to radius
        x <- -radius + 1 to radius
      } world.destroyTile(Vector2i(destroyPos.x + x, destroyPos.y + y), this)
    }
  }
}