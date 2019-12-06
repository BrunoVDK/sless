package sless.ast.exp.selector

case class SelectorListExp(s: Seq[SelectorExp]) extends SelectorExp {
  override def compile(): String = s.map(_.compile()).mkString(",")
  override def pretty(spaces: Int): String = s.map(_.pretty(spaces)).mkString(", ")
}
