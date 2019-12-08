package sless.ast.exp

trait Commentable extends Expression {
  val comment: String
  lazy val commentString: String = commentString(identity)
  def commentString(mod: String => String): String = if (comment == "") "" else mod("/* " + comment + " */")
}