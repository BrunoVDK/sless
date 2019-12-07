package sless.ast.exp

case class RuleExp(selector: SelectorExp, declarations: Seq[DeclarationExp]) extends Expression with Commentable {

  override def compile(): String = selector.compile() + "{" + declarations.map(_.compile()).mkString + "}"
  override def pretty(spaces: Int): String =
    selector.pretty(spaces) + " {\n" + declarations.map(" " * spaces + _.pretty(spaces) + "\n").mkString + "}"

  def isEmpty: Boolean = declarations.isEmpty
  def occurrences(p: PropertyExp): Int = declarations.count(_.property == p)

  def aggregate(ps: Seq[PropertyExp]): (Boolean, RuleExp) = {
    val vs = ps.flatMap(p => declarations.find(_.property == p)).map(_.value)
    val margin = DeclarationExp(PropertyExp("margin"), vs.reduce((d1,d2) => d1.merge(d2)))
    val res = declarations.span(d => ps.contains(d.property))
    (vs.length == ps.length, RuleExp(selector, res._1 ++ (margin +: res._2.filterNot(d => ps.contains(d.property)))))
  }

}
