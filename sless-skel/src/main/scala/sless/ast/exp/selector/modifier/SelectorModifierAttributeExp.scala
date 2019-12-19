package sless.ast.exp.selector.modifier

import sless.ast.exp.selector.SelectorExp
import sless.ast.exp.value.ValueExp

case class SelectorModifierAttributeExp(s: SelectorExp, a: String, v: ValueExp)(implicit extensions: Seq[SelectorExp] = List())
  extends SelectorModifierExp(s)(extensions) {
  override def compile(): String = s.compile() + "[" + a + "=" + v.compile() + "]"
  override def pretty(spaces: Int): String = s.pretty(spaces) + "[" + a + "=" + v.pretty(spaces) + "]"
  override def replace(el: SelectorExp, rep: SelectorExp): SelectorExp =
    if (this == el) super.replace(el,rep) else copy(s.replace(el,rep), a, v)(extensions)
  override def withExtensions(extensions: Seq[SelectorExp]): SelectorExp = copy()(extensions)
}