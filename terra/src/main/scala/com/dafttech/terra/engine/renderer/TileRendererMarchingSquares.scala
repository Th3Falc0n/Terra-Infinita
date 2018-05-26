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
  case class CornerState(is: Boolean, t: Tile)

  case class State(tl: CornerState, tr: CornerState, bl: CornerState, br: CornerState, x: Int, y: Int, level: Int = BLOCK_SIZE / 2) {
    def countTrues: Int = (if (tl.is) 1 else 0) + (if (tr.is) 1 else 0) + (if (bl.is) 1 else 0) + (if (br.is) 1 else 0)

    def topLeft: State = {
      new State(
        tl,
        CornerState(countTrues >= 2 ||(tl.is && tr.is && tl.t.connectsTo(tr.t) || (tl.t == tr.t && tl.is)), tl.t),
        CornerState(countTrues >= 2 ||(tl.is && bl.is && tl.t.connectsTo(bl.t) || (tl.t == bl.t && tl.is)), tl.t),
        CornerState(countTrues >= 2 && tl.t.connectsTo(br.t), tl.t),
        x,
        y,
        level / 2
      )
    }

    def topRight: State = {
      new State(
        CornerState(countTrues >= 2 ||(tr.is && tl.is && tr.t.connectsTo(tl.t) || (tr.t == tl.t && tr.is)), tr.t),
        tr,
        CornerState(countTrues >= 2 && tr.t.connectsTo(bl.t), tr.t),
        CornerState(countTrues >= 2 ||(tr.is && br.is && tr.t.connectsTo(br.t) || (tr.t == br.t && tr.is)), tr.t),
        x + level,
        y,
        level / 2
      )
    }

    def bottomLeft: State = {
      new State(
        CornerState(countTrues >= 2 ||(bl.is && tl.is && bl.t.connectsTo(tl.t) || (bl.t == tl.t && bl.is)), bl.t),
        CornerState(countTrues >= 2 && bl.t.connectsTo(tr.t), bl.t),
        bl,
        CornerState(countTrues >= 2 ||(bl.is && br.is && bl.t.connectsTo(br.t) || (bl.t == br.t && bl.is)), bl.t),
        x,
        y + level,
        level / 2
      )
    }

    def bottomRight: State = {
      new State(
        CornerState(countTrues >= 2 && br.t.connectsTo(tl.t), br.t),
        CornerState(countTrues >= 2 ||(br.is && tr.is && br.t.connectsTo(tr.t) || (br.t == tr.t && br.is)), br.t),
        CornerState(countTrues >= 2 ||(br.is && bl.is && br.t.connectsTo(bl.t) || (br.t == bl.t && br.is)), br.t),
        br,
        x + level,
        y + level,
        level / 2
      )
    }
  }

  object State {
    def unapply(arg: State): Option[(Boolean, Boolean, Boolean, Boolean)] = {
      Some((arg.tl.is, arg.tr.is, arg.bl.is, arg.br.is))
    }
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

    if(state.level != 1) {
      doDraw(state.topLeft)
      doDraw(state.topRight)
      doDraw(state.bottomLeft)
      doDraw(state.bottomRight)
    }
    else
    {
      if(state.countTrues >= 2) {
        import com.dafttech.terra.utils.RenderThread._
        val td = tp.getTile.getImage.runSyncUnsafe(Duration.Inf).getTexture.getTextureData
        if(!td.isPrepared) td.prepare()
        dest.setColor(td.consumePixmap().getPixel(state.x, state.y))

        dest.fillRectangle(state.x, BLOCK_SIZE - 2 - state.y, state.level * 2, state.level * 2)
      }
    }
  }

  def draw(screen: AbstractScreen, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition): Unit = {
    val tTL = tp.getTile
    val tTR = tp.withPosition(tp.pos + (1, 0)).getTile
    val tBL = tp.withPosition(tp.pos + (0, 1)).getTile
    val tBR = tp.withPosition(tp.pos + (1, 1)).getTile

    if(tTL.texture == null) {
      doDraw(State(
        CornerState(!tTL.isAir, tTL),
        CornerState(!tTR.isAir, tTR),
        CornerState(!tBL.isAir, tBL),
        CornerState(!tBR.isAir, tBR),
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