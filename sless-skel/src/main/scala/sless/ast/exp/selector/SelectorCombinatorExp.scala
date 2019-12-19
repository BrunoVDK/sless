package sless.ast.exp.selector

case class SelectorCombinatorExp(s1: SelectorExp, s2: SelectorExp, sep: String)(implicit extensions: Seq[SelectorExp] = List())
  extends SelectorExp(extensions) {

  override def compile(): String = s1.compile() + sep + s2.compile()
  override def pretty(spaces: Int): String = s1.pretty(spaces) + (if (sep == " ") sep else " " + sep + " ") + s2.pretty(spaces)

  override def grounded: Boolean = s1.grounded & s2.grounded
  override def replace(el: SelectorExp, rep: SelectorExp): SelectorExp =
    if (this == el) super.replace(el,rep) else copy(s1.replace(el,rep), s2.replace(el,rep))(extensions)

  override def withExtensions(extensions: Seq[SelectorExp]): SelectorExp = copy()(extensions)
  override def addExtension(toExtend: SelectorExp): SelectorExp = copy()(toExtend +: extensions)
  override def extensionPairs: Seq[(SelectorExp, SelectorExp)] = extensions.map((this,_)) ++ s1.extensionPairs ++ s2.extensionPairs

}
