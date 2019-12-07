package sless.ast.exp

sealed trait Commentable extends Expression {
  val comment: String
}

trait CommentableBefore extends Commentable {
  abstract override def compile(): String = "/* " + comment + " */" + super.compile()
  abstract override def pretty(spaces: Int): String = "/* " + comment + " */\n" + super.pretty(spaces)
}

trait CommentableAfter extends Commentable {
  abstract override def compile(): String = super.compile() + "/* " + comment + " */"
  abstract override def pretty(spaces: Int): String = super.pretty(spaces) + " /* " + comment + " */"
}