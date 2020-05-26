package com.dafttech.terra.game.world.entities

import java.util.Random

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.particles.ParticleSpark
import com.dafttech.terra.game.world.tiles.TileFire
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class EntityFlamingArrow(pos: Vector2d)(implicit world: GameWorld) extends EntityArrow(pos) {
  private val light: PointLight = new PointLight(7)
  light.setColor(new Color(255, 200, 40, 255))

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("arrow")

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)

    //light.setSize(7 + new Random().nextInt(10))

    for (_ <- 0 until 5)
      if (TerraInfinita.rnd.nextDouble < delta * body.getLinearVelocity.len() * 0.1f)
        new ParticleSpark(getPosition)
  }

  override def onTerrainCollision(tilePosition: TilePosition): Unit = {
    super.onTerrainCollision(tilePosition)
    placeBlockOnHit(tilePosition)
  }

  def placeBlockOnHit(tilePosition: TilePosition): Unit = TileFire.createFire(tilePosition)

  override def getEmittedLight: PointLight = light
}