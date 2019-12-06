package sless.ast.exp

case class PropertyExp(p: String) extends Expression {
  override def pretty(spaces: Int): String = p
}
