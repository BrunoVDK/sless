package sless.ast.exp.selector

case class SelectorAll() extends SelectorExp {
  override def pretty(spaces: Int): String = "*"
}
