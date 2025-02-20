package sless.ast.exp

import sless.ast.exp.selector.SelectorExp

/**
  * A rule expression represents a CSS rule having a selector and a list of elements.
  *   These elements can be either declarations or (when there's nesting) other rules.
  *   Nested rules can be flattened which gives a list of rules.
  *   Rules can also be merged with other rules.
  */
case class RuleExp(selector: SelectorExp, elements: Seq[RuleOrDeclaration], override val comment: String = "") extends RuleOrDeclaration with CommentableExp {

  lazy val declarations: Seq[DeclarationExp] = elements.flatMap(_ match {case d: DeclarationExp => Some(d) ; case _ => None})
  lazy val rules: Seq[RuleExp] = {
    elements.flatMap(_ match {
      case r: RuleExp => Some(RuleExp(r.selector.ground(selector), r.elements, r.comment))
      case _ => None
    })
  }

  override def compile(): String = comment() + selector.compile() + "{" + declarations.map(_.compile()).mkString + "}"
  override def pretty(spaces: Int): String =
    comment(_.+("\n")) + selector.pretty(spaces) + " {\n" + declarations.map(" " * spaces + _.pretty(spaces) + "\n").mkString + "}"

  val isEmpty: Boolean = declarations.isEmpty
  def occurrences(p: PropertyExp): Int = declarations.count(_.property == p)
  lazy val hasDuplicates: Boolean = withoutDuplicates.elements.size < declarations.size
  lazy val withoutDuplicates: RuleExp = RuleExp(selector, declarations.groupBy(_.property).values.map(_.head).toSeq, comment)

  def aggregate(ps: Seq[PropertyExp]): (Boolean, RuleExp) = {
    val vs = ps.flatMap(p => declarations.find(_.property == p)).map(_.value)
    val margin = DeclarationExp(PropertyExp("margin"), vs.reduce((v1,v2) => v1.aggregate(v2)))
    val res = declarations.span(d => !ps.contains(d.property))
    (vs.length == ps.length, RuleExp(selector, res._1 ++ (margin +: res._2.filterNot(d => ps.contains(d.property))), comment))
  }

  def flatten(): Seq[RuleExp] =
    if (!isEmpty || rules.isEmpty) RuleExp(selector, declarations, comment) +: rules.flatMap(_.flatten())
    else rules.flatMap(_.flatten())

  def merge(other: RuleExp): Seq[RuleExp] = other.selector.intersect(selector) match {
    case None => List(other, this)
    case Some((left, intersect, right)) => List(
      if (left != null) Some(RuleExp(left.withExtensions(List()), other.declarations, other.comment)) else None,
      Some(RuleExp(intersect.withExtensions(List()), declarations ++ other.declarations.filterNot(p => declarations.exists(_.property == p.property)))),
      if (right != null) Some(RuleExp(right.withExtensions(List()), declarations, comment)) else None
    ).flatten
  }

}