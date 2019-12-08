package sless.ast.exp

trait Commentable extends Expression {
  val comment: String
  def comment(mod: String => String = identity): String = if (comment == "") "" else mod("/* " + comment + " */")
}