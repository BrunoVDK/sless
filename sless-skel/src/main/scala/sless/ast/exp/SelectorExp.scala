package sless.ast.exp

import sless.ast.exp.selector._

case class SelectorExp(elements: Seq[SelectorElementExp], descendants: Seq[SelectorExp] = List()) extends Expression {

  override def compile(): String = elements.map(_.compile()).mkString(",")
  override def pretty(spaces: Int): String = elements.map(_.pretty(spaces)).mkString(", ")

  def replace(el: SelectorElementExp, rep: SelectorExp): SelectorExp = SelectorExp(elements.flatMap(x => if (el == x) rep.elements else List(x)))

  def ground(parent: SelectorExp): SelectorExp =
    if (elements.exists(!_.grounded)) SelectorExp(elements.flatMap(_.replace(SelectorParentExp, parent.elements)))
    else parent.combine(this, " ")

  def overlap(other: SelectorExp): Option[(Option[SelectorExp], SelectorExp, Option[SelectorExp])] = ???

  def modify(mod: SelectorElementExp => SelectorElementExp) = SelectorExp(elements.map(mod(_)))
  def combine(other: SelectorExp, separator: String) = SelectorExp(
      (this.elements.init :+ SelectorCombinatorExp(this.elements.last, other.elements.head, separator))
      ++ other.elements.tail
  )

}
