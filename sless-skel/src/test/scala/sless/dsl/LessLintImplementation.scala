package sless.dsl

import sless.ast.BaseLint

object LessLintImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with LintDSL with Compilable
  val dsl: DSL = new BaseLint()
}
