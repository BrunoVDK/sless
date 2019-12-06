package sless.ast.exp.selector.modifier

import sless.ast.exp.selector.SelectorExp
import sless.ast.exp.value.ValueExp

case class SelectorAttributeExp(sel: SelectorExp, attr: String, v: ValueExp) extends SelectorModifierExp(sel) {
  override def compile(): String = super.compile() + "[" + attr + "=" + v.compile() + "]"
  override def pretty(spaces: Int): String = super.pretty(spaces) + "[" + attr + "=" + v.pretty(spaces) + "]"
}
