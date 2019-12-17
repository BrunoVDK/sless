package sless.ast.exp.selector

case class SelectorListExp(ss: Seq[SelectorExp]) extends SelectorExp {

  lazy val selectors: Seq[SelectorExp] = ss.flatMap {
    case l: SelectorListExp => l.selectors
    case s: SelectorExp => List(s)
  }

  override def compile(): String = selectors.map(_.compile()).mkString(",")
  override def pretty(spaces: Int): String = selectors.map(_.pretty(spaces)).mkString(", ")

  override def replace(el: SelectorExp, rep: SelectorExp): SelectorExp = copy(selectors.map(_.replace(el,rep)))
  override def grounded: Boolean = selectors.forall(_.grounded)

  override def addExtension(toExtend: SelectorExp): SelectorExp = SelectorListExp(selectors.map(_.addExtension(toExtend)))
  override def extend(toExtend: SelectorExp): SelectorExp = selectors.foldRight(toExtend)((selector,cur) => selector.extend(cur))

  override def intersect(other: SelectorExp): Option[(SelectorExp, SelectorExp, SelectorExp)] = other match {
    case l: SelectorListExp =>
      val intersect = selectors.intersect(l.selectors) ; val left = selectors.diff(intersect) ; val right = l.selectors.diff(intersect)
      Some((
        if (left.isEmpty) null else if (left.size == 1) left.head else copy(left),
        if (intersect.isEmpty) return None else if (intersect.size == 1) left.head else copy(intersect),
        if (right.isEmpty) null else if (right.size == 1) right.head else copy(right)))
    case _ => if (selectors.contains(other)) Some((copy(selectors.filterNot(_ == other)), other, null)) else None
  }

}
