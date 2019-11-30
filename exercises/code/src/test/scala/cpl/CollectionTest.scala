package cpl

import org.scalatest.FunSuite

class CollectionTest extends FunSuite {

  /**
    * In Scala, a 'String' is automatically wrapped into a collection type.
    * It makes String manipulation a breeze since typical collection APIs can be
    * used.
    *
    * Look at the Scaladoc for WrappedString and search for drop, take,
    * slice, etc. to solve this problem.
    */
  test("Wrap center letter into brackets (favour left in even case)") {
    def wrapCenter(in: String): String = in.take((in.length-1)/2) + "[" + in.slice(in.length/2,in.length/2+1) + "]" + in.drop((in.length-1)/2+1)
    assert(wrapCenter("hello") == "he[l]lo")
    assert(wrapCenter("passed") == "pa[s]sed")
  }

  /**
    * Write a program that prints the numbers from 1 to 100 (inclusive).
    * But for multiples of three print “Fizz” instead of the number and
    * for the multiples of five print “Buzz”. For numbers which are
    * multiples of both three and five print “FizzBuzz”.
    *
    * The result should be a string, use Range and its APIs, (look at mkString)
    */
  test("Use Range to write FizzBuzz") {
    // Range(1, 100), 1 to 100, 1 until 100 -> figure out the difference!
    //  Only 'until' doesn't includes 100
    // val result : String = Range(1,100).foldRight("")((i,s) => (if (i % 3 == 0) "Fizz" else if (i % 5 == 0) "Buzz" else i.toString) + " " + s)
    val result : String = Range(1,100).map(i => (if (i % 3 == 0) "Fizz" else if (i % 5 == 0) "Buzz" else i.toString)).mkString(" ")
    assert(result.startsWith("1 2 Fizz 4 Buzz"))
  }

}
