package sless.ast.exp

case class CssExp(rules: RuleExp*) extends Expression {

  override def compile(): String = rules(0).compile()

}
