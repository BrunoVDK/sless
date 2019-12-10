package sless.ast.exp.measure

case class Length[T: Numeric](x: T, u: String) extends Measure {
  override def toString: String = x.toString + u
}
