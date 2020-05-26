package com.dafttech.terra.game.world.items.inventories

import java.util

import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.persistence.{GameObject, Prototype}

import scala.annotation.tailrec
import scala.jdk.CollectionConverters._

/* MUTABLE */
class Inventory {
  private var content = Map.empty[Prototype, List[Stack]]

  def add(stack: Stack): Unit = {
    val stacks = content.getOrElse(stack.prototype, Nil)
    val maxStackSize = stack.prototype.toGameObject match {
      case item: Item => item.maxStackSize
    }

    @tailrec
    def rec(stack: Stack, out: List[Stack], in: List[Stack]): List[Stack] =
      if (stack.isEmpty) out ++ in
      else in.headOption match {
        case Some(head) if head.size < maxStackSize =>
          val addedCount = Math.min(maxStackSize - head.size, stack.size)
          rec(stack.drop(addedCount), out :+ head.withSize(head.size + addedCount), in.tail)

        case Some(head) =>
          rec(stack, out :+ head, in.tail)

        case None =>
          rec(stack.drop(maxStackSize), out :+ stack.take(maxStackSize), List.empty)
      }

    val newStacks = rec(stack, List.empty, stacks)
    content = content + (stack.prototype -> newStacks)
  }

  def getList: util.List[Stack] = content.values.flatten.toBuffer.asJava

  def remove(stack: Stack): Stack = {
    val stacks = content.getOrElse(stack.prototype, Nil)

    val (remainingStack: Stack, newStacks: List[Stack]) = stacks.foldLeft((stack, List.empty[Stack])) {
      case ((remainingStack, stacks), stack) =>
        val removedCount = Math.min(remainingStack.size, stack.size)
        (remainingStack.drop(removedCount), stacks :+ stack.drop(removedCount))
    }

    content = content + (stack.prototype -> newStacks)
    remainingStack
  }

  def contains(obj: GameObject): Boolean = contains(obj.toPrototype)

  def contains(prototype: Prototype): Boolean = getAmount(prototype) > 0

  def getAmount(obj: GameObject): Int = getAmount(obj.toPrototype)

  def getAmount(prototype: Prototype): Int = content.getOrElse(prototype, Nil).map(_.size).sum
}