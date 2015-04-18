package com.dafttech.terra.game.world

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.resources.Options.BLOCK_SIZE

object Vector2i {
  private def getChunkPos(blockInWorldPos: Int, chunkSize: Int): Int = {
    return if (blockInWorldPos >= 0) blockInWorldPos / chunkSize else (blockInWorldPos + 1) / chunkSize - 1
  }

  private def getBlockInChunkPos(blockInWorldPos: Int, chunkSize: Int): Int = {
    return blockInWorldPos - getChunkPos(blockInWorldPos, chunkSize) * chunkSize
  }

  private def getBlockInWorldPos(chunkPos: Int, chunkSize: Int, blockInChunkPos: Int): Int = {
    return chunkSize * chunkPos + blockInChunkPos
  }
}
class Vector2i {
  var x: Int = 0
  var y: Int = 0
  set

  def this(x: Int, y: Int) {
    this()
    set(x, y)
  }

  def this(pos: Vector2i) {
    this()
    set(pos)
  }

  def this(pos: Vector2) {
    this()
    set(pos)
  }

  def toScreenPos(pointOfView: Entity): Vector2 = {
    return new Vector2(x * BLOCK_SIZE - pointOfView.getPosition.x + Gdx.graphics.getWidth / 2, y * BLOCK_SIZE - pointOfView.getPosition.y + Gdx.graphics.getHeight / 2)
  }

  def toEntityPos: Vector2 = {
    return new Vector2(x * BLOCK_SIZE, y * BLOCK_SIZE)
  }

  def toVector2: Vector2 = {
    return new Vector2(x, y)
  }

  def getX: Int = {
    return x
  }

  def getY: Int = {
    return y
  }

  def setX(x: Int): Vector2i = {
    this.x = x
    return this
  }

  def setY(y: Int): Vector2i = {
    this.y = y
    return this
  }

  def set: Vector2i = {
    this.x = 0
    this.y = 0
    return this
  }

  def set(`val`: Int): Vector2i = {
    this.x = `val`
    this.y = `val`
    return this
  }

  def set(x: Int, y: Int): Vector2i = {
    this.x = x
    this.y = y
    return this
  }

  def set(pos: Vector2i): Vector2i = {
    this.x = pos.x
    this.y = pos.y
    return this
  }

  def set(pos: Vector2): Vector2i = {
    return set(pos.toWorldPosition)
  }

  def addX(x: Int): Vector2i = {
    this.x += x
    return this
  }

  def addY(y: Int): Vector2i = {
    this.y += y
    return this
  }

  def add: Vector2i = {
    this.x += 0
    this.y += 0
    return this
  }

  def add(`val`: Int): Vector2i = {
    this.x += `val`
    this.y += `val`
    return this
  }

  def add(x: Int, y: Int): Vector2i = {
    this.x += x
    this.y += y
    return this
  }

  def add(pos: Vector2i): Vector2i = {
    this.x += pos.x
    this.y += pos.y
    return this
  }

  def add(facing: Facing): Vector2i = {
    return add(facing.xOff, facing.yOff)
  }

  def add(pos: Vector2): Vector2i = {
    return add(pos.toWorldPosition)
  }

  def mulX(x: Int): Vector2i = {
    this.x *= x
    return this
  }

  def mulY(y: Int): Vector2i = {
    this.y *= y
    return this
  }

  def mul: Vector2i = {
    this.x *= 1
    this.y *= 1
    return this
  }

  def mul(`val`: Int): Vector2i = {
    this.x *= `val`
    this.y *= `val`
    return this
  }

  def mul(x: Int, y: Int): Vector2i = {
    this.x *= x
    this.y *= y
    return this
  }

  def mul(pos: Vector2i): Vector2i = {
    this.x *= pos.x
    this.y *= pos.y
    return this
  }

  def mul(pos: Vector2): Vector2i = {
    return mul(pos.toWorldPosition)
  }

  def setXNew(x: Int): Vector2i = {
    return clone.setX(x)
  }

  def setYNew(y: Int): Vector2i = {
    return clone.setY(y)
  }

  def setNew: Vector2i = {
    return clone.set
  }

  def setNew(`val`: Int): Vector2i = {
    return clone.setNew(`val`)
  }

  def setNew(x: Int, y: Int): Vector2i = {
    return clone.set(x, y)
  }

  def setNew(pos: Vector2i): Vector2i = {
    return clone.set(pos)
  }

  def setNew(pos: Vector2): Vector2i = {
    return clone.set(pos)
  }

  def addXNew(x: Int): Vector2i = {
    return clone.addX(x)
  }

  def addYNew(y: Int): Vector2i = {
    return clone.addY(y)
  }

  def addNew: Vector2i = {
    return clone.add
  }

  def addNew(`val`: Int): Vector2i = {
    return clone.add(`val`)
  }

  def addNew(x: Int, y: Int): Vector2i = {
    return clone.add(x, y)
  }

  def addNew(pos: Vector2i): Vector2i = {
    return clone.add(pos)
  }

  def addNew(pos: Vector2): Vector2i = {
    return clone.add(pos)
  }

  def addNew(facing: Facing): Vector2i = {
    return addNew(facing.xOff, facing.yOff)
  }

  def mulXNew(x: Int): Vector2i = {
    return clone.mulX(x)
  }

  def mulYNew(y: Int): Vector2i = {
    return clone.mulY(y)
  }

  def mulNew: Vector2i = {
    return clone.mul
  }

  def mulNew(`val`: Int): Vector2i = {
    return clone.mul(`val`)
  }

  def mulNew(x: Int, y: Int): Vector2i = {
    return clone.mul(x, y)
  }

  def mulNew(pos: Vector2i): Vector2i = {
    return clone.mul(pos)
  }

  def mulNew(pos: Vector2): Vector2i = {
    return clone.mul(pos)
  }

  override def clone: Vector2i = {
    return new Vector2i(this)
  }

  override def equals(obj: Any): Boolean = {
    return (obj.isInstanceOf[Vector2i] && (obj.asInstanceOf[Vector2i]).x == x && (obj.asInstanceOf[Vector2i]).y == y)
  }

  override def hashCode: Int = {
    return (x.asInstanceOf[Integer]).hashCode * 5573 + (y.asInstanceOf[Integer]).hashCode
  }

  def isInRect(x: Int, y: Int, sizeX: Int, sizeY: Int): Boolean = {
    return this.x >= x && this.y >= y && this.x < x + sizeX && this.y < y + sizeY
  }

  def getChunkPos(world: World): Vector2i = {
    return new Vector2i(Vector2i.getChunkPos(x, world.chunksize.x), Vector2i.getChunkPos(y, world.chunksize.y))
  }

  def getBlockInChunkPos(world: World): Vector2i = {
    return new Vector2i(Vector2i.getBlockInChunkPos(x, world.chunksize.x), Vector2i.getBlockInChunkPos(y, world.chunksize.y))
  }

  def getBlockInWorldPos(chunk: Chunk): Vector2i = {
    return new Vector2i(Vector2i.getBlockInWorldPos(chunk.pos.x, chunk.world.chunksize.x, x), Vector2i.getBlockInWorldPos(chunk.pos.y, chunk.world.chunksize.y, y))
  }
}