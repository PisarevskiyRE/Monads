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

  def createDescription(args: Array[String]): IO[Unit] =
    loop(triesLeftAfterThisIteration = 3)

  def loop(triesLeftAfterThisIteration: Int): IO[Unit] = for {

    _ <- display(hyphens)
    _ <- display(question)
    input <- prompt
    result <- fromInputToTuple(input)
    (message, wasInputValid) = result
    _ <- display(message)
    _ <- display(hyphens)
    _ <- decideWhatToDoNext(wasInputValid, triesLeftAfterThisIteration)
  } yield ()

  private def decideWhatToDoNext(wasInputValid: Boolean, triesLeftAfterThisIteration: Int): IO[Unit] = {
    if (wasInputValid)
      IO.create(())
    else if (triesLeftAfterThisIteration == 0)
      ranOutOfTries
    else
     tryAgain(triesLeftAfterThisIteration)
  }

  private def tryAgain(triesLeftAfterThisIteration: Int): IO[Unit] =  state
    .flatMap(_ => state)
    .runA(triesLeftAfterThisIteration)

  private lazy val state = State(stateful)

  private lazy val ranOutOfTries: IO[Unit] = for {
    _ <- display("Й")
    _ <- display(hyphens)
  } yield ()

  private lazy val stateful: Int => (IO[Unit], Int) = tries => {
    val nextState = tries - 1
    val nextValue: IO[Unit] = loop(tries)
    nextValue -> nextState
  }

  private def fromInputToTuple(input: String): IO[(String, Boolean)] = IO.create {
    val meybeInteger: Maybe[Int] = convertStringToInt(input)

    val message = meybeInteger.mapOrElse("}{y")(fromIntegerInputToMessage)

    val wasInputValid = meybeInteger.isJust

    message -> wasInputValid

  }

  private def fromInputToMessage(input: String): String = {
    convertStringToInt(input).mapOrElse("}{y")(fromIntegerInputToMessage)
  }

  private def fromIntegerInputToMessage(integerAmount: Int): String = {
    createMessage(round(ensureAmountPositive(integerAmount)))
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
