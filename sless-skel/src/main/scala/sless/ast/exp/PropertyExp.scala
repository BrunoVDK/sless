package sless.ast.exp

/**
  * A property expression represents a CSS property.
  */
case class PropertyExp(p: String) extends Expression {
  override def pretty(spaces: Int): String = p
}
