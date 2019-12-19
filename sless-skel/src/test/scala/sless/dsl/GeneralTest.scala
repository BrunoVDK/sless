package sless.dsl

import org.scalatest.FunSuite

class GeneralTest extends FunSuite {
  import GeneralImplementation.dsl._

  test("Extending with parent") {
    val sel = All.c("class1")
    val res = compile(css(
      All.nest (
        (Parent |+ Parent).extend(sel) ()
      ),
      sel ()
    ))
    assert(res == "*+*{}*.class1,*+*{}")
  }

  test("Extending with parent (v2)") {
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
          Parent (),
        ),
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
    assert(res == "*.class1,*+*,*+*+*+*{}*+*+*+*{}*.class1,*+*,*+*+*+*{}")
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
    // Extensions are applied before merging
    // I also wrote an implementation where it is done *before* merging so it's really just a choice
    val ex1 = css(
      N(All.c("class-name1"), All.c("class-name2")).nest {
        N(Parent, All.c("class-name3").extend(N(All.c("class-name1"), All.c("class-name2")))) (
          prop("width") := value("100%"),
          prop("display") := value("block")
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
    assert(compile(ex) === """*.class-name3,*.class-name2,*.class-name3,*.class-name3{width:100%;display:block;}*.class-name1{background-color:blue;width:95%;display:block;}""")
  }

  test("Extending and merge (3 sheets)") {
    // Extensions will only be applied before merging
    val sel = All.c("some-class")
    val dummy = prop("width") := value("100%")
    val dummy2 = prop("height") := value("100%")
    val dummy3 = prop("display") := value("block")
    val ex1 = css(All.extend(sel) (dummy3), sel (dummy))
    val ex2 = css(sel (dummy))
    val ex3 = css(sel (dummy))
    val ex4 = css(sel (dummy2))
    val ex = mergeSheets(ex1, ex2,  ex3, ex4)
    assert(compile(ex) === """*{width:100%;display:block;}*.some-class{height:100%;width:100%;}""")
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
        N(All, Parent |+ Parent) (
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
    assert(compile(removeEmptyRules(css ( All.nest (prop("width") := value("100%"), All ())))._2) === """*{width:100%;}""")
  }

  test("Aggregate better values margins") {

    val res = compile(aggregateMargins(css(All (prop("margin-top") :=  margin(1 em),
      prop("margin-left") :=  margin(2 em),
      prop("margin-right") :=  margin(3 em),
      prop("margin-bottom") :=  margin(4 em))))._2)
    assert(res === """*{margin:1em 3em 4em 2em;}""")

    val res2 = compile(aggregateMargins(css(All (prop("margin-top") :=  margin(1 em),
      prop("margin-left") :=  value("2em"),
      prop("margin-right") :=  value("3em"),
      prop("margin-bottom") :=  margin(4 em))))._2)
    assert(res2 === """*{margin:1em 3em 4em 2em;}""")

  }

  test("Extending throughout a nesting") {
    val ex = css(All.extend(All |+ All).nest (
      Parent.nest (
        Parent.nest (
          (Parent |+ Parent) (
            prop("width") := value("100%")
          )
        )
      )
    ))
    val ex2 = css(All.extend(All |+ All).nest (
      Parent.nest (
        (Parent |+ Parent).nest (
          N(Parent,Parent) (
            prop("width") := value("100%")
          )
        )
      )
    ))
    assert(compile(ex) === """*+*,*{width:100%;}""")
    assert(compile(ex2) === """*+*,*,*+*,*{width:100%;}""")
  }

}