package com.dafttech.terra.utils

import monix.eval.{MVar, Task}

final class MLock(mvar: MVar[Unit]) {
  def acquire: Task[Unit] = mvar.take

  def release: Task[Unit] = mvar.put(())

  def bracket[A](f: => Task[A]): Task[A] =
    acquire.bracket(_ => f)(_ => release)
}

object MLock {
  def apply(): Task[MLock] = MVar(()).map(v => new MLock(v))
}
