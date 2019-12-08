package sless.dsl

import sless.ast.ExtendedBase

object ExtendImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with ExtendDSL with Compilable
  val dsl: DSL = ExtendedBase
}
