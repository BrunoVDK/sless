package sless.ast.exp.selector

case class SelectorTypeExp(tipe: String)(implicit extensions: Seq[SelectorExp] = List()) extends SelectorExp(extensions) {
  override def pretty(spaces: Int): String = tipe
  override def withExtensions(extensions: Seq[SelectorExp]): SelectorExp = copy()(extensions)
}
