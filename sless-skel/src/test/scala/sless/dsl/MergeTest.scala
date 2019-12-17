package sless.dsl

import org.scalatest.FunSuite

class MergeTest extends FunSuite {
  import MergeImplementation.dsl._

  test("Vanilla merge") {
    val backgroundColor = prop("background-color")
    val container = tipe("div") ## "container"

    val ex1 = css(
      N(All.c("class-name1"), All.c("class-name2")) {
        prop("width") := value("100%")
      }
    )

    val ex2 = css(
      container {
        backgroundColor := value("blue")
      }
    )

    val ex = mergeSheets(ex1,ex2)

    assert(
      MergeImplementation.dsl.compile(ex) ===
        """*.class-name1,*.class-name2{width:100%;}div#container{background-color:blue;}""")
  }

  test("Merging retains rightmost overlapping property") {
    val backgroundColor = prop("background-color")
    val ex1 = css(
      N(All.c("class-name1"), All.c("class-name2")) {
        prop("width") := value("100%")
      }
    )
    val ex2 = css(
      All.c("class-name1") (
        backgroundColor := value("blue"),
        prop("width") := value("95%")
      )
    )
    val ex = mergeSheets(ex1,ex2)
    assert(MergeImplementation.dsl.compile(ex) === """*.class-name2{width:100%;}*.class-name1{background-color:blue;width:95%;}""")
  }

  test("Quadruple merge") {

    val ex1 = css(All (prop("width") := value("100%")))
    val ex2 = css(All (prop("width") := value("90%")))
    val ex3 = css(All (prop("width") := value("80%")))
    val ex4 = css(All (prop("width") := value("70%")))
    assert(compile(mergeSheets(ex1, ex2, ex3, ex4)) === "*{width:70%;}")

  }

}
