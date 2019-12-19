package sless.ast.exp.selector.modifier

import sless.ast.exp.selector.SelectorExp

case class SelectorModifierClassExp(s: SelectorExp, c: String)(implicit extensions: Seq[SelectorExp] = List())
  extends SelectorModifierExp(s, "." + c)(extensions) {
  override def replace(el: SelectorExp, rep: SelectorExp): SelectorExp =
    if (this == el) super.replace(el,rep) else copy(s.replace(el,rep), c)(extensions)
  override def withExtensions(extensions: Seq[SelectorExp]): SelectorExp = copy()(extensions)
}
