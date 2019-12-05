package sless.dsl

import sless.ast.Base

object CssImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with Compilable
  val dsl: DSL = new Base()
}
