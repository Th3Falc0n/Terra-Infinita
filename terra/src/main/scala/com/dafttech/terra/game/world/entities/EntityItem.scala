package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.inventories.Stack
import com.dafttech.terra.game.world.items.persistence.Prototype
import com.dafttech.terra.resources.Options

class EntityItem(pos: Vector2, world: World, size: Vector2, val wrapped: Item) extends Entity(pos, world, size) {
  override def getImage: TextureRegion = wrapped.getImage

  override def toPrototype: Prototype = wrapped.toPrototype

  override def collidesWith(e: Entity): Boolean = false

  override def update(world: World, delta: Float): Unit = {
    val playerSize = getWorld.localPlayer.getSize
    val vp = getWorld.localPlayer.getPosition +
      (playerSize.x * Options.BLOCK_SIZE / 2, playerSize.y * Options.BLOCK_SIZE / 2) -
      getPosition

    if (vp.`length²` < 400) {
      getWorld.localPlayer.inventory.add(Stack.apply(wrapped, 1))
      getWorld.removeEntity(this)
    }
    if (vp.`length²` < 10000) addForce(vp.normalized * (14f / vp.`length²`))
    super.update(world, delta)
  }
}