package sless.ast.exp

import sless.ast.exp.value.ValueExp

case class DeclarationExp(property: PropertyExp, value: ValueExp) extends Expression {
  override def compile(): String = property.compile() + ":" + value.compile()
  override def pretty(spaces: Int): String = property.pretty(spaces) + ": " + value.pretty(spaces)
}
