import fplibrary._

import scala.annotation.tailrec

object Program {

  def creteDescription(args: Array[String]): Description[Unit] =
    Description.create(
      display(
        hyphens(
          display(
            createMessage(
              round(
                ensureAmountPositive(
                  convertStringToInt(
                    prompt(
                      display(
                        question(
                          display(
                            hyphens(
                              ()
                            )
                          )
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    )

  private def hyphens(input: Any): String =
    "─" * 50

  private def question(input: Any): String =
    "Внеси бабки"

  private def display(input: Any): Unit =
    println(input)

  private def prompt(input: Any): String = "5"
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
    s"Ёба боба -  $balance"

}
