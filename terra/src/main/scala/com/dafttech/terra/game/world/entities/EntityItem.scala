package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.inventories.Stack
import com.dafttech.terra.game.world.items.persistence.Prototype
import com.dafttech.terra.resources.Options
import monix.eval.Task

class EntityItem(pos: Vector2d, size: Vector2d, val wrapped: Item)(implicit world: World) extends Entity(pos, size) {
  override def getImage: Task[TextureRegion] = wrapped.getImage

  override def toPrototype: Prototype = wrapped.toPrototype

  override def collidesWith(e: Entity): Boolean = false

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    val playerSize = getWorld.localPlayer.getSize
    val vp = getWorld.localPlayer.getPosition +
      (playerSize.x * Options.BLOCK_SIZE / 2, playerSize.y * Options.BLOCK_SIZE / 2) -
      getPosition

    if (vp.`length²` < 400) {
      getWorld.localPlayer.inventory.add(Stack.apply(wrapped, 1))
      getWorld.removeEntity(this)
    }
    if (vp.`length²` < 10000) addForce(vp.normalized * (14f / vp.`length²`))
    super.update(delta)
  }
}