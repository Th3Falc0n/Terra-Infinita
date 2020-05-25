package com.dafttech.terra.game.world.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.entities.EntityDynamite
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class ItemDynamite extends ItemEntitySpawner {
  override def spawnEntity(causer: EntityLiving, position: Vector2d): Boolean = {
    new EntityDynamite(causer.getPosition, 3, 4)(causer.world)
    true
  }

  override val getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("dynamite")

  override def getNextUseDelay(causer: EntityLiving, position: Vector2d, leftClick: Boolean): Double = 1
}