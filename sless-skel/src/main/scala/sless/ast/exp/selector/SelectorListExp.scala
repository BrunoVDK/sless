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

  override def withExtensions(extensions: Seq[SelectorExp]): SelectorExp = SelectorListExp(selectors.map(_.withExtensions(extensions)))
  override def extensionPairs: Seq[(SelectorExp, SelectorExp)] = selectors.reverse.flatMap(_.extensionPairs)
  override def extend(part: SelectorExp, ext: SelectorExp): SelectorExp = copy(selectors.map(_.extend(part, ext)))

  override def intersect(other: SelectorExp): Option[(SelectorExp, SelectorExp, SelectorExp)] = other match {
    case l: SelectorListExp =>
      val intersect = selectors.intersect(l.selectors)
      val left = selectors.diff(intersect)
      val right = l.selectors.diff(intersect)
      Some(
        if (left.isEmpty) null else if (left.size == 1) left.head else copy(left),
        if (intersect.isEmpty) return None else if (intersect.size == 1) left.head else copy(intersect),
        if (right.isEmpty) null else if (right.size == 1) right.head else copy(right))
    case _ => if (selectors.contains(other)) Some({
      val rem = selectors.filterNot(_ == other)
      if (rem.isEmpty) null else if (rem.size == 1) rem.head else copy(rem)
    }, other, null) else None
  }

}
