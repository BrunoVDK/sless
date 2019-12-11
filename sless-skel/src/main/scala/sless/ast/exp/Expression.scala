package sless.ast.exp

/**
  * An expression represents a node in the abstract syntax tree.
  *   Any such node can be compiled.
  */
trait Expression {
  def compile(): String = pretty(0)
  def pretty(spaces: Int): String = ""
}
