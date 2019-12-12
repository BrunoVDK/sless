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

  test("Merging sheets with nesting, extensions and comments") {

    val ex1 = css(
      (All ## "container").nest (
        (Parent |+ Parent) (
          (prop("width") := value("95%")).comment("comment that is about to leave"),
          (prop("height") := value("95%")).comment("comment that stays"),
        )
      )
    )

    val ex2 = css(
      (All ## "container").nest (
        N(All, (Parent |+ Parent)) (
          (prop("width") := value("100%")).comment("this comment is here to stay"),
        ).comment("hello"),
        prop("display") := value("block")
      ).comment("hi")
    )

    assert(compile(ex1) === """*#container+*#container{width:95%;/* comment that is about to leave */height:95%;/* comment that stays */}""")
    assert(compile(ex2) === """/* hi */*#container{display:block;}/* hello */*,*#container+*#container{width:100%;/* this comment is here to stay */}""")
    assert(compile(mergeSheets(ex1, ex2)) === """/* hi */*#container{display:block;}*#container+*#container{width:100%;/* this comment is here to stay */height:95%;/* comment that stays */}/* hello */*{width:100%;/* this comment is here to stay */}""")

  }

  test("Empty nested rule linting") {

    print(compile(removeEmptyRules(css ( All.nest (prop("width") := value("100%"), All ())))._2) === """""")

  }

}