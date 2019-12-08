package sless.ast.exp.selector

import sless.ast.exp._

sealed trait SelectorElementExp extends Expression {
  def grounded: Boolean = true
  def ground(parent: Seq[SelectorElementExp]): Seq[SelectorElementExp] = List(this)
}

case object SelectorParentExp extends SelectorElementExp {
  override def grounded: Boolean = false
  override def ground(parent: Seq[SelectorElementExp]): Seq[SelectorElementExp] = parent
}

case class SelectorAllExp() extends SelectorElementExp {
  override def pretty(spaces: Int): String = "*"
}

case class SelectorTypeExp(s: String) extends SelectorElementExp {
  override def pretty(spaces: Int): String = s
}

// Combinators

case class SelectorCombinatorExp(s1: SelectorElementExp, s2: SelectorElementExp, sep: String) extends SelectorElementExp {
  override def compile(): String = s1.compile() + sep + s2.compile()
  override def pretty(spaces: Int): String = s1.pretty(spaces) + (if (sep == " ") sep else " " + sep + " ") + s2.pretty(spaces)
  override def grounded: Boolean = s1.grounded & s2.grounded
  override def ground(parent: Seq[SelectorElementExp]): Seq[SelectorElementExp] = {
    val new1 = s1.ground(parent)
    val new2 = s2.ground(parent)
    (new1.init :+ SelectorCombinatorExp(new1.last, new2.head, sep)) ++ new2.tail
  }
}

// Modifiers

case class SelectorModifierExp(s: SelectorElementExp, suffix: String) extends SelectorElementExp {
  override def compile(): String = s.compile() + suffix
  override def pretty(spaces: Int): String = s.pretty(spaces) + suffix
  override def grounded: Boolean = s.grounded
  override def ground(parent: Seq[SelectorElementExp]): Seq[SelectorElementExp] = s.ground(parent).map(SelectorModifierExp(_, suffix))
}

case class SelectorAttributeExp(s: SelectorElementExp, attr: String, v: ValueExp) extends SelectorElementExp {
  override def compile(): String = s.compile() + "[" + attr + "=" + v.compile() + "]"
  override def pretty(spaces: Int): String = s.pretty(spaces) + "[" + attr + "=" + v.pretty(spaces) + "]"
  override def grounded: Boolean = s.grounded
  override def ground(parent: Seq[SelectorElementExp]): Seq[SelectorElementExp] = s.ground(parent).map(SelectorAttributeExp(_, attr, v))
}