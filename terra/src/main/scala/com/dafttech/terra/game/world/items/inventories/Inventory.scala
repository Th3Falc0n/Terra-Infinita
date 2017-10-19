package com.dafttech.terra.game.world.items.inventories

import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.persistence.GameObject
import com.dafttech.terra.game.world.items.persistence.Prototype
import java.util
import scala.collection.JavaConverters._

/* MUTABLE */
class Inventory {
  private var content = Map.empty[Prototype, List[Stack]]

  def add(stack: Stack): Unit = {
    val stacks = content.getOrElse(stack.prototype, Nil)
    val maxStackSize = stack.prototype.toGameObject.asInstanceOf[Item].maxStackSize

    def addStackRec(stack: Stack, out: List[Stack], in: List[Stack]): List[Stack] =
      if (stack.isEmpty) out ++ in
      else if (in.isEmpty) {
        in :+ stack.take(maxStackSize) :+ addStackRec(stack.drop(maxStackSize), List.empty)
      } else if (in.head.size < maxStackSize)
        if (in.head.size + stack.size > maxStackSize)
          addStackRec(stack.drop(maxStackSize - in.head.size), out :+ in.head.withSize(maxStackSize), in.tail)
    else
        addStackRec(in.head.withSize(), in.tail)
    else
        addStackRec(stack, in.take(1), in.drop(1))


    val proto = stack.prototype
    if (stacks.contains(proto)) {
      import scala.collection.JavaConversions._
      for (s <- stacks.get(proto)) {
        var am = proto.toGameObject.asInstanceOf[Item].maxStackSize - s.size
        if (am <= stack.size) {
          stack.size -= am
          s.size += am
        }
        else {
          am = stack.size
          stack.size -= am
          s.size += am
        }
      }
      if (stack.size > 0) stacks.get(proto).add(stack)
    }
    else {
      stacks.put(proto, new util.ArrayList[Stack])
      stacks.get(proto).add(stack)
    }
  }

  def getList: util.List[Stack] = content.values.flatten.toBuffer.asJava

  def remove(stack: Stack): Boolean = {
    if (!(stacks.containsKey(stack.prototype) && stacks.get(stack.prototype).contains(stack))) return false
    stacks.get(stack.prototype).remove(stack)
    true
  }

  def contains(obj: GameObject): Boolean = contains(obj.toPrototype)

  def contains(prototype: Prototype): Boolean = getAmount(prototype) > 0

  def getAmount(obj: GameObject): Int = getAmount(obj.toPrototype)

  def getAmount(prototype: Prototype): Int = content.getOrElse(prototype, Nil).map(_.size).sum
}