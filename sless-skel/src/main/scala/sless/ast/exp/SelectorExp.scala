package sless.ast.exp

sealed trait SelectorExp extends Expression {

  val dependants: Seq[SelectorExp] = Seq[SelectorExp]() // Selectors it extends

  def ground(parent: SelectorExp): SelectorExp =
    if (this.contains(SelectorParent)) replace(SelectorParent, parent)
    else SelectorCombinatorExp(parent, this, " ")
  def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = if (this == sel) rep else this
  def contains(other: SelectorExp): Boolean = this == other
  def overlap(other: SelectorExp): (Option[SelectorExp], Option[SelectorExp], Option[SelectorExp]) =
    if (this == other) (None, Some(this), None) else (Some(this), None, Some(other))

}

// Constructors

case object SelectorParent extends SelectorExp

case class SelectorAllExp() extends SelectorExp {
  override def pretty(spaces: Int): String = "*"
}

case class SelectorListExp(s: Seq[SelectorExp]) extends SelectorExp {

  override def compile(): String = s.map(_.compile()).mkString(",")
  override def pretty(spaces: Int): String = s.map(_.pretty(spaces)).mkString(", ")

  override def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = {
    SelectorListExp( sel match {
       case x: SelectorListExp => {
         val idx = s.indexOfSlice(x.s)
         (if (idx < 0) s else s.patch(idx, List(rep), x.s.length)).map(_.replace(sel, rep))
       }
       case _ => s.map(_.replace(sel, rep))
    })
  }

  override def contains(other: SelectorExp): Boolean = s.contains(other) | (other match {
    case x: SelectorListExp => s.indexOfSlice(x.s) < 0
    case _ => false
  })

  override def overlap(other: SelectorExp): (Option[SelectorExp], Option[SelectorExp], Option[SelectorExp]) = ???

}

case class SelectorTypeExp(s: String) extends SelectorExp {
  override def pretty(spaces: Int): String = s
}

// Combinators

case class SelectorCombinatorExp(s1: SelectorExp, s2: SelectorExp, sep: String) extends SelectorExp {
  override def compile(): String = s1.compile() + sep + s2.compile()
  override def pretty(spaces: Int): String = s1.pretty(spaces) + (if (sep == " ") sep else " " + sep + " ") + s2.pretty(spaces)
  override def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = SelectorCombinatorExp(s1.replace(sel, rep), s2.replace(sel, rep), sep)
  override def contains(other: SelectorExp): Boolean = super.contains(other) | s1.contains(other) | s2.contains(other)
}

// Modifiers

case class SelectorModifierExp(s: SelectorExp, suffix: String) extends SelectorExp {
  override def compile(): String = s.compile() + suffix
  override def pretty(spaces: Int): String = s.pretty(spaces) + suffix
  override def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = SelectorModifierExp(s.replace(sel, rep), suffix)
  override def contains(other: SelectorExp): Boolean = super.contains(other) | s.contains(other)
}

case class SelectorAttributeExp(s: SelectorExp, attr: String, v: ValueExp) extends SelectorExp {
  override def compile(): String = s.compile() + "[" + attr + "=" + v.compile() + "]"
  override def pretty(spaces: Int): String = s.pretty(spaces) + "[" + attr + "=" + v.pretty(spaces) + "]"
  override def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = SelectorAttributeExp(s.replace(sel, rep), attr, v)
  override def contains(other: SelectorExp): Boolean = super.contains(other) | s.contains(other)
}
