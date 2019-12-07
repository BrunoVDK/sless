package sless.dsl

import sless.ast.Base

object ExtendImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with ExtendDSL with Compilable
  val dsl: DSL = Base
}
