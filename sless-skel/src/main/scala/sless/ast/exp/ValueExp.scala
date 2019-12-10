package sless.ast.exp

import sless.ast.exp.measure.Measure

sealed trait ValueExp extends Expression {
  val value: String = ""
  override def pretty(spaces: Int): String = value + ";"
  def aggregate(other: ValueExp): ValueExp = this
}

case object ValueInheritExp extends ValueExp { override val value = "inherit" }
case object ValueInitialExp extends ValueExp { override val value = "initial" }

case class ValueStringExp(override val value: String) extends ValueExp {
  override def pretty(spaces: Int): String = value + ";"
  override def aggregate(other: ValueExp): ValueExp = other match {
    case v: ValueStringExp => ValueStringExp(value + " " + v.value)
    case _ => super.aggregate(other)
  }
}

case class ValueMarginExp(vs: Seq[ValueMarginWidthExp]) extends ValueExp {
  override def pretty(spaces: Int): String = vs.take(4).map(_.pretty(spaces)).mkString(" ")
}

case class ValueMarginWidthExp(m: Measure) extends ValueExp {
  override def pretty(spaces: Int): String = m.toString
}