package sless.ast.exp.measure

case class Length[T: Numeric](x: T, u: Unit) extends Measure {
  override def toString: String = x.toString + u.toString
}
