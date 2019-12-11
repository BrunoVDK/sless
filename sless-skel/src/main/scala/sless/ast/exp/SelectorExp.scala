package sless.ast.exp

/**
  * A selector expression is a selector at the level of a rule. It is a container for selector elements.
  *   Its extensions are a bunch of <selectors> 'to-be-extended-with' <selectors> relationships.
  */
case class SelectorExp(elements: Seq[SelectorElementExp], extensions: Map[SelectorExp,SelectorExp] = Map()) extends Expression {

  override def compile(): String = elements.map(_.compile()).mkString(",")
  override def pretty(spaces: Int): String = elements.map(_.pretty(spaces)).mkString(", ")

  def replace(el: SelectorElementExp, rep: SelectorExp): SelectorExp =
    SelectorExp(elements.flatMap(x => if (el == x) rep.elements else List(x)), extensions)

  def ground(parent: SelectorExp): SelectorExp =
    if (elements.exists(!_.grounded)) SelectorExp(elements.flatMap(_.replace(SelectorParentExp, parent.elements)), extensions)
    else parent.combine(this, " ")

  def modify(mod: SelectorElementExp => SelectorElementExp) = SelectorExp(elements.map(mod(_)), extensions)
  def combine(other: SelectorExp, separator: String) = SelectorExp(
    (this.elements.init :+ SelectorCombinatorExp(this.elements.last, other.elements.head, separator))
      ++ other.elements.tail,
    extensions
  )

  def addExtension(toExtend: SelectorExp, extension: SelectorExp): SelectorExp = copy(elements, extensions + (toExtend -> extension))

  def extend(other: SelectorExp): SelectorExp = extensions.foldLeft(other)((cur, extension) => extension(cur))
  def applyExtension(toExtend: SelectorExp, extension: SelectorExp, from: Int = 0): SelectorExp = {
    val idx = elements.indexOfSlice(toExtend.elements, from)
    if (idx < 0) SelectorExp(elements)
    else SelectorExp(elements.patch(idx + toExtend.elements.length, extension.elements, 0)).
      applyExtension(toExtend, extension, idx + toExtend.elements.length + extension.elements.length)
  }

  def intersect(other: SelectorExp): Option[(SelectorExp, SelectorExp, SelectorExp)] = { // returns (Left outer part, intersect, right outer part)
    val intersect = elements.intersect(other.elements)
    if (intersect.isEmpty) None
    else Some(
      SelectorExp(elements.diff(intersect), extensions),
      SelectorExp(intersect),
      SelectorExp(other.elements.diff(intersect), other.extensions)
    )
  }

}
