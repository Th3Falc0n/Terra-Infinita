package com.dafttech.terra.game

import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.resources.Options.METERS_PER_BLOCK
import org.lolhens.eventmanager.{Event, EventManager, EventType, ListenerContainer}
import org.lolhens.storage.tuple.Tuple

object Events {
  val EVENTMANAGER: EventManager = new EventManager
  val EVENT_INITPRE: EventType = new EventType("INITPRE", EVENTMANAGER)
  val EVENT_INITPOST: EventType = new EventType("INITPOST", EVENTMANAGER)
  val EVENT_WINRESIZE: EventType = new EventType("WINRESIZE", EVENTMANAGER)
  val EVENT_WINPAUSE: EventType = new EventType("WINPAUSE", EVENTMANAGER)
  val EVENT_WINRESUME: EventType = new EventType("WINRESUME", EVENTMANAGER)
  val EVENT_WINDISPOSE: EventType = new EventType("WINDISPOSE", EVENTMANAGER)
  val EVENT_BLOCKCHANGE: EventType = new EventType("BLOCKCHANGE", EVENTMANAGER)
  val EVENT_CHATCOMMAND: EventType = new EventType("CHATCOMMAND", EVENTMANAGER)
  val EVENT_KEYDOWN: EventType = new EventType("KEYDOWN", EVENTMANAGER) {
    protected override def isFiltered(event: Event, filter: Tuple, eventListener: ListenerContainer): Boolean = {
      filter.get(0, classOf[String]) == event.in.get(0, classOf[String])
    }
  }
  val EVENT_KEYUP: EventType = new EventType("KEYUP", EVENTMANAGER) {
    protected override def isFiltered(event: Event, filter: Tuple, eventListener: ListenerContainer): Boolean = {
      filter.get(0, classOf[String]) == event.in.get(0, classOf[String])
    }
  }
  val EVENT_MOUSEDOWN: EventType = new EventType("MOUSEDOWN", EVENTMANAGER)
  val EVENT_MOUSEUP: EventType = new EventType("MOUSEUP", EVENTMANAGER)
  val EVENT_MOUSEMOVE: EventType = new EventType("MOUSEMOVE", EVENTMANAGER)
  val EVENT_SCROLL: EventType = new EventType("SCROLL", EVENTMANAGER)

  /*val EVENT_BLOCKUPDATE: EventType = new EventType("BLOCKUPDATE", EVENTMANAGER) {
    protected override def onEvent(event: Event) {
      val tile: Tile = event.in.get(0, classOf[World]).getTile(event.in.get[Integer](1, classOf[Integer]), event.in.get[Integer](2, classOf[Integer]))
      if (tile != null) tile.update(event.in.get(0, classOf[World]), 0)
    }
  }*/

  val EVENT_WORLDTICK: EventType = new EventType("GAMETICK", EVENTMANAGER) {
    protected override def onEvent(event: Event) {
      val world: GameWorld = event.in.get(0, classOf[GameWorld])
      val sx: Int = (25 + AbstractScreen.getVWidth / METERS_PER_BLOCK / 2).toInt
      val sy: Int = (25 + AbstractScreen.getVHeight / METERS_PER_BLOCK / 2).toInt


      for (x <- (world.localPlayer.getPosition.x / METERS_PER_BLOCK - sx).toInt until (world.localPlayer.getPosition.x / METERS_PER_BLOCK + sx).toInt) {
        for (y <- (world.localPlayer.getPosition.y / METERS_PER_BLOCK - sy).toInt until (world.localPlayer.getPosition.y / METERS_PER_BLOCK + sy).toInt) {
          val tile = world.getTile(Vector2i(x, y))
          if (tile != null) tile.tile.tick(tile, event.in.get(1, classOf[Float]))(TilePosition(world, Vector2i(x, y)))
        }
      }
    }
  }

  def init(): Unit = {
  }
}