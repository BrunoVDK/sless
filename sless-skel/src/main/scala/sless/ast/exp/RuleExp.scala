package sless.ast.exp

import sless.ast.exp.selector.SelectorExp

case class RuleExp(selector: SelectorExp, declarations: DeclarationExp*) extends Expression with Commentable {

}
