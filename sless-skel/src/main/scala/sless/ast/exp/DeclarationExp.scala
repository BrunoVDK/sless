package sless.ast.exp

case class DeclarationExp(property: PropertyExp, value: ValueExp) extends RuleOrDeclaration {
  override def compile(): String = property.compile() + ":" + value.compile()
  override def pretty(spaces: Int): String = property.pretty(spaces) + ": " + value.pretty(spaces)
}
