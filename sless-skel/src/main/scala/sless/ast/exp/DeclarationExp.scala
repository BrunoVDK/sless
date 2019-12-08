package sless.ast.exp

case class DeclarationExp(property: PropertyExp, value: ValueExp, override val comment: String = "") extends RuleOrDeclaration with Commentable {
  override def compile(): String = property.compile() + ":" + value.compile() + comment()
  override def pretty(spaces: Int): String = property.pretty(spaces) + ": " + value.pretty(spaces) + comment(" ".+(_))
}
