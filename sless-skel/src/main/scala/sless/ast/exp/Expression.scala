package sless.ast.exp

abstract class Expression {
  def compile(): String = ""
  def pretty(spaces: Int): String = ""
}
