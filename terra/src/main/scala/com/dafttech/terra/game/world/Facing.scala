package com.dafttech.terra.game.world

import com.dafttech.terra.game.world.Facing._

/**
  * Created by LolHens on 18.04.2015.
  */
sealed abstract class Facing(val index: Int,
                             val xOff: Int,
                             val yOff: Int) {
  def isVertical: Boolean = {
    return (this eq TOP) || (this eq BOTTOM)
  }

  def isHorizontal: Boolean = {
    return (this eq LEFT) || (this eq RIGHT)
  }

  def invert: Facing = {
    if (this eq TOP) return BOTTOM
    if (this eq BOTTOM) return TOP
    if (this eq LEFT) return RIGHT
    return LEFT
  }

  def getIndex: Int = {
    return index
  }
}

object Facing {

  object NONE extends Facing(0, 0, 0)

  object TOP extends Facing(1, 0, -1)

  object BOTTOM extends Facing(2, 0, 1)

  object LEFT extends Facing(3, -1, 0)

  object RIGHT extends Facing(4, 1, 0)

}
