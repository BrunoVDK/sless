package sless.ast.exp

/**
  * A value expression represents a CSS value. This can be a simple string or a more complex representation.
  */
sealed trait ValueExp extends Expression {
  def aggregate(other: ValueExp): ValueExp = this
}

case object ValueInheritExp extends ValueExp { override def pretty(spaces: Int): String = "inherit;" }
case object ValueInitialExp extends ValueExp { override def pretty(spaces: Int): String = "initial;" }

case class ValueStringExp(value: String) extends ValueExp {
  override def pretty(spaces: Int): String = value + ";"
  override def aggregate(other: ValueExp): ValueExp = other match {
    case v: ValueStringExp => ValueStringExp(value + " " + v.value)
    case _ => super.aggregate(other)
  }
}

case class ValueMarginExp(vs: Seq[ValueMarginWidthExp]) extends ValueExp {
  override def pretty(spaces: Int): String = vs.take(4).map(_.pretty(spaces)).mkString(" ") + ";"
}

case class ValueMarginWidthExp(m: Measure) extends ValueExp {
  override def pretty(spaces: Int): String = m.toString
}