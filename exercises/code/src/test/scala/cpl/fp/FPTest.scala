package cpl.fp

import org.scalatest.FunSuite

class FPTest extends FunSuite {

  test("Exp trees can be evaluated") {
    val one = Literal(1)
    assert(Exp.eval(one) === 1)
    assert(Exp.eval(Addition(one, one)) === 2)
    assert(Exp.eval(Addition(one, Addition(one, one))) === 3)
    assert(Exp.eval(Addition(Addition(one, one), one)) === 3)
   }

  // 3b

  test("Exp trees can be printed") {
    val one = Literal(1)
    assert(Exp.prettyPrint(one) === "1")
    assert(Exp.prettyPrint(Addition(Addition(one, one), one)) === "1 + 1 + 1")
  }

}
