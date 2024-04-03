import fplibrary._

import scala.annotation.tailrec

object ProgramOld {

  def creteDescription(args: Array[String]): IO[Unit] =
    displayKleisli(hyphens).flatMap { _ =>
      displayKleisli(question).flatMap { _ =>
        promptKleisli.flatMap { input =>
          IO.create {
            val integerAmount: Int = convertStringToInt(input)
            val positiveAmount: Int = ensureAmountPositive(integerAmount)
            val balance: Int = round(positiveAmount)
            val message: String = createMessage(balance)
            message
          }.flatMap { message =>
            displayKleisli(message).flatMap { _ =>
              displayKleisli(hyphens)
            }
          }
        }
      }
    }

//  def creteDescription(args: Array[String]): IO[Unit] = for {
//    _ <- displayKleisli(hyphens)
//    _ <- displayKleisli(question)
//    input <- promptKleisli
//    message <-  IO.create {
//                  val integerAmount: Int = convertStringToInt(input)
//                  val positiveAmount: Int = ensureAmountPositive(integerAmount)
//                  val balance: Int = round(positiveAmount)
//                  val message: String = createMessage(balance)
//                  message
//                }
//    _ <- displayKleisli(message)
//    _ <- displayKleisli(hyphens)
//  } yield ()


  def associativityLaw = {
    type AnythingThatHasMonad[A] = IO[A]
    type AnyType = Int
    val anyValue: AnyType = 5

    val io: AnythingThatHasMonad[AnyType] = IO.create(anyValue)
    val f: AnyType => AnythingThatHasMonad[String] = a => IO.create(a.toString)
    val g: String => AnythingThatHasMonad[Unit] = a => IO.create(println(a))

    io.flatMap(f).flatMap(g) == io.flatMap(a => f(a).flatMap(g))
    io.flatMap(f).flatMap(g) == io.flatMap(f >=> g)

    val f1: AnyType => String = a => a.toString
    val g1: String => Unit = a => println(a)

    //    io.map(f1).map(g1) == io.map(a => g1(f1(a)))
    //    io.map(f1).map(g1) == io.map(f1 --> g1)

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
