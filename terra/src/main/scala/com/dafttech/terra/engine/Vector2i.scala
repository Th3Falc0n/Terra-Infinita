package com.dafttech.terra.engine

import com.badlogic.gdx.Gdx
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.{Chunk, World}
import com.dafttech.terra.resources.Options.BLOCK_SIZE

case class Vector2i(x: Int, y: Int) {
  self =>

  protected object Vector2i extends Vector2iObj {
    override def apply(x: Int, y: Int): Vector2i =
      if (x == self.x && y == self.y) self else super.apply(x, y)
  }

  def +(x: Int, y: Int): Vector2i = Vector2i(this.x + x, this.y + y)

  def -(x: Int, y: Int): Vector2i = Vector2i(this.x - x, this.y - y)

  def *(x: Int, y: Int): Vector2i = Vector2i(this.x * x, this.y * y)

  def /(x: Int, y: Int): Vector2i = Vector2i(this.x / x, this.y / y)

  def +(vec: Vector2i): Vector2i = if (isNull) vec else self + (vec.x, vec.y)

  def -(vec: Vector2i): Vector2i = self - (vec.x, vec.y)

  def *(vec: Vector2i): Vector2i = if (isOne) vec else self * (vec.x, vec.y)

  def /(vec: Vector2i): Vector2i = self / (vec.x, vec.y)

  def +(value: Int): Vector2i = self + (value, value)

  def -(value: Int): Vector2i = self - (value, value)

  def *(value: Int): Vector2i = self * (value, value)

  def /(value: Int): Vector2i = self / (value, value)

  lazy val unary_- : Vector2i = Vector2i(-x, -y)

  def withX(x: Int): Vector2i = Vector2i(x, y)

  def withY(y: Int): Vector2i = Vector2i(x, y)

  def mapX(f: Int => Int): Vector2i = Vector2i(f(x), y)

  def mapY(f: Int => Int): Vector2i = Vector2i(x, f(y))

  def isNull: Boolean = x == 0 && y == 0

  def isOne: Boolean = x == 1 && y == 1


  def toScreenPos(pointOfView: Entity): Vector2 =
    Vector2(x * BLOCK_SIZE - pointOfView.getPosition.x + Gdx.graphics.getWidth / 2, y * BLOCK_SIZE - pointOfView.getPosition.y + Gdx.graphics.getHeight / 2)

  def toEntityPos: Vector2 = Vector2(x * BLOCK_SIZE, y * BLOCK_SIZE)

  def toVector2: Vector2 = Vector2(x, y)

  def isInRect(x: Int, y: Int, sizeX: Int, sizeY: Int): Boolean = {
    this.x >= x && this.y >= y && this.x < x + sizeX && this.y < y + sizeY
  }

  def getChunkPos(world: World): Vector2i =
    new Vector2i(Vector2i.getChunkPos(x, world.chunksize.x), Vector2i.getChunkPos(y, world.chunksize.y))

  def getBlockInChunkPos(world: World): Vector2i =
    new Vector2i(Vector2i.getBlockInChunkPos(x, world.chunksize.x), Vector2i.getBlockInChunkPos(y, world.chunksize.y))

  def getBlockInWorldPos(chunk: Chunk): Vector2i =
    new Vector2i(Vector2i.getBlockInWorldPos(chunk.pos.x, chunk.world.chunksize.x, x), Vector2i.getBlockInWorldPos(chunk.pos.y, chunk.world.chunksize.y, y))
}

trait Vector2iObj {
  def apply(x: Int, y: Int): Vector2i =
    if (x == 0 && y == 0) Null
    else if (x == 1 && y == 1) One
    else new Vector2i(x, y)

  lazy val Null = new Vector2i(0, 0)

  lazy val One = new Vector2i(1, 1)


  private[engine] def getChunkPos(blockInWorldPos: Int, chunkSize: Int): Int =
    if (blockInWorldPos >= 0) blockInWorldPos / chunkSize else (blockInWorldPos + 1) / chunkSize - 1

  private[engine] def getBlockInChunkPos(blockInWorldPos: Int, chunkSize: Int): Int =
    blockInWorldPos - getChunkPos(blockInWorldPos, chunkSize) * chunkSize

  private[engine] def getBlockInWorldPos(chunkPos: Int, chunkSize: Int, blockInChunkPos: Int): Int =
    chunkSize * chunkPos + blockInChunkPos
}

object Vector2i extends Vector2iObj
