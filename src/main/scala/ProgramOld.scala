import fplibrary._

import scala.annotation.tailrec

object ProgramOld {

  //  def creteDescription(args: Array[String]): IO[Unit] =
  //    display(hyphens).flatMap { _ =>
  //      display(question).flatMap { _ =>
  //        prompt.flatMap { input =>
  //          display(fromInputToMessage(input)).flatMap { _ =>
  //            display(hyphens).map { _ =>
  //              ()
  //            }
  //          }
  //        }
  //      }
  //    }

  def creteDescription(args: Array[String]): IO[Unit] = for {
    _ <- display(hyphens)
    _ <- display(question)
    input <- prompt

    _ <- display(fromInputToMessage(input))
    _ <- display(hyphens)
  } yield ()

  private def fromInputToMessage(input: String): String = {

    val maybeInteger: Maybe[Int] = convertStringToInt(input)

    val message: String = maybeInteger match {
      case Maybe.Just(integerAmount) =>
        val positiveAmount = ensureAmountPositive(integerAmount)
        val balance = round(positiveAmount)
        val message = createMessage(balance)

        message
      case Maybe.Nothing =>
        val message = "}{y"

        message
    }

    message
  }

  //  def associativityLaw = {
  //    type AnythingThatHasMonad[A] = IO[A]
  //    type AnyType = Int
  //    val anyValue: AnyType = 5
  //
  //    val io: AnythingThatHasMonad[AnyType] = IO.create(anyValue)
  //    val f: AnyType => AnythingThatHasMonad[String] = a => IO.create(a.toString)
  //    val g: String => AnythingThatHasMonad[Unit] = a => IO.create(println(a))
  //
  //    io.flatMap(f).flatMap(g) == io.flatMap(a => f(a).flatMap(g))
  //    io.flatMap(f).flatMap(g) == io.flatMap(f >=> g)
  //
  //    val f1: AnyType => String = a => a.toString
  //    val g1: String => Unit = a => println(a)
  //
  //    io.map(f1).map(g1) == io.map(a => g1(f1(a)))
  //    io.map(f1).map(g1) == io.map(f1 --> g1)
  //
  //  }
  //
  //  def identityLaw = {
  //    type AnythingThatHasMonad[A] = IO[A]
  //    type AnyType = Int
  //    val anyValue: AnyType = 5
  //
  //    val M = Monad[IO]
  //    val f: AnyType => AnythingThatHasMonad[String] = a => M.pure(a.toString)
  //
  //    val io = M.pure(anyValue)
  //    M.flatMap(io)(f) == io
  //    io.flatMap(f) == io
  //    f(anyValue).flatMap(a => IO.create(a)) == IO.create(anyValue)
  //  }

  private val hyphens: String =
    "\u2500" * 50

  private val question: String =
    "Some text"

  private def display(input: Any): IO[Unit] = {
    IO.create {
      println(input)
    }
  }

  private def prompt: IO[String] = IO.create("5")
  //scala.io.StdIn.readLine()

  private def convertStringToInt(input: String): Maybe[Int] = {
    try Maybe.Just(input.toInt)
    catch {
      case _: NumberFormatException => Maybe.Nothing
    }
  }

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
