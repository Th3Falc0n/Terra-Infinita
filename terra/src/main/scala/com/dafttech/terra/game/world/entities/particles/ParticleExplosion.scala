package com.dafttech.terra.game.world.entities.particles

import java.util.Random

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.resources.{Options, Resources}
import monix.eval.Task

class ParticleExplosion(pos: Vector2d, var radius: Int)(implicit world: GameWorld) extends Particle(pos, 0.3f, Vector2d(radius * 2, radius * 2)) {
  private val midpos: Vector2d = pos

  private val light: PointLight = new PointLight(60)
  light.setColor(new Color(1, 0.9f, 0.9f, 0.4f))
  light.setSize((getSize.x.toFloat + getSize.y.toFloat) * 6 + Options.BLOCK_SIZE)

  setGravityFactor(0)
  setMidPos(midpos)

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("explosion")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    val rnd = new Random
    val blockSRad = radius * Options.BLOCK_SIZE * 3

    new ParticleSpark(midpos - (blockSRad, blockSRad) + (rnd.nextFloat * blockSRad * 2, rnd.nextFloat * blockSRad * 2))

    setSize(getSize + (delta * 30, delta * 30))
    setMidPos(midpos)
  }

  override def isLightEmitter = true

  override def getEmittedLight: PointLight = light
}