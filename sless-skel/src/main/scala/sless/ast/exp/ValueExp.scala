package sless.ast.exp

sealed trait ValueExp extends Expression

case class ValueStringExp(value: String) extends ValueExp {
  override def pretty(spaces: Int): String = value + ";"
}
