package fplibrary

final case class Reader[-D, +A](run: D => A) extends AnyVal

object Reader {

  def create[D, A](a: => A): Reader[D, A] =
    Reader(s => (a, s))

  implicit def M[D]: Monad[Reader[D, *]] = new Monad[Reader[D, *]] {

    final override def pure[A](a: => A): Reader[D, A] =
      create(a)

    final override def map[A, B](ca: Reader[D, A])(ab: A => B): Reader[D, B] =
      Reader { s =>

        val (a, s1) = ca.run(s)
        val b = ab(a)
        b -> s1
      }

    final override def flatMap[A, B](ca: Reader[D, A])(acb: A => Reader[D, B]): Reader[D, B] =
      Reader { s =>
        val (a, s1) = ca.run(s)
        val cb = acb(a)
        val (b, s2) = cb.run(s1)

        b -> s2
      }

    final override def flatten[A](cca: Reader[D, Reader[D, A]]): Reader[D, A] =
      Reader { s =>
        val (ca, s1) = cca.run(s)
        val (a, s2) = ca.run(s1)
        a -> s2
      }
  }
}

