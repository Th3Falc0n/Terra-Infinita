package com.dafttech.terra.game.world.items.inventories

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.persistence.Prototype

case class Stack(prototype: Prototype, size: Int) {
  def isEmpty: Boolean = size <= 0

  def take(n: Int): Stack = Stack(prototype, Math.min(n, size))

  def drop(n: Int): Stack = Stack(prototype, Math.max(size - n, 0))

  def withPrototype(prototype: Prototype): Stack = copy(prototype = prototype)

  def withSize(size: Int): Stack = copy(size = size)

  def use(causer: EntityLiving, position: Vector2): Stack = {
    val item = prototype.toGameObject.asInstanceOf[Item]
    val usedItemNum = item.getUsedItemNum(causer, position)

    if (size >= usedItemNum && item.use(causer, position))
      copy(size = size - usedItemNum)
    else
      this
  }
}

object Stack {
  def apply(item: Item, size: Int): Stack = Stack(item.toPrototype, size)
}
