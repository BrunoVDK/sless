package sless.ast.exp

case class CssExp(rules: Seq[RuleExp]) extends Expression {

  override def compile(): String = rules(0).compile()

}
