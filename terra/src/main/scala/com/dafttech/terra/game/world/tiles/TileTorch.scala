package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.particles.ParticleSpark
import com.dafttech.terra.resources.{Options, Resources}

class TileTorch(implicit val world: World) extends TileFalling {
  private[tiles] var light = new PointLight(95)

  override def getImage: TextureRegion = Resources.TILES.getImage("torch")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    for (_ <- 0 until 5)
      if (TerraInfinita.rnd.nextDouble() < delta * 0.5)
        new ParticleSpark(tilePosition.pos.toEntityPos + (Options.BLOCK_SIZE / 2, 0)).addVelocity(Vector2(0, -1))
  }

  override def isCollidableWith(entity: Entity): Boolean = false

  override def isOpaque: Boolean = false

  override def isLightEmitter: Boolean = true

  override def getEmittedLight: PointLight = light

  override def getFallSpeed(world: World): Float = 10

  override def getFallDelay(world: World): Float = 0.2f
}