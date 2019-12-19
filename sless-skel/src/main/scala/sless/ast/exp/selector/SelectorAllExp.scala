package sless.ast.exp.selector

case class SelectorAllExp()(implicit extensions: Seq[SelectorExp] = List()) extends SelectorExp(extensions) {
  override def pretty(spaces: Int): String = "*"
  def withExtensions(extensions: Seq[SelectorExp]): SelectorExp = copy()(extensions)
}
