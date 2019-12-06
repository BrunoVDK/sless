package sless.ast.exp.selector.combinator

import sless.ast.exp.selector.SelectorExp

case class SelectorDescendantExp(s1: SelectorExp, s2: SelectorExp) extends SelectorCombinatorExp(s1, s2) {
  override def separator = " "
  override def pretty(spaces: Int): String = s1.pretty(spaces) + separator + s2.pretty(spaces)
}
