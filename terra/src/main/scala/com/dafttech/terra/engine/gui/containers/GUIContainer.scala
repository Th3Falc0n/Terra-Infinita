package com.dafttech.terra.engine.gui.containers

import com.dafttech.terra.engine.gui.GUIObject
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import org.lolhens.eventmanager.{Event, EventListener}

import scala.collection.mutable

abstract class GUIContainer(p: Vector2, s: Vector2) extends GUIObject(p, s) {
  val objects: mutable.ArrayBuffer[GUIObject] = new mutable.ArrayBuffer[GUIObject]

  def draw(screen: AbstractScreen): Unit = {
    for (o <- objects) {
      o.draw(screen)
    }
  }

  def clearObjects: Unit = objects.clear

  def containsObject(`object`: GUIObject): Boolean = objects.contains(`object`)

  def addObject(gObj: GUIObject): Unit = {
    objects += gObj
    gObj.setContainer(this)
    gObj.applyAssignedAnchorSet
  }

  def removeObject(gObj: GUIObject): Unit = {
    objects -= gObj
  }

  override def update(delta: Float): Unit = {
    super.update(delta)
    for (e <- objects) {
      e.update(delta)
    }
  }

  def applyAllAssignedAnchorSets: Unit = {
    applyAssignedAnchorSet
    for (o <- objects) {
      o match {
        case container1: GUIContainer =>
          container1.applyAllAssignedAnchorSets
        case _ =>
      }
      o.applyAssignedAnchorSet
    }
  }

  @EventListener(Array("MOUSEMOVE")) def onEventMouseMove(event: Event): Unit = {
    if (isInActiveHierarchy || this.providesActiveHierarchy) {
      for (e <- objects) {
        val x: Int = event.in.get[Integer](1, classOf[Integer])
        val y: Int = event.in.get[Integer](2, classOf[Integer])
        val p: Vector2 = e.getScreenPosition
        if (e.size != null) {
          if (x > p.x && x < p.x + e.size.x && y > p.y && y < p.y + e.size.y && !e.mouseHover) {
            e.onMouseIn
            e.mouseHover = true
          }
          else if (!(x > p.x && x < p.x + e.size.x && y > p.y && y < p.y + e.size.y) && e.mouseHover) {
            e.onMouseOut
            e.mouseHover = false
          }
        }
        else {
          System.out.println("Size null in " + e)
        }
      }
    }
  }

  @EventListener(Array("MOUSEDOWN")) def onEventMouseDown(event: Event): Unit = {
    if (isInActiveHierarchy || this.providesActiveHierarchy) {
      for (e <- objects) {
        val button: Int = event.in.get[Integer](0, classOf[Integer])
        val x: Int = event.in.get[Integer](1, classOf[Integer])
        val y: Int = event.in.get[Integer](2, classOf[Integer])
        val p: Vector2 = e.getScreenPosition
        if (e.size != null) {
          if (x > p.x && x < p.x + e.size.x && y > p.y && y < p.y + e.size.y) {
            e.onClick(button)
          }
        }
        else {
          System.out.println("Size null in " + e)
        }
      }
    }
  }
}