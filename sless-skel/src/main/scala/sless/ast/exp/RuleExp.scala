package sless.ast.exp

trait RuleOrDeclaration extends Expression

class RuleExp(val selector: SelectorExp, val elements: Seq[RuleOrDeclaration]) extends RuleOrDeclaration {

  val declarations: Seq[DeclarationExp] = elements.flatMap(_ match {case d: DeclarationExp => Some(d) ; case _ => None})
  val rules: Seq[RuleExp] = {
    elements.flatMap(_ match {
      case r: RuleExp =>
        Some(
          if (r.selector.contains(SelectorParent))
            new RuleExp(r.selector.replace(SelectorParent, selector), r.elements)
          else
            new RuleExp(SelectorCombinatorExp(selector, r.selector, " "), r.elements)
        )
      case _ => None
    })
  }

  override def compile(): String = selector.compile() + "{" + declarations.map(_.compile()).mkString + "}"
  override def pretty(spaces: Int): String =
    selector.pretty(spaces) + " {\n" + declarations.map(" " * spaces + _.pretty(spaces) + "\n").mkString + "}"

  def isEmpty: Boolean = declarations.isEmpty
  def occurrences(p: PropertyExp): Int = declarations.count(_.property == p)

  def aggregate(ps: Seq[PropertyExp]): (Boolean, RuleExp) = {
    val vs = ps.flatMap(p => declarations.find(_.property == p)).map(_.value)
    val margin = DeclarationExp(PropertyExp("margin"), vs.reduce((d1,d2) => d1.merge(d2)))
    val res = declarations.span(d => !ps.contains(d.property))
    (vs.length == ps.length, RuleGroundExp(selector, res._1 ++ (margin +: res._2.filterNot(d => ps.contains(d.property)))))
  }

  def flatten(): Seq[RuleExp] = if (declarations.nonEmpty)
    RuleGroundExp(selector, declarations) +: rules.flatMap(_.flatten())
  else
    rules.flatMap(_.flatten())
}

case class RuleGroundExp(s: SelectorExp, override val declarations: Seq[DeclarationExp]) extends RuleExp(s, declarations) {
  override val rules: Seq[RuleExp] = Seq[RuleExp]()
}
