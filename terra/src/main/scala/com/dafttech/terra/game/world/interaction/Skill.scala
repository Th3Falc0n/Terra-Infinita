package com.dafttech.terra.game.world.interaction

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInventory, Vector2}
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

  override def drawInventory(pos: Vector2, screen: AbstractScreen): Unit = {
    // TODO: Scheduler
    import monix.execution.Scheduler.Implicits.global
    val image = getImage.runSyncUnsafe(5.seconds)
    screen.batch.draw(image, pos.xFloat + 4, pos.yFloat + 4, 24, 24)
  }

  override def update(delta: Float): Unit = {
    // NOPE!
  }
}