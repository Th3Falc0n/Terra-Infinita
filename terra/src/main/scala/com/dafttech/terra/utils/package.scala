package com.dafttech.terra

import monix.eval.Task
import monix.execution.Scheduler

package object utils {

  implicit class SyncTaskOps[A](val task: Task[A]) extends AnyVal {
    def getSyncUnsafe: A = task.runSyncMaybe(Scheduler.global).getOrElse {
      throw new RuntimeException("Task not synchronous!")
    }
  }

}
