package sless.ast.exp.selector

case class SelectorTypeExp(tipe: String)(implicit extensions: Seq[SelectorExp] = List()) extends SelectorExp(extensions) {
  override def pretty(spaces: Int): String = tipe
  override def addExtension(toExtend: SelectorExp): SelectorExp = SelectorTypeExp(tipe)(toExtend +: extensions)
}
