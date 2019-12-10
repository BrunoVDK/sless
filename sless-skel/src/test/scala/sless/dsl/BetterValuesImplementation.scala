package sless.dsl

import sless.ast.ExtendedBase

object BetterValuesImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with BetterValuesDSL with Compilable
  val dsl: DSL = ExtendedBase
}
