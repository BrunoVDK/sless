package sless.ast.exp.value

import sless.ast.exp.Expression

case class ValueExp(value: String) extends Expression {
  override def pretty(spaces: Int): String = value + ";"
}
