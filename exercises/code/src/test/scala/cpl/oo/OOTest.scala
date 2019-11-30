package cpl.oo

import org.scalatest.FunSuite

class OOTest extends FunSuite {

  test("Literals evaluate to themselves") {
    assert(new Literal(10).eval === 10)
    assert(new Literal(1).eval === 1)
  }

  test("Addition evaluates to addition...") {
    val one = new Literal(1)
    assert(new Addition(one, one).eval === 2)
    assert(new Addition(one, new Addition(one, one)).eval === 3)
    assert(new Addition(new Addition(one, one), one).eval === 3)
  }

  test("Negation evaluates to negation") {
    val one = new Negation(new Literal(1))
    assert(one.eval === -1)
    assert(new Addition(one, new Addition(one, one)).eval === -3)
  }

}
