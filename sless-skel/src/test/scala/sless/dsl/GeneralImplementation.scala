package sless.dsl

import sless.ast.ExtendedBase

object GeneralImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with ExtendDSL with Compilable with CommentDSL with LintDSL with ExtendDSL with MergeDSL with NestedSelectorDSL with BetterValuesDSL
  val dsl: DSL = ExtendedBase
}
