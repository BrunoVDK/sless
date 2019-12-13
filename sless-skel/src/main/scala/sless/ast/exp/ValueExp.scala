package sless.ast.exp

/**
  * A value expression represents a CSS value. This can be a simple string or a more complex representation.
  */
sealed trait ValueExp extends Expression {
  override def pretty(spaces: Int): String = toString + ";"
  def aggregate(other: ValueExp): ValueExp = ValueStringExp(this.toString + " " + other.toString)
}

case object ValueInheritExp extends ValueExp { override def toString: String = "inherit" }
case object ValueInitialExp extends ValueExp { override def toString: String = "initial" }

case class ValueStringExp(value: String) extends ValueExp { override def toString: String = value }

case class ValueMarginExp(vs: Seq[ValueMarginWidthExp]) extends ValueExp {
  override def aggregate(other: ValueExp): ValueExp = other match {
    case m: ValueMarginExp => ValueMarginExp(vs ++ m.vs)
    case _ => super.aggregate(other)
  }
  override def toString: String = vs.take(4).map(_.toString).mkString(" ")
}

case class ValueMarginWidthExp(m: Measure) extends ValueExp {
  override def toString: String = m.toString
}

object ValueMarginWidthExp {
  implicit def MarginWidth(m: Measure): ValueMarginWidthExp = ValueMarginWidthExp(m)
}