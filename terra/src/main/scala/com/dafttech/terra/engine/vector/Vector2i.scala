package com.dafttech.terra.engine.vector

import com.badlogic.gdx.Gdx
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Options.BLOCK_SIZE

final case class Vector2i private(override val x: Int,
                                  override val y: Int) extends Vector2[Int](x, y) {
  override type Self = Vector2i

  override def Vector2(x: Int, y: Int): Vector2i =
    if (x == this.x && y == this.y) this
    else Vector2i(x, y)

  override def isZero: Boolean = x == 0 && y == 0
  override def isOne: Boolean = x == 1 && y == 1

  override def unary_- : Vector2i = Vector2i(-x, -y)

  override def +(x: Int, y: Int): Vector2i = if (x == 0 && y == 0) this else Vector2i(this.x + x, this.y + y)
  override def -(x: Int, y: Int): Vector2i = if (x == 0 && y == 0) this else Vector2i(this.x - x, this.y - y)
  override def *(x: Int, y: Int): Vector2i = if (x == 1 && y == 1) this else Vector2i(this.x * x, this.y * y)
  override def /(x: Int, y: Int): Vector2i = if (x == 1 && y == 1) this else Vector2i(this.x / x, this.y / y)

  override def `length²`: Int = x * x + y * y
  override def length: Int = Math.sqrt(`length²`).toInt

  def toScreenPos(pointOfView: Entity): Vector2d =
    Vector2d(x * BLOCK_SIZE - pointOfView.getPosition.x + Gdx.graphics.getWidth / 2, y * BLOCK_SIZE - pointOfView.getPosition.y + Gdx.graphics.getHeight / 2)

  def toEntityPos: Vector2d = Vector2d(x * BLOCK_SIZE, y * BLOCK_SIZE)

  def toVector2: Vector2d = Vector2d(x, y)

  def isInRect(x: Int, y: Int, sizeX: Int, sizeY: Int): Boolean = {
    this.x >= x && this.y >= y && this.x < x + sizeX && this.y < y + sizeY
  }
}

object Vector2i {
  val Zero = new Vector2i(0, 0)
  val One = new Vector2i(1, 1)

  val X = new Vector2i(1, 0)
  val Y = new Vector2i(0, 1)

  def apply(x: Int, y: Int): Vector2i =
    if (x == 0 && y == 0) Zero
    else if (x == 1 && y == 1) One
    else new Vector2i(x, y)

  private[engine] def getChunkPos(blockInWorldPos: Int, chunkSize: Int): Int =
    if (blockInWorldPos >= 0) blockInWorldPos / chunkSize else (blockInWorldPos + 1) / chunkSize - 1

  private[engine] def getBlockInChunkPos(blockInWorldPos: Int, chunkSize: Int): Int =
    blockInWorldPos - getChunkPos(blockInWorldPos, chunkSize) * chunkSize

  private[engine] def getBlockInWorldPos(chunkPos: Int, chunkSize: Int, blockInChunkPos: Int): Int =
    chunkSize * chunkPos + blockInChunkPos
}
