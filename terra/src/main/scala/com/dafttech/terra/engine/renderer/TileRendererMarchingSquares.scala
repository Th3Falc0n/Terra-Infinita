package com.dafttech.terra.engine.renderer

import com.badlogic.gdx.graphics.{ Color, Pixmap, Texture }
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.{ AbstractScreen, TilePosition }
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.{ Tile, TileFalling }
import com.dafttech.terra.resources.Options
import com.dafttech.terra.resources.Options.BLOCK_SIZE

import scala.collection.mutable
import scala.concurrent.duration.Duration

object TileRendererMarchingSquares {
  var $Instance: TileRenderer = new TileRendererMarchingSquares
}

class TileCache {
  val pixmap = new Pixmap(Options.BLOCK_SIZE, Options.BLOCK_SIZE, Pixmap.Format.RGBA8888)
  var texture: Texture = _
}

class TileRendererMarchingSquares extends TileRenderer {
  val renderPositions = new mutable.HashMap[TilePosition, TileCache]

  case class CornerState(pressure: Int, t: Tile)

  case class State(tl: CornerState, tr: CornerState, bl: CornerState, br: CornerState, x: Int, y: Int, level: Int = BLOCK_SIZE / 2) {

    val l = CornerState((tl.pressure + bl.pressure) / 2, if (tl.pressure > bl.pressure) tl.t else bl.t)
    val t = CornerState((tl.pressure + tr.pressure) / 2, if (tl.pressure > tr.pressure) tl.t else tr.t)
    val r = CornerState((tr.pressure + br.pressure) / 2, if (tr.pressure > br.pressure) tr.t else br.t)
    val b = CornerState((bl.pressure + br.pressure) / 2, if (bl.pressure > br.pressure) bl.t else br.t)

    val m = CornerState(
      (l.pressure + t.pressure + r.pressure + b.pressure) / 4 + (TerraInfinita.rnd.nextInt(3) - 2),
      Seq(l, t, r, b).maxBy(_.pressure).t
    )

    def topLeft: State = State(tl, t, l, m, x, y, level / 2)
    def topRight: State = State(t, tr, m, r, x + level, y, level / 2)
    def bottomLeft: State = State(l, m, bl, b, x, y + level, level / 2)
    def bottomRight: State = State(m, r, b, br, x + level, y + level, level / 2)
  }

  def invalidateCache(tp: TilePosition): Unit = {
    renderPositions.remove(tp)
    renderPositions.remove(tp.withPosition(tp.pos - (0, 1)))
    renderPositions.remove(tp.withPosition(tp.pos - (1, 0)))
    renderPositions.remove(tp.withPosition(tp.pos - (1, 1)))
  }

  //TL, TR, BL, BR
  def doDraw(state: State)(implicit dest: Pixmap, tp: TilePosition): Unit = {
    /*if(state.level == BLOCK_SIZE / 2 && state.countTrues >= 0) {
      if(state.tl.is) {
        dest.setColor(Color.GREEN)
      }
      else
      {
        dest.setColor(Color.RED)
      }
      dest.drawRectangle(0, state.level, state.level, state.level)

      if(state.tr.is) {
        dest.setColor(Color.GREEN)
      }
      else
      {
        dest.setColor(Color.RED)
      }
      dest.drawRectangle(state.level, state.level, state.level, state.level)

      if(state.bl.is) {
        dest.setColor(Color.GREEN)
      }
      else
      {
        dest.setColor(Color.RED)
      }
      dest.drawRectangle(0, 0, state.level, state.level)

      if(state.br.is) {
        dest.setColor(Color.GREEN)
      }
      else
      {
        dest.setColor(Color.RED)
      }
      dest.drawRectangle(state.level, 0, state.level, state.level)
    }

    dest.setColor(Color.CYAN)
    dest.drawRectangle(0, 0, BLOCK_SIZE, BLOCK_SIZE)*/

    if (state.level != 1) {
      doDraw(state.topLeft)
      doDraw(state.topRight)
      doDraw(state.bottomLeft)
      doDraw(state.bottomRight)
    }
    else {
      if (state.m.pressure >= BLOCK_SIZE / 2) {
        import com.dafttech.terra.utils.RenderThread._
        val td = state.m.t.getImage.runSyncUnsafe(Duration.Inf).getTexture.getTextureData
        if (!td.isPrepared) td.prepare()

        val xm = BLOCK_SIZE.toFloat / td.getWidth
        val ym = BLOCK_SIZE.toFloat / td.getHeight

        if(state.m.pressure ==  BLOCK_SIZE / 2) {
          dest.setColor(td.consumePixmap().getPixel((state.x / xm).toInt, (state.y / ym).toInt) & 0x000000FF)
        }
        else {
          dest.setColor(td.consumePixmap().getPixel((state.x / xm).toInt, (state.y / ym).toInt))
        }

        dest.fillRectangle(state.x, BLOCK_SIZE - 2 - state.y, 2, 2)
      }
    }
  }

  def draw(screen: AbstractScreen, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition): Unit = {
    val tTL = tp.getTile
    val tTR = tp.withPosition(tp.pos + (1, 0)).getTile
    val tBL = tp.withPosition(tp.pos + (0, 1)).getTile
    val tBR = tp.withPosition(tp.pos + (1, 1)).getTile

    if (renderPositions.getOrElseUpdate(tp, new TileCache).texture == null) {
      doDraw(State(
        CornerState(if (!tTL.isAir) BLOCK_SIZE else 0, tTL),
        CornerState(if (!tTR.isAir) BLOCK_SIZE else 0, tTR),
        CornerState(if (!tBL.isAir) BLOCK_SIZE else 0, tBL),
        CornerState(if (!tBR.isAir) BLOCK_SIZE else 0, tBR),
        0,
        0)
      )(renderPositions(tp).pixmap, tp)

      renderPositions(tp).texture = new Texture(renderPositions(tp).pixmap)
    }

    val tpx = tp.pos.toScreenPos(pointOfView).x.toFloat
    val tpy = tp.pos.toScreenPos(pointOfView).y.toFloat

    val yOff = tp.getTile match {
      case falling: TileFalling => falling.renderOffset.x.toFloat
      case _ => 0
    }

    screen.batch.draw(renderPositions(tp).texture, tpx + BLOCK_SIZE / 2, tpy + BLOCK_SIZE / 2 + yOff)
  }
}