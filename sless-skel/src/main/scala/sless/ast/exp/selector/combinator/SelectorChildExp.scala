package sless.ast.exp.selector.combinator

import sless.ast.exp.selector.SelectorExp

case class SelectorChildExp(s1: SelectorExp, s2: SelectorExp) extends SelectorCombinatorExp(s1, s2) {
  override protected def separator: String = ">"
}
