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

}
