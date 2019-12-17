package sless.ast.exp

import sless.ast.exp.value.ValueExp

/**
  * A declaration expression represents a CSS declaration with a property and a value.
  */
case class DeclarationExp(property: PropertyExp, value: ValueExp, override val comment: String = "") extends RuleOrDeclaration with CommentableExp {
  override def compile(): String = property.compile() + ":" + value.compile() + comment()
  override def pretty(spaces: Int): String = property.pretty(spaces) + ": " + value.pretty(spaces) + comment(" ".+(_))
}
