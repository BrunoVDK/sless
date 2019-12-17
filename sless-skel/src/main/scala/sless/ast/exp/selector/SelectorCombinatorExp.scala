package sless.ast.exp.selector

case class SelectorCombinatorExp(s1: SelectorExp, s2: SelectorExp, sep: String)(extensions: Seq[SelectorExp] = List())
  extends SelectorExp(extensions) {
  override def compile(): String = s1.compile() + sep + s2.compile()
  override def pretty(spaces: Int): String = s1.pretty(spaces) + (if (sep == " ") sep else " " + sep + " ") + s2.pretty(spaces)
  override def grounded: Boolean = s1.grounded & s2.grounded
  override def replace(el: SelectorExp, rep: SelectorExp): SelectorExp =
    if (this == el) rep else SelectorCombinatorExp(s1.replace(el,rep), s2.replace(el,rep), sep)()
  override def addExtension(toExtend: SelectorExp): SelectorExp = SelectorCombinatorExp(s1, s2, sep)(toExtend +: extensions)
}
