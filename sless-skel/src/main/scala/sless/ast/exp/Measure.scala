package sless.ast.exp

sealed trait Measure

case object Auto extends Measure { override def toString: String = "auto" }
case class Length[T: Numeric](x: T, u: String) extends Measure { override def toString: String = x.toString + u }
