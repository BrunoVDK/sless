package sless.ast.exp

trait Expression {
  def compile(): String = pretty(0)
  def pretty(spaces: Int): String = ""
}
