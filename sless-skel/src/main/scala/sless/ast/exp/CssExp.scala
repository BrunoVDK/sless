package sless.ast.exp

case class CssExp(rs: Seq[RuleExp]) extends Expression {

  lazy val rules: Seq[RuleExp] = extend(rs.flatMap(_.flatten()))

  override def compile(): String = rules.map(_.compile()).mkString("")
  override def pretty(spaces: Int): String = rules.map(_.pretty(spaces)).mkString("\n\n")

  def occurrences(p: PropertyExp): Int = rules.foldRight(0)((r,x) => x + r.occurrences(p))
  def withoutEmptyRules: (Boolean, CssExp) = (rules.exists(_.isEmpty), CssExp(rules.filterNot(_.isEmpty)))
  def aggregate(ps: PropertyExp*): (Boolean, CssExp) = {
    val res = rules.map(_.aggregate(ps))
    (!res.exists(_._1 == false), CssExp(res.map(_._2)))
  }

  def merge(cssExp: CssExp): CssExp = ???

  def extend(rules: Seq[RuleExp]): Seq[RuleExp] =
    rules.foldLeft(rules)((cur, r) => cur.map(subject => subject.copy(r.selector.extend(subject.selector), subject.elements)))

}
