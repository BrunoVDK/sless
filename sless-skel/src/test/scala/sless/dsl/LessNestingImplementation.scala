package sless.dsl

import sless.ast.ExtendedBase

object LessNestingImplementation {
  type DSL = PropertyDSL with NestedSelectorDSL with ValueDSL with Compilable
  val dsl: DSL = ExtendedBase
}
