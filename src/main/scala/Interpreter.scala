import fplibrary._

object Interpreter {

  def main(args: Array[String]): Unit = {

    println(Console.RED)

    def description: IO[Unit] =
      ProgramOld.creteDescription(args)

    def interpreter[A](description: IO[A]): A =
      description.unsafeRun()

    println(Console.GREEN)
    interpreter(description)
    println(Console.RESET)

  }
}
