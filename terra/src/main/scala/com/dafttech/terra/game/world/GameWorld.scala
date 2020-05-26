package com.dafttech.terra.game.world

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.{BodyDef, Box2DDebugRenderer, PolygonShape, World}
import com.dafttech.terra.engine.passes.RenderingPass
import com.dafttech.terra.engine.renderer.TileRendererMarchingSquares
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.entities.{Entity, EntityItem}
import com.dafttech.terra.game.world.environment.{SunMap, Weather, WeatherRainy}
import com.dafttech.terra.game.world.gen.WorldGenerator
import com.dafttech.terra.game.world.items.persistence.GameObject
import com.dafttech.terra.game.world.subtiles.Subtile
import com.dafttech.terra.game.world.tiles.{Tile, TileAir}
import com.dafttech.terra.game.{Events, TimeKeeping}
import com.dafttech.terra.resources.Options.METERS_PER_BLOCK

class GameWorld(val size: Vector2i) extends GameObject {


  var time: Float = 0
  var lastTick: Float = 0
  var tickLength: Float = 0.005f

  val physicsWorld = new World(new Vector2(0, 9.81f), true)
  val b2drdr = new Box2DDebugRenderer()


  val tiles: Array[Array[Tile]] = Array.ofDim[Tile](size.x, size.y)
  private var entities: Seq[Entity] = Seq.empty
  var sunmap: SunMap = new SunMap
  var weather: Weather = new WeatherRainy

  var localPlayer: Player = new Player(Vector2d(500, 0))(this)
  //localPlayer.body.setLinearVelocity(1, 1)

  val renderer = new TileRendererMarchingSquares()

  var gen: WorldGenerator = new WorldGenerator(this)
  gen.generateWorld(this)

  for(x <- 0 until size.x) {
    for(y <- 0 until size.y) {
      val tile = getTile(Vector2i(x, y))

      val l = getTile(Vector2i(x-1, y))
      val r = getTile(Vector2i(x+1, y))
      val t = getTile(Vector2i(x, y-1))
      val b = getTile(Vector2i(x, y+1))

      if(!tile.isAir && (l.isAir || r.isAir || t.isAir || b.isAir)) {
        val bodyDef = new BodyDef

        bodyDef.`type` = BodyDef.BodyType.StaticBody
        bodyDef.position.set(x * METERS_PER_BLOCK, y * METERS_PER_BLOCK)

        val shape = new PolygonShape()

        shape.setAsBox(METERS_PER_BLOCK / 2f, METERS_PER_BLOCK / 2f)

        println(s"Created physics tile at mpb=${METERS_PER_BLOCK} xc=${x} x=${x*METERS_PER_BLOCK} yc=${y} y=${y*METERS_PER_BLOCK}")

        val body = physicsWorld.createBody(bodyDef)

        body.createFixture(shape, 0)
        shape.dispose()
      }
    }
  }

  def getEntities: Seq[Entity] = entities

  def addEntity(entity: Entity) = {
    entities = entities :+ entity
  }

  def getTile(pos: Vector2i): Tile = {
    if(pos.x >= 0 && pos.x < size.x && pos.y >= 0 && pos.y < size.y) {
      tiles(pos.x)(pos.y)
    }
    else {
      new TileAir()
    }
  }

  def getNextTileBelow(pos: Vector2i): Option[TilePosition] = {
    var y = pos.y

    y += 1
    while (y < size.y) {
      val tile = Option(getTile(Vector2i(pos.x, y))).filter(!_.isAir)
      if (tile.isDefined) return Some(TilePosition(this, Vector2i(pos.x, y)))
      y += 1
    }
    None
  }

  def getNextTileAbove(pos: Vector2i): Option[TilePosition] = {
    var y = pos.y

    y -= 1
    while (y >= 0) {
      if (getTile(Vector2i(pos.x, y)) != null && !getTile(Vector2i(pos.x, y)).isAir) return Some(TilePosition(this, Vector2i(pos.x, y)))
      y -= 1
    }
    None
  }

  def setTile(pos: Vector2i, _tile: Tile, notify: Boolean): GameWorld = {
    var tile = _tile

    var tileIndependentSubtiles: List[Subtile] = Nil
    var notifyOldRemoval: Vector2i = null

    if (tile != null && pos != null && (getTile(pos) eq tile)) {
      notifyOldRemoval = pos
      setTile(notifyOldRemoval, null, false)
    }

    if (tile == null) tile = new TileAir

    val oldTile: Tile = getTile(pos)

    if (oldTile != null) {
      sunmap.postTileRemove(TilePosition(this, pos))
      for (subtile <- oldTile.getSubtiles)
        if (subtile.isTileIndependent)
          tileIndependentSubtiles = tileIndependentSubtiles :+ subtile
      oldTile.removeAndUnlinkSubtile(tileIndependentSubtiles: _*)
    }

    if (!Events.EVENTMANAGER.callSync(Events.EVENT_BLOCKCHANGE, tile).isCancelled)
      tiles(pos.x)(pos.y) = tile

    tile.addSubtile(tileIndependentSubtiles: _*)

    sunmap.postTilePlace(TilePosition(this, pos))

    if (notify) {
      tile.onTileSet(TilePosition(this, pos))

      if (notifyOldRemoval != null) notifyNeighborTiles(Vector2i(notifyOldRemoval.x, notifyOldRemoval.y))
      notifyNeighborTiles(Vector2i(pos.x, pos.y))
    }

    renderer.invalidateCache(TilePosition(this, pos))
    this
  }

  def placeTile(pos: Vector2i, t: Tile, causer: Entity): Boolean = {
    val tile: Tile = getTile(pos)
    if (tile.isAir || tile.isReplacable) {
      setTile(pos, t, notify = true)
      tile.onTilePlaced(causer)(TilePosition(this, pos))
      true
    } else
      false
  }

  def destroyTile(pos: Vector2i, causer: Entity): Seq[EntityItem] = {
    val tile: Tile = getTile(pos)
    if (tile.isBreakable) {
      val entity = tile.spawnAsEntity(TilePosition(this, pos))
      setTile(pos, null, notify = true)
      tile.onTileDestroyed(causer)(TilePosition(this, pos))
      entity
    } else
      List.empty
  }

  private def notifyNeighborTiles(pos: Vector2i): Unit = {
    var tile: Tile = null
    tile = getTile(pos + (1, 0))
    tile.onNeighborChange(TilePosition(this, pos))(TilePosition(this, pos + (1, 0)))
    tile = getTile(pos + (0, 1))
    tile.onNeighborChange(TilePosition(this, pos))(TilePosition(this, pos + (0, 1)))
    tile = getTile(pos - (1, 0))
    tile.onNeighborChange(TilePosition(this, pos))(TilePosition(this, pos - (1, 0)))
    tile = getTile(pos - (0, 1))
    tile.onNeighborChange(TilePosition(this, pos))(TilePosition(this, pos - (0, 1)))
  }

  def removeEntity(entity: Entity): Unit = {
    entity.onRemove()
    entities = entities.filter(_ != entity)
  }

  def update(delta: Float): Unit = {
    time += delta
    weather.update(delta)(this)
    val sx: Int = (25 + AbstractScreen.getVWidth / METERS_PER_BLOCK / 2).toInt
    val sy: Int = (25 + AbstractScreen.getVHeight / METERS_PER_BLOCK / 2).toInt

    val px: Int = (localPlayer.getPosition.x / METERS_PER_BLOCK).toInt
    val py: Int = (localPlayer.getPosition.y / METERS_PER_BLOCK).toInt

    var tile: Tile = null
    for {
      x <- (px - sx) until (px + sx)
      y <- (py - sy) until (py + sy)
    } {
      tile = getTile(Vector2i(x, y))
      if (tile != null) tile.update(delta)(TilePosition(this, Vector2i(x, y)))
    }

    /*var x: Int = localPlayer.getPosition.x.toInt / BLOCK_SIZE - sx
    while (x < localPlayer.getPosition.x.toInt / BLOCK_SIZE + sx) {
      {
        {
          var y: Int = localPlayer.getPosition.y.toInt / BLOCK_SIZE - sy
          while (y < localPlayer.getPosition.y.toInt / BLOCK_SIZE + sy) {
            {
              tile = getTile(x, y)
              if (tile != null) tile.update(this, delta)
            }
            {
              y += 1;
              y - 1
            }
          }
        }
      }
      {
        x += 1;
        x - 1
      }
    }*/

    TimeKeeping.timeKeeping("Tile update")

    for (entity <- entities) {
      entity.update(delta)(TilePosition(this, entity.getPosition.toWorldPosition))
    }
    TimeKeeping.timeKeeping("Entity update")

    Events.EVENTMANAGER.callSync(Events.EVENT_WORLDTICK, this, (time - lastTick).asInstanceOf[AnyRef])
    lastTick = time
    TimeKeeping.timeKeeping("Tick update")

    physicsWorld.step(delta, 6, 2);

    //println(s"PX: ${localPlayer.body.getPosition.x} PY: ${localPlayer.body.getPosition.y}")

    TimeKeeping.timeKeeping("Physics update")
  }

  def isInRenderRange(position: Vector2d): Boolean = true

  def draw(screen: AbstractScreen, pointOfView: Entity): Unit = {
    RenderingPass.rpBackground.applyPass(screen, pointOfView, this)
    TimeKeeping.timeKeeping("rpBack")

    RenderingPass.rpObjects.applyPass(screen, pointOfView, this)
    TimeKeeping.timeKeeping("rpObj")

    RenderingPass.rpLighting.applyPass(screen, pointOfView, this)
    TimeKeeping.timeKeeping("rpLig")

    b2drdr.render(physicsWorld, screen.projectionIngame.cpy().translate(-pointOfView.getPosition.x.toFloat + AbstractScreen.getVWidth/2, -pointOfView.getPosition.y.toFloat + AbstractScreen.getVHeight/2,  0))

  }
}