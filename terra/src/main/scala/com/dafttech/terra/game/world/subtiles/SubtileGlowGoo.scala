package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.entities.particles.ParticleSpark
import com.dafttech.terra.resources.{Options, Resources}
import monix.eval.Task

class SubtileGlowGoo extends SubtileFluid {
  private[subtiles] var img: Float = 0
  private[subtiles] var light = new PointLight(95)

  override def getNewFluid = new SubtileGlowGoo()

  override def getViscosity = 100

  override def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.onTick(delta)

    img += delta
    if (img > 3) img = 0

    for (_ <- 0 until pressure.toInt)
      if (TerraInfinita.rnd.nextDouble() < delta * 0.5f)
        new ParticleSpark(tilePosition.pos.toEntityPos + (Options.BLOCK_SIZE / 2, 0))(tilePosition.world).addVelocity(Vector2d(0, -1))
  }

  override val getImage: Task[TextureRegion] = Resources.TILES.getImageTask("glowgoo", img.toInt)

  override def getMaxReach: Int = 0

  override def getPressCap: Int = 0
}