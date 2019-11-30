package cpl

import org.scalatest.FunSuite

class DelayTest extends FunSuite {

  test("Delay delays computation") {
    var x = 0

    val d = Delay.fromFunction(() => x += 1)

    assert(x === 0)

    d.eval
    assert(x === 1)

    val d2 = d.flatMap { _ =>
      x += 1
      Delay(10)
    }

    assert(x === 1)
    assert(d2.eval === 10)
    assert(x === 2)
  }

  test("Delay supports for comprehensions") {
    val d1 = Delay(1)
    val d2 = Delay(2)

    val d3 = for {
      one <- d1
      two <- d2
    } yield one + two

    assert(d3.eval === 3)
  }

  test("Delay caches computation") {
    var x = 0
    val d = Delay.fromFunction(() => x += 1)

    d.eval
    d.eval
    assert(x === 1)
  }
}
