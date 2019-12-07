package sless.dsl

import sless.ast.Base

object LessNestingImplementation {
  type DSL = PropertyDSL with NestedSelectorDSL with ValueDSL with Compilable
  val dsl: DSL = Base
}
