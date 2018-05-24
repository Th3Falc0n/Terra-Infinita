package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.particles.ParticleSpark
import com.dafttech.terra.resources.{Options, Resources}

class SubtileGlowGoo() extends SubtileFluid {
  private[subtiles] var img: Float = 0
  private[subtiles] var light = new PointLight(tile.getPosition.toEntityPos, 95)

  override def getNewFluid = new SubtileGlowGoo()

  override def getViscosity = 100

  override def onTick(world: World, delta: Float): Unit = {
    super.onTick(world, delta)

    img += delta
    if (img > 3) img = 0

    light.setPosition(tile.getPosition.toEntityPos.$plus(Options.BLOCK_SIZE / 2, Options.BLOCK_SIZE / 2))

    for (_ <- 0 until pressure.toInt)
      if (TerraInfinita.rnd.nextDouble() < delta * 0.5f)
        new ParticleSpark(tile.getPosition.toEntityPos + (Options.BLOCK_SIZE / 2, 0), world).addVelocity(new Vector2(0, -1))
  }

  override def getImage: TextureRegion = Resources.TILES.getImage("glowgoo", img.toInt)

  override def getMaxReach: Int = 0

  override def getPressCap: Int = 0
}