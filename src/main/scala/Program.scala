import fplibrary._

object Program {

  //  lazy val creteDescription: Array[String] => Description[Unit] = args =>
  //    Description.create(
  //      display(
  //        hyphens(
  //          display(
  //            createMessage(
  //              round(
  //                ensureAmountPositive(
  //                  convertStringToInt(
  //                    prompt(
  //                      display(
  //                        question(
  //                          display(
  //                            hyphens(
  //                              ()
  //                            )
  //                          )
  //                        )
  //                      )
  //                    )
  //                  )
  //                )
  //              )
  //            )
  //          )
  //        )
  //      )
  //    )

  import IO._

  lazy val creteDescription: Array[String] => IO[Unit] = {

    // format: OFF
      ignoreArgs            -->
      hyphens               -->
      displayKleisli               >=>
      question              -->
      displayKleisli               >=>
      promptKleisli                >=>
      convertStringToInt    -->
      ensureAmountPositive  -->
      round                 -->
      createMessage         -->
      displayKleisli               >=>
      hyphens               -->
      displayKleisli

    // format: ON
  }

  private lazy val ignoreArgs: Array[String] => Unit = _ => ()

  private lazy val hyphens: Any => String = _ => "─" * 50

  private lazy val question: Any => String = _ => "Внеси бабки"

  private lazy val display: Any => Unit = input => println(input)

  private lazy val displayKleisli: Any => IO[Unit] = input => IO.create {
    println(input)
  }

  private lazy val promptKleisli: Any => IO[String] = _ => IO.create("5")
  //scala.io.StdIn.readLine()

  private lazy val prompt: Any => String = input => "5"
  //scala.io.StdIn.readLine()

  private lazy val convertStringToInt: String => Int = input => input.toInt

  private lazy val ensureAmountPositive: Int => Int = amount => if (amount < 1)
    1
  else
    amount

  private lazy val round: Int => Int = amount => {
    if (isDivisibleByHundred(amount))
      amount
    else
      round(amount + 1)
  }

  private lazy val isDivisibleByHundred: Int => Boolean = amount => amount % 100 == 0

  private lazy val createMessage: Int => String = balance => s"Ёба боба -  $balance"

}
