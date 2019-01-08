package com.dafttech.terra.game.world

import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.game.world.Facing._

/**
  * Created by LolHens on 18.04.2015.
  */
sealed abstract class Facing(val index: Int,
                             val xOff: Int,
                             val yOff: Int) {
  def isVertical: Boolean = this == Top || this == Bottom

  def isHorizontal: Boolean = this == Left || this == Right

  def invert: Facing = this match {
    case Top => Bottom
    case Bottom => Top
    case Left => Right
    case Right => Left
    case None => None
  }

  def getIndex: Int = index

  def vector: Vector2d = Vector2d(xOff, yOff)

  def intVector: Vector2i = Vector2i(xOff, yOff)
}

object Facing {

  object None extends Facing(0, 0, 0)

  object Top extends Facing(1, 0, -1)

  object Bottom extends Facing(2, 0, 1)

  object Left extends Facing(3, -1, 0)

  object Right extends Facing(4, 1, 0)

}
