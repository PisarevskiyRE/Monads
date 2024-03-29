import fplibrary._

object Interpreter {
  def main(args: Array[String]): Unit = {

    println(Console.RED)

    def description: Description[Unit] =
      Program.creteDescription(args)

    def interpreter[A](description: Description[A]): A =
      description.apply()

    println(Console.GREEN)
    interpreter(description)
    println(Console.RESET)

  }
}
