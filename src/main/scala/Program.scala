import fplibrary.Description

import scala.annotation.tailrec

object Program {

  def creteDescription(args: Array[String]): Description[Unit] = () => {

    display(hyphens)

    display(question)

    val input: String = prompt()
    val integerAmount: Int = convertStringToInt(input)
    val positiveAmount: Int = ensureAmountPositive(integerAmount)
    val balance: Int = round(positiveAmount)
    val message: String = createMessage(balance)

    display(message)

    display(hyphens)
  }

  private val hyphens: String =
    "\u2500" * 50

  private val question: String =
    "Внеси бабки"

  private def display(input: Any): Unit =
    println(input)

  private def prompt(): String = "5"
  //scala.io.StdIn.readLine()

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
