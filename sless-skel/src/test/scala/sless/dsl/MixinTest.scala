package sless.dsl

import org.scalatest.FunSuite

class MixinTest extends FunSuite {
  import MixinImplementation.dsl._

  test("Simple mixin") {
    val asgn = prop("property-1") := value("value-1")
    val mixin = List(asgn,asgn)
    val propertiesPre = List(prop("property-pre") := value("value-pre"))
    val propertiesPost = List(prop("property-post") := value("value-post"))
    val rule = All.mixin(propertiesPre, mixin, propertiesPost)
    val ex = css(rule)

    assert(
      MixinImplementation.dsl.compile(ex) ===
        """*{property-pre:value-pre;property-1:value-1;property-1:value-1;property-post:value-post;}""")
  }

  test("Parametric mixin") {
    val asgnPar : String => Declaration = x  => prop(x) := value("value-1")
    val mixin : String => Seq[Declaration] = x  =>  List(asgnPar(x))
    val propertiesPre = List(prop("property-pre") := value("value-pre"))
    val propertiesPost = List(prop("property-post") := value("value-post"))
    val rule = All.mixin(propertiesPre,mixin("Hey-Im-a-mixin"), propertiesPost)
    val ex = css(rule)

    assert(
      MixinImplementation.dsl.compile(ex) ===
        """*{property-pre:value-pre;Hey-Im-a-mixin:value-1;property-post:value-post;}""")
  }

  test("Mixing map") {
    val sizes: Map[String, Int => Value] = Map(
      "mobile" -> (x => value(x.toString + "px")),
      "desktop" -> (x => value((2*x).toString + "px"))
    )
    val ex1 = css(All (prop("width") := sizes("mobile")(100)))
    val ex2 = css(All (prop("width") := sizes("desktop")(100)))
    assert(compile(ex1) === """*{width:100px;}""")
    assert(compile(ex2) === """*{width:200px;}""")
  }

  test("Namespaces (my library example from lesscss.org)") {
    val library = Map("my-mixin" -> (prop("color") := value("black")))
    val ex = css(tipe("").c("class") (library("my-mixin")))
    assert(compile(ex) === """.class{color:black;}""")
  }

}
