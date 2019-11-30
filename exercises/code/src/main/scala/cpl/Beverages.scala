package cpl

import cpl.BeveragesGeneric.{Beer, Student}

object BeveragesGeneric {
  class Beverage
  abstract class Human[T <: Beverage] {
    def guzzle(drink: T) : Unit
  }

  class Beer extends Beverage
  class Student extends Human[Beer] {
    override def guzzle(drink: Beer) = {}
  }

  class Wine extends Beverage
  val spencer: Student = new Student
  spencer guzzle(new Beer)
  // Uncomment the below line to see that it does indeed type error, due to a type mismatch
  //spencer guzzle (new Wine)
}

/**
  * Implement your version of the beverages class hierarchy, using an abstract type member this time around, below.
  * When you think you are done, verify that the above line "spencer guzzle(new Beer)"
  * does indeed still result in the same type error
  */
object BeveragesAbstract {
  class Beverage
  abstract class Human {
    type T <: Beverage
    def guzzle(drink: T): Unit = {}
  }

  class Beer extends Beverage
  class Student extends Human {
    type T = Beer
    override def guzzle(drink: Beer): Unit = {}
  }

  class Wine extends Beverage
  val spencer: Student = new Student
  spencer guzzle(new Beer)
  // Uncomment the below line to see that it does indeed type error, due to a type mismatch
  //spencer guzzle (new Wine)

}