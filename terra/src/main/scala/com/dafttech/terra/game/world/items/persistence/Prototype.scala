package com.dafttech.terra.game.world.items.persistence

import java.lang.reflect.Field

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
    try {
      @SuppressWarnings(Array("unchecked"))
      val cl = Class.forName(className).asInstanceOf[Class[GameObject]]
      val obj = cl.newInstance()
      for ((field, value) <- values) field.set(obj, value)
      obj
    } catch {
      case e@(_: ClassNotFoundException | _: InstantiationException | _: IllegalAccessException) =>
        // TODO Auto-generated catch block
        e.printStackTrace()
        null // TODO: NPE!
    }
  }
}