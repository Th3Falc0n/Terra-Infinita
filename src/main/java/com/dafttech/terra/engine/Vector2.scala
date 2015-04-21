package com.dafttech.terra.engine

import com.badlogic.gdx.Gdx
import com.dafttech.terra.game.world.{Chunk, Facing, Vector2i, World}
import com.dafttech.terra.resources.Options

object Vector2 {
  def getMouse: Vector2 = {
    return new Vector2(Gdx.input.getX, Gdx.input.getY)
  }

  private def getChunkPos(blockInWorldPos: Float, chunkSize: Int): Int = {
    return if (blockInWorldPos.toInt >= 0) blockInWorldPos.toInt / chunkSize else (blockInWorldPos.toInt + 1) / chunkSize - 1
  }

  private def getBlockInChunkPos(blockInWorldPos: Float, chunkSize: Int): Float = {
    return blockInWorldPos - getChunkPos(blockInWorldPos, chunkSize) * chunkSize
  }

  private def getBlockInWorldPos(chunkPos: Int, chunkSize: Int, blockInChunkPos: Float): Float = {
    return chunkSize * chunkPos + blockInChunkPos
  }
}

class Vector2 {
  var x: Float = .0f
  var y: Float = .0f

  def this(cx: Float, cy: Float) {
    this()
    x = cx
    y = cy
  }

  def this(vec: Vector2) {
    this(vec.x, vec.y)
  }

  def this(vec: Vector2i) {
    this(vec.x, vec.y)
  }

  def setNull: Vector2 = {
    x = 0
    y = 0
    return this
  }

  def toWorldPosition: Vector2i = {
    val ox: Int = if (x.toInt >= 0) x.toInt / Options.BLOCK_SIZE else (x.toInt + 1) / Options.BLOCK_SIZE - 1
    val oy: Int = if (y.toInt >= 0) y.toInt / Options.BLOCK_SIZE else (y.toInt + 1) / Options.BLOCK_SIZE - 1
    return new Vector2i(ox, oy)
  }

  def toRenderPosition(relateTo: Vector2): Vector2 = {
    return new Vector2(x - relateTo.x + Gdx.graphics.getWidth / 2.0f, y - relateTo.y + Gdx.graphics.getHeight / 2.0f)
  }

  def toVector2i: Vector2i = {
    return new Vector2i(if (x.toInt >= 0) x.toInt else x.toInt - 1, if (y.toInt >= 0) y.toInt else y.toInt - 1)
  }

  def is: Boolean = {
    return is(0, 0)
  }

  def is(`val`: Float): Boolean = {
    return is(`val`, `val`)
  }

  def is(x: Float, y: Float): Boolean = {
    return x == this.x && y == this.y
  }

  def is(vec: Vector2): Boolean = {
    return is(vec.x, vec.y)
  }

  def sub(v: Vector2): Vector2 = {
    return sub(v.x, v.y)
  }

  def sub(x2: Float, y2: Float): Vector2 = {
    x -= x2
    y -= y2
    return this
  }

  def len: Float = {
    return Math.sqrt(len2).toFloat
  }

  def len2: Float = {
    return x * x + y * y
  }

  def nor: Vector2 = {
    return mul(1f / len)
  }

  def angle: Float = {
    var angle: Float = Math.atan2(this.y, this.x).toFloat * 57.295776F
    if (angle < 0.0F) angle += 360.0F
    return angle
  }

  def getX: Float = {
    return x
  }

  def getY: Float = {
    return y
  }

  def setX(x: Float): Vector2 = {
    this.x = x
    return this
  }

  def setY(y: Float): Vector2 = {
    this.y = y
    return this
  }

  def set: Vector2 = {
    this.x = 0
    this.y = 0
    return this
  }

  def set(`val`: Float): Vector2 = {
    this.x = `val`
    this.y = `val`
    return this
  }

  def set(x: Float, y: Float): Vector2 = {
    this.x = x
    this.y = y
    return this
  }

  def set(pos: Vector2): Vector2 = {
    this.x = pos.x
    this.y = pos.y
    return this
  }

  def addX(x: Float): Vector2 = {
    this.x += x
    return this
  }

  def addY(y: Float): Vector2 = {
    this.y += y
    return this
  }

  def add: Vector2 = {
    this.x += 0
    this.y += 0
    return this
  }

  def add(`val`: Float): Vector2 = {
    this.x += `val`
    this.y += `val`
    return this
  }

  def add(x: Float, y: Float): Vector2 = {
    this.x += x
    this.y += y
    return this
  }

  def add(pos: Vector2): Vector2 = {
    this.x += pos.x
    this.y += pos.y
    return this
  }

  def add(facing: Facing): Vector2 = {
    return add(facing.xOff, facing.yOff)
  }

  def mulX(x: Float): Vector2 = {
    this.x *= x
    return this
  }

  def mulY(y: Float): Vector2 = {
    this.y *= y
    return this
  }

  def mul: Vector2 = {
    this.x *= 1
    this.y *= 1
    return this
  }

  def mul(`val`: Float): Vector2 = {
    this.x *= `val`
    this.y *= `val`
    return this
  }

  def mul(x: Float, y: Float): Vector2 = {
    this.x *= x
    this.y *= y
    return this
  }

  def mul(pos: Vector2): Vector2 = {
    this.x *= pos.x
    this.y *= pos.y
    return this
  }

  def setXNew(x: Float): Vector2 = {
    return clone.setX(x)
  }

  def setYNew(y: Float): Vector2 = {
    return clone.setY(y)
  }

  def setNew: Vector2 = {
    return clone.set
  }

  def setNew(`val`: Float): Vector2 = {
    return clone.setNew(`val`)
  }

  def setNew(x: Float, y: Float): Vector2 = {
    return clone.set(x, y)
  }

  def setNew(pos: Vector2): Vector2 = {
    return clone.set(pos)
  }

  def addXNew(x: Float): Vector2 = {
    return clone.addX(x)
  }

  def addYNew(y: Float): Vector2 = {
    return clone.addY(y)
  }

  def addNew: Vector2 = {
    return clone.add
  }

  def addNew(`val`: Float): Vector2 = {
    return clone.add(`val`)
  }

  def addNew(x: Float, y: Float): Vector2 = {
    return clone.add(x, y)
  }

  def addNew(pos: Vector2): Vector2 = {
    return clone.add(pos)
  }

  def addNew(facing: Facing): Vector2 = {
    return addNew(facing.xOff, facing.yOff)
  }

  def mulXNew(x: Float): Vector2 = {
    return clone.mulX(x)
  }

  def mulYNew(y: Float): Vector2 = {
    return clone.mulY(y)
  }

  def mulNew: Vector2 = {
    return clone.mul
  }

  def mulNew(`val`: Float): Vector2 = {
    return clone.mul(`val`)
  }

  def mulNew(x: Float, y: Float): Vector2 = {
    return clone.mul(x, y)
  }

  def mulNew(pos: Vector2): Vector2 = {
    return clone.mul(pos)
  }

  override def clone: Vector2 = {
    return new Vector2(this)
  }

  override def equals(obj: Any): Boolean = {
    return (obj.isInstanceOf[Vector2i] && ((obj.asInstanceOf[Vector2i]).x == x) && ((obj.asInstanceOf[Vector2i]).y == y))
  }

  override def hashCode: Int = {
    return toString.hashCode
  }

  def getChunkPos(world: World): Vector2 = {
    return new Vector2(Vector2.getChunkPos(x, world.chunksize.x), Vector2.getChunkPos(y, world.chunksize.y))
  }

  def getBlockInChunkPos(world: World): Vector2 = {
    return new Vector2(Vector2.getBlockInChunkPos(x, world.chunksize.x), Vector2.getBlockInChunkPos(y, world.chunksize.y))
  }

  def getBlockInWorldPos(chunk: Chunk): Vector2 = {
    return new Vector2(Vector2.getBlockInWorldPos(chunk.pos.x, chunk.world.chunksize.x, x), Vector2.getBlockInWorldPos(chunk.pos.y, chunk.world.chunksize.y, y))
  }

  override def toString: String = {
    return x + "," + y
  }
}