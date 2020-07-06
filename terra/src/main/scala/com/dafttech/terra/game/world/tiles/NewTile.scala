package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import monix.eval.Task

trait NewTile[Inst <: NewTileInstance] {
  def name: String

  def getImage: Task[TextureRegion]

  def newInstance: NewTileInstance
}

class NewTileInstance {

}
