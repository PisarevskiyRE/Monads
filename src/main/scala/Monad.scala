package fplibrary

trait Monad[C[_]] extends FlatMap[C] with Applicative[C] {

  def flatten[B](ccb: C[C[B]]): C[B]
  @inline def join[B](ccb: C[C[B]]): C[B] = flatten(ccb)

}

object Monad extends Summoner[Monad]
