package sless.dsl

import org.scalatest.FunSuite

class GeneralTest extends FunSuite {
  import GeneralImplementation.dsl._

  test("Extending with parent") {
    val sel = All.c("class1")
    val res = compile(css(
      All.nest (
        Parent.extend(sel) ()
      ),
      sel ()
    ))
    assert(res == "*{}*.class1,*{}")
  }

  test("Extending parent") {
    val sel = All.c("class1")
    val res = compile(css(
      sel.nest (
        All.extend(Parent) ()
      ),
      sel.extend(Parent) () // Should not extend anything
    ))
    assert(res == "*.class1 *{}*.class1{}")
    // This is okay (imo) because it makes no sense to extend a part of a combinator
    // I always make the assumption that list selectors can't be nested within other selectors, only inside each other
  }

  test("Extending with parent (more nesting)") {
    val sel = All.c("class1")
    val res = compile(css(
      All.nest (
        (Parent |+ Parent).extend(sel).nest (
          Parent ()
        )
      ),
      sel ()
    ))
    assert(res == "*+*{}*.class1,*+*{}")
  }

  test("Extending with parent in variable (more nesting)") {
    val sel = All.c("class1")
    val par = Parent |+ Parent
    val res = compile(css(
      sel (),
      All.nest (
        par.extend(sel).nest (
          par.extend(sel) ()
        )
      ),
      sel ()
    ))
    assert(res == "*.class1,*+*+*+*,*+*{}*+*+*+*{}*.class1,*+*+*+*,*+*{}")
  }

}