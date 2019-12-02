package sless.ast.exp

case class RuleExp(selector: SelectorExp, declarations: DeclarationExp*) extends Expression {

}
