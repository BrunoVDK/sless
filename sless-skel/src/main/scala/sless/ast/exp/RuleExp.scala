package sless.ast.exp

import sless.ast.exp.selector.SelectorExp

case class RuleExp(selector: SelectorExp, declarations: Seq[DeclarationExp]) extends Expression with Commentable {
  override def compile(): String = selector.compile() + "{" + declarations.map(_.compile()).mkString + "}"
  override def pretty(spaces: Int): String =
    selector.pretty(spaces) + " {\n" + declarations.map(" " * spaces + _.pretty(spaces) + "\n").mkString + "}"
}
