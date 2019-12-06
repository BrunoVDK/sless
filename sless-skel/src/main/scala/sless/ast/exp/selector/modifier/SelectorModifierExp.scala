package sless.ast.exp.selector.modifier

import sless.ast.exp.selector.SelectorExp

abstract class SelectorModifierExp(s: SelectorExp) extends SelectorExp {
  override def compile(): String = s.compile() + suffix()
  override def pretty(spaces: Int): String = s.pretty(spaces) + suffix()
  protected def suffix(): String = ""
}
