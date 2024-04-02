import fplibrary.IO

import scala.annotation.tailrec

object ProgramOld {

  def creteDescription(args: Array[String]): IO[Unit] = IO.create {

    val firstIO: IO[Unit] = displayKleisli(hyphens)

    val secondIO: IO[Unit] = IO.create {
      val _: Unit = firstIO.unsafeRun() //1
      val secondIO: IO[Unit] = displayKleisli(question)

      val secondIOResult: Unit = secondIO.unsafeRun()

      secondIOResult
    }

    val thirdIO: IO[String] = IO.create {
      val _: Unit = secondIO.unsafeRun() //2
      val thirdIO: IO[String] = promptKleisli
      val input: String = thirdIO.unsafeRun()

      input
    }

    val fourthIO: IO[String] = IO.create {

      val input: String = thirdIO.unsafeRun() //3

      val fourthIO: IO[String] = IO.create {
        val integerAmount: Int = convertStringToInt(input)
        val positiveAmount: Int = ensureAmountPositive(integerAmount)
        val balance: Int = round(positiveAmount)
        val message: String = createMessage(balance)
        message
      }
      val message: String = fourthIO.unsafeRun()

      message
    }

    val fifthIO: IO[Unit] = IO.create {
      val message: String = fourthIO.unsafeRun() //4
      val fifthIO: IO[Unit] = displayKleisli(message)
      val fifthIOResult: Unit = fifthIO.unsafeRun()
      fifthIOResult
    }

    val sixthIO: IO[Unit] = IO.create {
      val _: Unit = fifthIO.unsafeRun() //5
      val sixthIO: IO[Unit] = displayKleisli(hyphens)
      val sixthIOResult = sixthIO.unsafeRun()
      sixthIOResult
    }

    sixthIO

  }

  private val hyphens: String =
    "\u2500" * 50

  private val question: String =
    "Some text"

  private def display(input: Any): Unit =
    println(input)

  private def displayKleisli(input: Any): IO[Unit] = {
    IO.create {
      println(input)
    }
  }

  private def promptKleisli: IO[String] = IO.create("5")

  private def prompt(): String =
    scala.io.StdIn.readLine()

  private def convertStringToInt(input: String): Int =
    input.toInt

  private def ensureAmountPositive(amount: Int): Int =
    if (amount < 1)
      1
    else
      amount

  @tailrec
  private def round(amount: Int): Int =
    if (isDivisibleByHundred(amount))
      amount
    else
      round(amount + 1)

  private def isDivisibleByHundred(amount: Int): Boolean =
    amount % 100 == 0

  private def createMessage(balance: Int): String =
    s"Еба боба -  $balance"

}
