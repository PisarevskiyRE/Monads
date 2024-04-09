import fplibrary._

object Interpreter {

  def main(args: Array[String]): Unit = {

    //    println(Console.RED)
    //
    //    def description: IO[Unit] =
    //      ProgramOld.createDescription(args)
    //
    //    def interpreter[A](description: IO[A]): A =
    //      description.unsafeRun()
    //
    //    println(Console.GREEN)
    //    interpreter(description)
    //    println(Console.RESET)

    val stateless: Int => String = i =>
      s"$i is ${if (i % 2 == 0) "even" else "odd"}"

    type IntialState = Int
    type NextState = Int

    val stateful: IntialState => (String, NextState) = i =>
      stateless(i) -> (i + 1)

    def run[S, A]: S => (A, S) = ???

    val t1 @ (a, s1) = stateful(0)
    val t2 @ (b, s2) = stateful(s1)
    val t3 @ (c, s3) = stateful(s2)

    println(t1)
    println(t2)
    println(t3)
    println("-" * 50)
    println(stateful(s1))

  }
}
