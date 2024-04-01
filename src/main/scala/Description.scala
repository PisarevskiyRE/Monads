package fplibrary

object Description {

  def create[A](a: => A): Description[A] =
    () => a

  def brokenCreate[A]: A => Description[A] = a =>
    () => a
}

