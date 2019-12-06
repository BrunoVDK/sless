package sless.ast.exp.selector

case class SelectorTypeExp(s: String) extends SelectorExp {
  override def pretty(spaces: Int): String = s
}
