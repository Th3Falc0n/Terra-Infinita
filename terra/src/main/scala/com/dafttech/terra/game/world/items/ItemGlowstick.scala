package com.dafttech.terra.game.world.items

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.input.MousePos
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.entities.EntityGlowstick
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class ItemGlowstick extends ItemEntitySpawner {
  override def spawnEntity(causer: EntityLiving, position: Vector2d): Boolean = {
    val a = new EntityGlowstick(causer.getPosition)(causer.world)
    a.setVelocity((MousePos.vector2d - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)) * 0.08)
    true
  }

  override def getNextUseDelay(causer: EntityLiving, position: Vector2d, leftClick: Boolean): Double = 0.4

  override val getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("glowstick")
}