package sless.ast.exp.selector.combinator

import sless.ast.exp.selector.SelectorExp

abstract class SelectorCombinatorExp(s1: SelectorExp, s2: SelectorExp) extends SelectorExp {
  protected def separator: String
  override def compile(): String = s1.compile() + separator + s2.compile()
  override def pretty(spaces: Int): String = s1.pretty(spaces) + " " + separator + " " + s2.pretty(spaces)
}
