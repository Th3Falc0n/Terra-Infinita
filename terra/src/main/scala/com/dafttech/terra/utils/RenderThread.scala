package com.dafttech.terra.utils

import com.badlogic.gdx.Gdx
import monix.eval.Task
import monix.execution.Scheduler
import org.lwjgl.opengl.GLContext

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}

object RenderThread {
  def isCurrentThread: Boolean = GLContext.getCapabilities != null

  val executionContext: ExecutionContextExecutor =
    ExecutionContext.fromExecutor { runnable =>
      if (isCurrentThread) runnable.run()
      else Gdx.app.postRunnable(runnable)
    }

  implicit val scheduler: Scheduler = Scheduler(executionContext)

  def apply[A](task: Task[A]): Task[A] = Task.defer {
    if (isCurrentThread)
      task.runSyncMaybe(scheduler) match {
        case Right(result) => Task.now(result)
        case Left(future) => Task.fromFuture(future)
      }
    else
      task.executeOn(scheduler)
  }

  def sync[E](task: Task[E])(implicit scheduler: Scheduler): Task[E] = Task.defer {
    task.runSyncMaybe(scheduler) match {
      case Right(result) => Task.now(result)
      case Left(future) => Task.eval(Await.result(future, Duration.Inf))
    }
  }
}
