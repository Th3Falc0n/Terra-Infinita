package com.dafttech.terra.utils

import com.badlogic.gdx.Gdx
import monix.eval.Task
import monix.execution.Scheduler
import org.lwjgl.opengl.GLContext

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

object RenderThread {
  val executionContext: ExecutionContextExecutor =
    ExecutionContext.fromExecutor { runnable =>
      Option(GLContext.getCapabilities).map { _ =>
        runnable.run()
      }.getOrElse {
        Gdx.app.postRunnable(runnable)
      }
    }

  implicit val scheduler: Scheduler = Scheduler(executionContext)

  def apply[A](task: Task[A]): Task[A] =
    task.executeOn(scheduler)
}
