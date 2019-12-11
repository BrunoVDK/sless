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

  test("Nesting and merge") {

    val ex1 = css(
      N(All.c("class-name1"), All.c("class-name2")).nest {
        N(Parent, All.c("class-name3")) (
          prop("width") := value("100%")
        )
      }
    )

    val ex2 = css(
      All.c("class-name1") (
        prop("background-color") := value("blue"),
        prop("width") := value("95%")
      )
    )

    val ex = mergeSheets(ex1,ex2)
    assert(compile(ex) === """*.class-name2,*.class-name3{width:100%;}*.class-name1{background-color:blue;width:95%;}""")

  }

  test("Extending and merge") {

    val ex1 = css(
      N(All.c("class-name1"), All.c("class-name2")).nest {
        N(Parent, All.c("class-name3").extend(Parent)) (
          prop("width") := value("100%")
        )
      }
    )

    val ex2 = css(
      All.c("class-name1") (
        prop("background-color") := value("blue"),
        prop("width") := value("95%")
      )
    )

    val ex = mergeSheets(ex1,ex2)
    assert(compile(ex) === """*.class-name2,*.class-name3,*.class-name3{width:100%;}*.class-name1{background-color:blue;width:95%;}""")

  }

  test("Comments within nested rules") {

    val res = compile(css(
      All.nest (
        (Parent |+ Parent) (

        ).comment("first comment"),
        prop("width") := value("95%")
      ).comment("other cool comment")
    ))

    assert(res === """/* other cool comment */*{width:95%;}/* first comment */*+*{}""")

  }

  test("Linting on nesting") {

    val ex = css(
      (All ## "container").nest (
        (Parent |+ Parent) (
          prop("margin-right")  := value("50px"),
          prop("margin-bottom")  := value("75px"),
          prop("width") := value("100%"),
          prop("margin-top")  := value("25px"),
          prop("margin-left")  := value("100px")
        )
      )
    )

    val (linted,lintedEx) = aggregateMargins(ex)
    assert(linted === true)
    val res = compile(lintedEx)
    assert(res === "*#container+*#container{margin:25px 50px 75px 100px;width:100%;}")

  }

}