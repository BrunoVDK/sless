package sless.ast.exp

trait Expression {
  def compile(): String = ""
  def pretty(spaces: Int): String = ""
}
