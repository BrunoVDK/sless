package sless.ast.exp.selector.modifier

import sless.ast.exp.selector.SelectorExp

case class SelectorPseudoElementExp(sel: SelectorExp, c: String) extends SelectorModifierExp(sel) {
  override protected def suffix(): String = "::" + c
}