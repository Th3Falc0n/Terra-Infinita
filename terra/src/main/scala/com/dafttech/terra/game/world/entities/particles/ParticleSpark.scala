package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class ParticleSpark(val pos: Vector2d)(implicit world: GameWorld) extends Particle(pos, 0.6f + (0.75f * TerraInfinita.rnd.nextFloat)) {
  private val particleSize: Double = TerraInfinita.rnd.nextFloat * 0.2 + 0.1
  private val light: PointLight = new PointLight(4)

  light.setColor(new Color(1, 0.9f, 0.9f, 0.4f))
  body.setLinearVelocity(0.5f - TerraInfinita.rnd.nextFloat, 0.5f - TerraInfinita.rnd.nextFloat)

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("flame")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    setScale((particleSize * (lifetime / lifetimeMax)).toFloat)
    light.setSize(16 * (lifetime / lifetimeMax))
  }

  //override def checkTerrainCollisions(world: GameWorld): Unit = ()

  override def getEmittedLight: PointLight = light
}