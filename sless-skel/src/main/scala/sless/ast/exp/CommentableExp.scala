package sless.ast.exp

/**
  * A commentable expression is one which can display comments.
  */
trait CommentableExp extends Expression {
  val comment: String
  def comment(mod: String => String = identity): String = if (comment == "") "" else mod("/* " + comment + " */")
}