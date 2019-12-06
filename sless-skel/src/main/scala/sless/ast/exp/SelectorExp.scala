package sless.ast.exp

sealed trait SelectorExp extends Expression

// Constructors

case class SelectorAllExp() extends SelectorExp {
  override def pretty(spaces: Int): String = "*"
}

case class SelectorListExp(s: Seq[SelectorExp]) extends SelectorExp {
  override def compile(): String = s.map(_.compile()).mkString(",")
  override def pretty(spaces: Int): String = s.map(_.pretty(spaces)).mkString(", ")
}

case class SelectorTypeExp(s: String) extends SelectorExp {
  override def pretty(spaces: Int): String = s
}

// Combinators

abstract class SelectorCombinatorExp(s1: SelectorExp, s2: SelectorExp, sep: String) extends SelectorExp {
  val separator: String = sep
  override def compile(): String = s1.compile() + sep + s2.compile()
  override def pretty(spaces: Int): String = s1.pretty(spaces) + " " + sep + " " + s2.pretty(spaces)
}

case class SelectorAdjacentExp(s1: SelectorExp, s2: SelectorExp) extends SelectorCombinatorExp(s1, s2, "+")
case class SelectorChildExp(s1: SelectorExp, s2: SelectorExp) extends SelectorCombinatorExp(s1, s2, ">")
case class SelectorGeneralExp(s1: SelectorExp, s2: SelectorExp) extends SelectorCombinatorExp(s1, s2, "~")
case class SelectorDescendantExp(s1: SelectorExp, s2: SelectorExp) extends SelectorCombinatorExp(s1, s2, " ") {
  override def pretty(spaces: Int): String = s1.pretty(spaces) + separator + s2.pretty(spaces)
}

// Modifiers

abstract class SelectorModifierExp(s: SelectorExp, sfx: String) extends SelectorExp {
  def suffix: String = sfx
  override def compile(): String = s.compile() + suffix
  override def pretty(spaces: Int): String = s.pretty(spaces) + suffix
}

case class SelectorClassExp(s: SelectorExp, c: String) extends SelectorModifierExp(s, "." + c)
case class SelectorIDExp(s: SelectorExp, c: String) extends SelectorModifierExp(s, "#" + c)
case class SelectorPseudoClassExp(s: SelectorExp, c: String) extends SelectorModifierExp(s, ":" + c)
case class SelectorPseudoElementExp(s: SelectorExp, c: String) extends SelectorModifierExp(s, "::" + c)
case class SelectorAttributeExp(s: SelectorExp, attr: String, v: ValueExp) extends SelectorModifierExp(s, "") {
  override def compile(): String = s.compile() + "[" + attr + "=" + v.compile() + "]"
  override def pretty(spaces: Int): String = s.pretty(spaces) + "[" + attr + "=" + v.pretty(spaces) + "]"
}

