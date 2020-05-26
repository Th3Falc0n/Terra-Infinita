package com.dafttech.terra.utils

import com.badlogic.gdx.Gdx
import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.schedulers.CanBlock
import org.lwjgl.opengl.GLContext

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.util.Try

object RenderThread {
  def isCurrentThread: Boolean = Try(GLContext.getCapabilities).isSuccess

  val executionContext: ExecutionContextExecutor =
    ExecutionContext.fromExecutor { runnable =>
      if (isCurrentThread) runnable.run()
      else Gdx.app.postRunnable(runnable)
    }

  implicit val scheduler: Scheduler = Scheduler(executionContext)

  def apply[A](task: Task[A]): Task[A] = Task.defer {
    if (isCurrentThread)
      task.runSyncStep(scheduler) match {
        case Right(result) => Task.now(result)
        case Left(task) => task.executeOn(scheduler)
      }
    else
      task.executeOn(scheduler)
  }

  def sync[A](task: Task[A])(implicit scheduler: Scheduler): Task[A] = Task.eval {
    task.runSyncUnsafe(Duration.Inf)(scheduler, CanBlock.permit)
  }
}
