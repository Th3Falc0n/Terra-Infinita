package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.particles.ParticleSpark
import com.dafttech.terra.resources.{Options, Resources}
import monix.eval.Task

class TileTorch extends TileFalling {
  private[tiles] var light = new PointLight(95)

  override val getImage: Task[TextureRegion] = Resources.TILES.getImageTask("torch")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    implicit val world: GameWorld = tilePosition.world

    for (_ <- 0 until 5)
      if (TerraInfinita.rnd.nextDouble() < delta * 0.5)
        new ParticleSpark(tilePosition.pos.toEntityPos + (Options.METERS_PER_BLOCK / 2, 0)).body.setLinearVelocity(0, -1)
  }

  override def isCollidableWith(entity: Entity): Boolean = false

  override def isOpaque: Boolean = false

  override def isLightEmitter: Boolean = true

  override def getEmittedLight: PointLight = light

  override def getFallSpeed(world: GameWorld): Float = 10

  override def getFallDelay(world: GameWorld): Float = 0.2f
}