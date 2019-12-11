package sless.dsl

import org.scalatest.FunSuite

class ExtendTest extends FunSuite{
  import ExtendImplementation.dsl._

  test("Simple extension + exact match") {
    val ex = css(
      All.c("class-name1").extend(All ## "id-name1") {
        prop("width") := value("100%")
      },
      (All ## "id-name1") {
        prop("height") := value("95%")
      },
      (All ## "id-name1" ## "id-name2") {
        prop("height") := value("95%")
      }
    )

    assert(
      ExtendImplementation.dsl.compile(ex) ===
        """*.class-name1{width:100%;}*#id-name1,*.class-name1{height:95%;}*#id-name1#id-name2{height:95%;}""")
  }

  test("List extension") {
    val ex = css(
      N(All.c("class-name1"),All.c("class-name2")).extend(All ## "id-name1") {
        prop("width") := value("100%")
      },
      (All ## "id-name1") {
        prop("height") := value("95%")
      }
    )

    assert(
      ExtendImplementation.dsl.compile(ex) ===
        """*.class-name1,*.class-name2{width:100%;}*#id-name1,*.class-name1,*.class-name2{height:95%;}""")
  }

  test("Nested and partial extension"){
    val ex = css(
      (All ## "id-name1").extend(All).c("class-name1").extend(tipe("type1")) {
        prop("width") := value("100%")
      },
      All {
        prop("height") := value("95%")
      },
      tipe("type1") {
        prop("height") := value("95%")
      }
    )

    assert(
      ExtendImplementation.dsl.compile(ex) ===
        """*#id-name1.class-name1{width:100%;}*,*#id-name1{height:95%;}type1,*#id-name1.class-name1{height:95%;}""")
  }

  // Bit of commentary ; there are multiple interpretations here.
  //  - A variable holding a selector that extends may only extend once, or every time it is used
  //  - An extension occurring multiple times may be applied just once, or every time it is called
  // I implemented multiple ones but kept the simplest one to avoid problems with Parent references ; no duplicates

  test("Extension as a variable") {
    val sel = All.extend(All)
    val res = compile(css(
      sel (),
      sel (),
      sel (),
    ))
    assert(res === "*,*{}*,*{}*,*{}")
  }

  test("Duplicate extension") {
    val res = compile(css(
      All.extend(All) (),
      All.extend(All) (),
    ))
    assert(res === "*,*{}*,*{}")
  }

}
