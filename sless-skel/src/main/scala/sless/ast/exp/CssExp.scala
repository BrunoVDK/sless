package sless.ast.exp

import sless.ast.exp.selector.SelectorExp

/**
  * A CSS expression represents a CSS sheet.
  */
case class CssExp(rs: Seq[RuleExp]) extends Expression {

  lazy val rules: Seq[RuleExp] = extend(rs.flatMap(_.flatten()))

  override def compile(): String = rules.map(_.compile()).mkString("")

  override def pretty(spaces: Int): String = rules.map(_.pretty(spaces)).mkString("\n\n")

  def occurrences(p: PropertyExp): Int = rules.foldRight(0)((r, x) => x + r.occurrences(p))

  def withoutEmptyRules: (Boolean, CssExp) = (rules.exists(_.isEmpty), CssExp(rules.filterNot(_.isEmpty)))

  def withoutDuplicateProperties: (Boolean, CssExp) = (rs.exists(_.hasDuplicates), CssExp(rs.map(_.withoutDuplicates)))

  def aggregate(ps: PropertyExp*): (Boolean, CssExp) = {
    val res = rules.map(_.aggregate(ps))
    (!res.exists(_._1 == false), CssExp(res.map(_._2)))
  }

  def merge(right: CssExp): CssExp = CssExp(rules.foldRight(right.rules)((rule, acc) => {
    val idx = acc.indexWhere(_.selector.intersect(rule.selector).isDefined)
    if (idx < 0) rule +: acc else acc.patch(idx, acc(idx).merge(rule), 1)
  }))

  def extend(rules: Seq[RuleExp]): Seq[RuleExp] = {
    // rules.map(rules.foldLeft(_)((cur,rule) => RuleExp(rule.selector.extend(cur.selector), cur.declarations, cur.comment)))
    val extensions: Seq[(SelectorExp, SelectorExp)] = rules.map(_.selector).flatMap(_.extensionPairs).distinct
    rules.map(extensions.foldLeft(_)((rule,ext) => RuleExp(ext._1.extend(rule.selector), rule.declarations, rule.comment)))
  }

}
