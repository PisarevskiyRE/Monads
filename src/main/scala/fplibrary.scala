package object fplibrary {

  private type Thunk[A] = () => A
  type Description[A] = Thunk[A]

}
