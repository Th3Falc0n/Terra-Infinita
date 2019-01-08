package com.dafttech.terra.game

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Options.BLOCK_SIZE
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
      val world: World = event.in.get(0, classOf[World])
      val sx: Int = 25 + Gdx.graphics.getWidth / BLOCK_SIZE / 2
      val sy: Int = 25 + Gdx.graphics.getHeight / BLOCK_SIZE / 2


      for (x <- (world.localPlayer.getPosition.x / BLOCK_SIZE - sx).toInt until (world.localPlayer.getPosition.x / BLOCK_SIZE + sx).toInt) {
        for (y <- (world.localPlayer.getPosition.y / BLOCK_SIZE - sy).toInt until (world.localPlayer.getPosition.y / BLOCK_SIZE + sy).toInt) {
          val tile = world.getTile(Vector2i(x, y))
          if (tile != null) tile.tick(event.in.get(1, classOf[Float]))(TilePosition(world, Vector2i(x, y)))
        }
      }
    }
  }

  def init(): Unit = {
  }
}