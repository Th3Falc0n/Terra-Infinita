package com.dafttech.terra.game.world.interaction

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInventory}
import monix.eval.Task

import scala.concurrent.duration._

object Skill {

  sealed trait ActivationMode

  object ActivationMode {

    case object Execute extends ActivationMode

    case object Toggle extends ActivationMode

    case object Hold extends ActivationMode

    case object Passive extends ActivationMode

  }

}

abstract class Skill extends IDrawableInventory {
  def getImage: Task[TextureRegion]

  override def drawInventory(pos: Vector2d, screen: AbstractScreen): Unit = {
    // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    val image = getImage.runSyncUnsafe(5.seconds)
    screen.batch.draw(image, pos.x.toFloat + 4, pos.y.toFloat + 4, 24, 24)
  }

  override def update(delta: Float): Unit = {
    // NOPE!
  }
}