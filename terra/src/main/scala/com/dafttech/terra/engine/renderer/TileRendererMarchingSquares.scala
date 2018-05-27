package com.dafttech.terra.engine.renderer

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.BLOCK_SIZE
import org.lwjgl.opengl.PixelFormatLWJGL

import scala.concurrent.duration.Duration

object TileRendererMarchingSquares {
  var $Instance: TileRenderer = new TileRendererMarchingSquares
}

class TileRendererMarchingSquares extends TileRenderer {

  case class CornerState(pressure: Int, t: Tile)

  case class State(tl: CornerState, tr: CornerState, bl: CornerState, br: CornerState, x: Int, y: Int, level: Int = BLOCK_SIZE / 2) {

    val l = CornerState((tl.pressure + bl.pressure) / 2, if (tl.pressure > bl.pressure) tl.t else bl.t)
    val t = CornerState((tl.pressure + tr.pressure) / 2, if (tl.pressure > tr.pressure) tl.t else tr.t)
    val r = CornerState((tr.pressure + br.pressure) / 2, if (tr.pressure > br.pressure) tr.t else br.t)
    val b = CornerState((bl.pressure + br.pressure) / 2, if (bl.pressure > br.pressure) bl.t else br.t)

    val m = CornerState(
      (tl.pressure + tr.pressure + bl.pressure + br.pressure) / 4,
      Seq(tl, tr, bl, br).maxBy(_.pressure).t
    )

    def topLeft: State = new State(tl, t, l, m, x, y, level / 2)

    def topRight: State = new State(t, tr, m, r, x + level, y, level / 2)

    def bottomLeft: State = new State(l, m, bl, b, x, y + level, level / 2)

    def bottomRight: State = new State(m, r, b, br, x + level, y + level, level / 2)
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
      if (state.m.pressure > 3) {
        import com.dafttech.terra.utils.RenderThread._
        val td = tp.getTile.getImage.runSyncUnsafe(Duration.Inf).getTexture.getTextureData
        if(!td.isPrepared) td.prepare()

        val xm = BLOCK_SIZE.toFloat / td.getWidth
        val ym = BLOCK_SIZE.toFloat / td.getHeight

        dest.setColor(td.consumePixmap().getPixel((state.x / xm).toInt, (state.y / ym).toInt))

        dest.fillRectangle(state.x, BLOCK_SIZE - 2 - state.y, state.level * 2, state.level * 2)
      }
    }
  }

  def draw(screen: AbstractScreen, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition): Unit = {
    val tTL = tp.getTile
    val tTR = tp.withPosition(tp.pos + (1, 0)).getTile
    val tBL = tp.withPosition(tp.pos + (0, 1)).getTile
    val tBR = tp.withPosition(tp.pos + (1, 1)).getTile

    if (tTL.texture == null) {
      doDraw(State(
        CornerState(if(!tTL.isAir) BLOCK_SIZE else 0, tTL),
        CornerState(if(!tTR.isAir) BLOCK_SIZE else 0, tTR),
        CornerState(if(!tBL.isAir) BLOCK_SIZE else 0, tBL),
        CornerState(if(!tBR.isAir) BLOCK_SIZE else 0, tBR),
        0,
        0)
      )(tTL.pixmap, tp)

      tTL.texture = new Texture(tTL.pixmap)
    }

    val tpx = tp.pos.toScreenPos(pointOfView).x.toFloat
    val tpy = tp.pos.toScreenPos(pointOfView).y.toFloat

    screen.batch.draw(tTL.texture, tpx, tpy)
  }
}