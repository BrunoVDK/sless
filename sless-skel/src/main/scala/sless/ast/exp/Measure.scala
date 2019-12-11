package sless.ast.exp

/**
  * A measure represents a CSS length (with unit), or possibly the keyword 'auto'.
  */
abstract sealed class Measure

case object Auto extends Measure { override def toString: String = "auto" }

case class Length[T: Numeric](x: T, u: Unit) extends Measure { override def toString: String = x.toString + u.toString }