package sless.dsl

import org.scalatest.FunSuite

class BetterValuesTest extends FunSuite {
  import BetterValuesImplementation.dsl._

  test("Simple checks") {
    assert(compile(css(All {prop("margin") := margin(1 em, 1 em)})) == "*{margin:1em 1em;}")
    assert(compile(css(All {prop("margin") := margin(auto, 1 percent)})) == "*{margin:auto 1%;}")
    assert(compile(css(All {prop("margin") := inherit})) == "*{margin:inherit;}")
  }

}

