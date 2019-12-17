package sless.ast.exp.selector

case class SelectorAllExp()(extensions: Seq[SelectorExp] = List()) extends SelectorExp(extensions) {
  override def pretty(spaces: Int): String = "*"
  override def addExtension(toExtend: SelectorExp): SelectorExp = SelectorAllExp()(toExtend +: extensions)
}
