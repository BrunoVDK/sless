package sless.ast.exp.selector

import sless.ast.exp.Expression

/**
  * A selector expression is a selector for a style rule.
  */
abstract class SelectorExp(extensions: Seq[SelectorExp] = List()) extends Expression {

  def replace(el: SelectorExp, rep: SelectorExp): SelectorExp =
    if (el == this) rep.addExtensions(extensions.map(_.replace(el, rep)))
    else this.withExtensions(extensions.map(_.replace(el, rep)))

  def grounded: Boolean = true
  def ground(parent: SelectorExp): SelectorExp =
    if (!grounded) replace(SelectorParentExp(), parent)
    else SelectorCombinatorExp(parent, this, " ")

  def withExtensions(extensions: Seq[SelectorExp]): SelectorExp
  def addExtension(toExtend: SelectorExp): SelectorExp = withExtensions(toExtend +: extensions)
  def addExtensions(toExtend: Seq[SelectorExp]): SelectorExp = withExtensions(toExtend ++ extensions)

  def extensionPairs: Seq[(SelectorExp, SelectorExp)] = extensions.map((this,_))
  def extend(part: SelectorExp, ext: SelectorExp): SelectorExp = if (part == this) SelectorListExp(List(part, ext)) else this

  def intersect(other: SelectorExp): Option[(SelectorExp, SelectorExp, SelectorExp)] = other match {
    case l: SelectorListExp =>
      if (l.selectors.contains(this)) Some(null, this, {
        val rem = l.selectors.filterNot(_ == this)
        if (rem.isEmpty) null else if(rem.size == 1) rem.head else SelectorListExp(rem)
      })
      else None
    case _: SelectorExp => if (this == other) Some(null, this, null) else None
  }

}
