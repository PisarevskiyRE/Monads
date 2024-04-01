package object fplibrary {

  private type Thunk[A] = () => A
  type Description[A] = Thunk[A]

  private type RegularArrow[A, B] = A => B
  private type KleisliArrow[A, B, C[_]] = A => C[B]

  implicit final class InfixNotationForPointFree[A, B](private val ab: A => B) extends AnyVal {
    @inline def `;`[C](bc: B => C): A => C = PointFree.compose(ab, bc)
    @inline def `.`[C](bc: B => C): A => C = PointFree.compose(ab, bc)
    @inline def `-->`[C](bc: B => C): A => C = PointFree.compose(ab, bc)
  }

  implicit final class InfixNotationForPointFreeKleisli[A, B](private val adb: A => Description[B]) extends AnyVal {
    @inline def `;;`[C](bdc: B => Description[C]): A => Description[C] = PointFree.composeKleisli(adb, bdc)
    @inline def `>=>`[C](bdc: B => Description[C]): A => Description[C] = PointFree.composeKleisli(adb, bdc)

  }
}
