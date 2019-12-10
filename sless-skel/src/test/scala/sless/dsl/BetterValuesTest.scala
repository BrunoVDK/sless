package sless.dsl

import org.scalatest.FunSuite

class BetterValuesTest extends FunSuite {
  import BetterValuesImplementation.dsl._

  test("Simple checks") {
    val declaration = prop("margin") := Margin(1 em, 1 em)
    assert(compile(css(All {declaration})) == "*{margin:1em 1em}")
    val declaration2 = prop("margin") := Margin(auto, 1 percent)
    assert(compile(css(All {declaration2})) == "*{margin:auto 1%}")
  }

}
