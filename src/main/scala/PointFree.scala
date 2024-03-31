package fplibrary

object PointFree {

  def compose[A, B, C](ab: A => B, bc: B => C): A => C = a => {

    val b = ab(a)
    val c = bc(b)

    c
  }

}

