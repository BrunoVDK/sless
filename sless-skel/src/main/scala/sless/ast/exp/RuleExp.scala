package sless.ast.exp

import sless.ast.exp.selector.SelectorExp

case class RuleExp(selector: SelectorExp, declarations: Seq[DeclarationExp]) extends Expression with Commentable {

}
