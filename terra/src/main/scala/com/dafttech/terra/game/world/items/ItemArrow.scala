package com.dafttech.terra.game.world.items

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.EntityArrow
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class ItemArrow extends ItemEntitySpawner {
  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImage("arrow")

  override def getNextUseDelay(causer: EntityLiving, position: Vector2, leftClick: Boolean): Double = 0.2

  override def spawnEntity(causer: EntityLiving, position: Vector2): Boolean = {
    val a = new EntityArrow(causer.getPosition)(causer.world)
    a.setVelocity((Vector2.mousePos - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)) * 0.2)
    true
  }
}