package sless.ast.exp

/**
  * A measure represents a CSS length (with unit), or possibly the keyword 'auto'.
  */
abstract sealed class Measure

case object Auto extends Measure { override def toString: String = "auto" }

case class Length[T](x: T, u: Unit)(implicit num: Numeric[T]) extends Measure {
  import num._
  override def toString: String = x.toString + u.toString
  def + (other: Length[T]) = Length(x + other.x, u)
  def * (other: Length[T]) = Length(x * other.x, u)
}