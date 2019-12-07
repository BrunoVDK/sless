package sless.ast.exp

sealed trait ValueExp extends Expression {
  def merge(other: ValueExp): ValueExp = this
}

case class ValueStringExp(value: String) extends ValueExp {
  override def pretty(spaces: Int): String = value + ";"
  override def merge(other: ValueExp): ValueExp = other match {
    case v: ValueStringExp => ValueStringExp(value + " " + v.value)
    case _ => super.merge(other)
  }
}
