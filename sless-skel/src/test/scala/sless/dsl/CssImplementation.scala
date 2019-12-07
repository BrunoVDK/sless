package sless.dsl

import sless.ast.BaseLint

object CssImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with Compilable
  val dsl: DSL = new BaseLint()
}
