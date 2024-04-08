package fplibrary

import fplibrary.Maybe.M

sealed abstract class Maybe[+A] extends Product with Serializable {

  final def getOrElse[Super >: A](alternative: => Super): Super = this match {
    case Maybe.Just(a) => a
    case Maybe.Nothing => alternative
  }

  final def mapOrElse[B](alternative: => B)(ab: A => B): B = this match {
    case Maybe.Just(a) => ab(a)
    case Maybe.Nothing => alternative
  }

  final def isJust: Boolean =
    isInstanceOf[Maybe.Just[_]]

  final def isNothing: Boolean =
    !isJust
}

object Maybe {
  final case class Just[+A](a: A) extends Maybe[A]

  case object Nothing extends Maybe[scala.Nothing]

  implicit val M: Monad[Maybe] = new Monad[Maybe] {

    final override def pure[A](a: => A): Maybe[A] =
      Just(a)

    final override def map[A, B](ca: Maybe[A])(ab: A => B): Maybe[B] = ca match {
      case Just(a) => pure(ab(a))
      case Nothing => Nothing
    }

    final override def flatMap[A, B](ca: Maybe[A])(acb: A => Maybe[B]): Maybe[B] = ca match {
      case Just(a) => acb(a)
      case Nothing => Nothing
    }

    final override def flatten[A](cca: Maybe[Maybe[A]]): Maybe[A] = cca match {
      case Just(ma) => ma
      case Nothing  => Nothing
    }

  }
}
