package fplibrary

object PointFree {

  def compose[A, B, C](ab: A => B, bc: B => C): A => C = a => {
    val b = ab(a)
    val c = bc(b)
    c
  }

  //  def composeKleisli[A, B, C](adb: A => Description[B], bdc: B => Description[C]): A => Description[C] = a => {
  //    val db = adb(a)
  //    val b = db.apply()
  //
  //    val dc = bdc(b)
  //    dc
  //  }

  def composeKleisli[A, B, C, D[_]: Monad](adb: A => D[B], bdc: B => D[C]): A => D[C] = a => {
    val db = adb(a)

    val dc = Monad[D].flatMap(db)(bdc)
    dc
  }

}

