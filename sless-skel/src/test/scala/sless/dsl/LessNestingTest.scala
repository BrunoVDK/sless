package sless.dsl

import org.scalatest.FunSuite

class LessNestingTest extends FunSuite {
  import LessNestingImplementation.dsl._

  test("Nesting is possible") {
    val nestedExample = css(
      (All ## "header").nest(
        prop("color") := value("black"),
        All.c("logo")(
          prop("width") := value("300px")
        )
      )
    )

    assert(
      LessNestingImplementation.dsl.compile(nestedExample) ===
        """*#header{color:black;}*#header *.logo{width:300px;}""")
  }

  test("Parent support through 'Parent'") {
    val parentExample = css(
      (All ## "header").nest(
        (Parent |+ Parent)(
          prop("width") := value("300px")
        )
      )
    )

    assert(
      LessNestingImplementation.dsl.compile(parentExample) ===
        """*#header+*#header{width:300px;}""")
  }

  test("Parent scoping test for more difficult pattern") {
    val parentExample = css(
      (All ## "header").nest(
        (Parent |+ Parent).nest(
          (Parent |- Parent)(prop("width") := value("300px"))
        )
      )
    )

    assert(
      LessNestingImplementation.dsl.compile(parentExample) ===
        """*#header+*#header *#header+*#header{width:300px;}"""
    )
  }

  test("Parent & without parent") {
    val ex = css(
      (All ## "header").nest(
        (Parent |+ Parent).nest(
          (Parent |- Parent)(prop("width") := value("300px"))
        ),
        tipe("sub") (
          prop("width") := value("100%")
        )
      )
    )
    assert(compile(ex) === """*#header+*#header *#header+*#header{width:300px;}*#header sub{width:100%;}""")
  }

  test("Test algorithm again") {
    val ex = css(
      (All ## "header").nest(
        (Parent |+ Parent).nest(
          tipe("sub") (
            prop("width") := value("100%")
          )
        )
      )
    )
    assert(compile(ex) === """*#header+*#header sub{width:100%;}""")
  }

  test("From Discussion Board") {
    val ex = css((All ## "header").nest(
      prop("color") := value("black"),
      All.c("logo")(
        prop("width") := value("300px")
      ),
      prop("background-color") := value("green")
    ))
    assert(compile(ex) === """*#header{color:black;background-color:green;}*#header *.logo{width:300px;}""")
  }

  test("Nesting with variable referring to parent") {
    val sel = Parent ## "header"
    val ex = css(
      All.nest(sel ( prop("color") := value("blue"))),
      tipe("Test").nest(sel ( prop("color") := value("blue")))
    )
    assert(compile(ex) === """*#header{color:blue;}Test#header{color:blue;}""")
  }

}
