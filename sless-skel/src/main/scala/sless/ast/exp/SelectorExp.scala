package sless.ast.exp

sealed trait SelectorExp extends Expression {

  def ground(parent: SelectorExp): SelectorExp =
    if (this.contains(SelectorParent)) replace(SelectorParent, parent)
    else SelectorCombinatorExp(parent, this, " ")
  def contains(other: SelectorExp): Boolean = this == other
  def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = if (this == sel) rep else this
  def overlap(other: SelectorExp): Option[(Option[SelectorExp], SelectorExp, Option[SelectorExp])] =
    if (this == other) Some(None, this, None) else None
  
}

// Constructors

case object SelectorParent extends SelectorExp

case class SelectorAllExp() extends SelectorExp {
  override def pretty(spaces: Int): String = "*"
}

case class SelectorListExp(selectors: Seq[SelectorExp]) extends SelectorExp {

  lazy val elems: Seq[SelectorExp] = selectors.flatMap(_ match {
    case s: SelectorListExp => s.elems
    case other => List(other)
  })

  override def compile(): String = elems.map(_.compile()).mkString(",")
  override def pretty(spaces: Int): String = elems.map(_.pretty(spaces)).mkString(", ")

  override def contains(other: SelectorExp): Boolean = elems.contains(other) | (other match {
    case x: SelectorListExp => elems.indexOfSlice(x.elems) < 0
    case _ => false
  })

  override def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = SelectorListExp(sel match {
      case s: SelectorListExp => matchIndices(elems, s.elems, 0).foldLeft(elems)((x,i) => x.patch(i, List(rep), s.elems.length))
      case _ => elems.map(_.replace(sel, rep))
  })

  protected def matchIndices(seq: Seq[SelectorExp], m: Seq[SelectorExp], last: Int, acc: Seq[Int] = Seq[Int]()): Seq[Int] = {
    val nextIndex = seq.indexOfSlice(m, last + 1)
    matchIndices(seq, m, nextIndex + m.length, nextIndex +: acc)
  }

}

case class SelectorTypeExp(s: String) extends SelectorExp {
  override def pretty(spaces: Int): String = s
}

// Combinators

case class SelectorCombinatorExp(s1: SelectorExp, s2: SelectorExp, sep: String) extends SelectorExp {
  override def compile(): String = s1.compile() + sep + s2.compile()
  override def pretty(spaces: Int): String = s1.pretty(spaces) + (if (sep == " ") sep else " " + sep + " ") + s2.pretty(spaces)
  override def contains(other: SelectorExp): Boolean = super.contains(other) | s1.contains(other) | s2.contains(other)
  override def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = SelectorCombinatorExp(s1.replace(sel, rep), s2.replace(sel, rep), sep)
}

// Modifiers

case class SelectorModifierExp(s: SelectorExp, suffix: String) extends SelectorExp {
  override def compile(): String = s.compile() + suffix
  override def pretty(spaces: Int): String = s.pretty(spaces) + suffix
  override def contains(other: SelectorExp): Boolean = super.contains(other) | s.contains(other)
  override def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = SelectorModifierExp(s.replace(sel, rep), suffix)
}

case class SelectorAttributeExp(s: SelectorExp, attr: String, v: ValueExp) extends SelectorExp {
  override def compile(): String = s.compile() + "[" + attr + "=" + v.compile() + "]"
  override def pretty(spaces: Int): String = s.pretty(spaces) + "[" + attr + "=" + v.pretty(spaces) + "]"
  override def contains(other: SelectorExp): Boolean = super.contains(other) | s.contains(other)
  override def replace(sel: SelectorExp, rep: SelectorExp): SelectorExp = SelectorAttributeExp(s.replace(sel, rep), attr, v)
}
