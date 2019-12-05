package sless.ast.exp

case class DeclarationExp(property: PropertyExp, value: ValueExp[_]) extends Expression {

}
