package sless.ast.exp

abstract class Expression {
  def compile(): String = pretty(0)
  def pretty(spaces: Int): String
}
