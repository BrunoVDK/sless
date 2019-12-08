package sless.dsl

import sless.ast.ExtendedBase

object CssImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with Compilable
  val dsl: DSL = ExtendedBase
}
