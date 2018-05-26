package com.dafttech.terra.game.world.items.persistence

import java.lang.reflect.Field

object GameObject {
  private var currentUUID: Int = 0

  private def getAllDeclaredFields(targetClass: Class[_]): List[Field] =
    (targetClass.getDeclaredFields ++ Option(targetClass.getSuperclass).toList.flatMap(getAllDeclaredFields)).distinct.toList
}

// ****FOLLOWING PERSISTENCE CODE**** May harm your brain
abstract class GameObject() {
  val uuid: Int = { GameObject.currentUUID = GameObject.currentUUID + 1; GameObject.currentUUID }

  override def hashCode(): Int = uuid

  lazy val annotatedFields: List[Field] = {
    val fields: List[Field] = GameObject.getAllDeclaredFields(this.getClass).toList
    fields.flatMap { field =>
      field.setAccessible(true)
      val p = field.getAnnotation(classOf[Persistent])
      Option(p).map(_ => field).toList
    }
  }

  def getPrototypeHash: Int = getHashBase.hashCode

  def isSamePrototype(obj: Any): Boolean = obj match {
    case prototype: Prototype => getPrototypeHash == prototype.hashCode
    case gameObject: GameObject => getPrototypeHash == gameObject.getPrototypeHash
    case _ => false
  }

  def getHashBase: String = {
    val hashBuilder = new StringBuilder()

    hashBuilder.append(this.getClass.getCanonicalName)

    for (f <- annotatedFields) {
      try hashBuilder.append(f.get(this).toString)
      catch {
        case e@(_: IllegalArgumentException | _: IllegalAccessException) =>
          // TODO Auto-generated catch block
          e.printStackTrace()
      }
    }
    hashBuilder.toString
  }

  @throws[CloneNotSupportedException]
  override protected def clone(): AnyRef = super.clone()

  def getName: String = this.getClass.getCanonicalName

  def toPrototype: Prototype = {
    val proto = new Prototype
    proto.className = this.getClass.getCanonicalName

    for (f <- annotatedFields) {
      try {
        /*
               * if (!(f.get(this) instanceof Serializable))
               * System.out.println("WARNING! Field " + f.getName() + " in " +
               * this.getClass().getCanonicalName() +
               * " is not Serializable and cannot be saved!");
               */
        if (f.isAnnotationPresent(classOf[Persistent])) proto.addValue(f, f.get(this))
      } catch {
        case e@(_: IllegalArgumentException | _: IllegalAccessException) =>
          e.printStackTrace()
      }
    }

    proto
  }
}
