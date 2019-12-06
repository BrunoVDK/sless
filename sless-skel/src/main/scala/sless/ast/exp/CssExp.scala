package sless.ast.exp

case class CssExp(rules: Seq[RuleExp]) extends Expression {
  override def compile(): String = rules.map(_.compile()).mkString
  override def pretty(spaces: Int): String = rules.map(_.pretty(spaces)).mkString("\n\n")
}
