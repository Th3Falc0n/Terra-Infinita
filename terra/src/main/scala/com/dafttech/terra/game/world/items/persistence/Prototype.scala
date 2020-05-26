package com.dafttech.terra.game.world.items.persistence

import java.lang.reflect.Field

import com.dafttech.terra.game.world.items.TileItem
import com.dafttech.terra.game.world.tiles.Tile

class Prototype {
  var className = ""
  private var values = Map[Field, AnyRef]()

  override def hashCode: Int = getHashBase.hashCode

  override def equals(obj: Any): Boolean = obj match {
    case _: Prototype => hashCode == obj.hashCode
    case _: GameObject => hashCode == obj.hashCode
    case _ => true
  }

  def getHashBase: String = className + values.values.map(_.toString).mkString

  def addValue(field: Field, value: AnyRef): Unit = values = values + (field -> value)

  def toGameObject: GameObject = {
    Class.forName(className) match {
      case tileItem if tileItem == classOf[TileItem] =>
        TileItem(values(TileItem.tileField).asInstanceOf[Tile])

      case clazz =>
        @SuppressWarnings(Array("unchecked"))
        val cl = clazz.asInstanceOf[Class[GameObject]]
        val obj = cl.newInstance()
        for ((field, value) <- values) field.set(obj, value)
        obj
    }
  }
}