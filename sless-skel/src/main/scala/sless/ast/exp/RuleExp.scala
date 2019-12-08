package sless.ast.exp

case class RuleExp(selector: SelectorExp, elements: Seq[RuleOrDeclaration], override val comment: String = "") extends RuleOrDeclaration with Commentable {

  lazy val declarations: Seq[DeclarationExp] = elements.flatMap(_ match {case d: DeclarationExp => Some(d) ; case _ => None})
  lazy val rules: Seq[RuleExp] = {
    elements.flatMap(_ match {
      case r: RuleExp => Some(r.copy(r.selector.ground(selector), r.elements, comment))
      case _ => None
    })
  }

  override def compile(): String = comment() + selector.compile() + "{" + declarations.map(_.compile()).mkString + "}"
  override def pretty(spaces: Int): String =
    comment(_.+("\n")) + selector.pretty(spaces) + " {\n" + declarations.map(" " * spaces + _.pretty(spaces) + "\n").mkString + "}"

  def isEmpty: Boolean = declarations.isEmpty
  def occurrences(p: PropertyExp): Int = declarations.count(_.property == p)

  def aggregate(ps: Seq[PropertyExp]): (Boolean, RuleExp) = {
    val vs = ps.flatMap(p => declarations.find(_.property == p)).map(_.value)
    val margin = DeclarationExp(PropertyExp("margin"), vs.reduce((d1,d2) => d1.merge(d2)))
    val res = declarations.span(d => !ps.contains(d.property))
    (vs.length == ps.length, this.copy(selector, res._1 ++ (margin +: res._2.filterNot(d => ps.contains(d.property))), comment))
  }

  def flatten(): Seq[RuleExp] =
    if (!isEmpty | rules.isEmpty) this +: rules.flatMap(_.flatten())
    else rules.flatMap(_.flatten())

}
