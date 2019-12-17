package sless.ast.exp.selector

import sless.ast.exp.Expression

/**
  * A selector expression is a selector for a style rule.
  */
abstract class SelectorExp(extensions: Seq[SelectorExp] = List()) extends Expression {

  def replace(el: SelectorExp, rep: SelectorExp): SelectorExp = if (el == this) rep.addExtensions(extensions) else this
  def grounded: Boolean = true
  def ground(parent: SelectorExp): SelectorExp =
    if (!grounded) replace(SelectorParentExp(), parent)
    else SelectorCombinatorExp(parent, this, " ")

  def addExtension(toExtend: SelectorExp): SelectorExp
  def addExtensions(extensions: Seq[SelectorExp]) : SelectorExp = extensions.foldLeft(this)((cur,sel) => cur.addExtension(sel))
  def extensionPairs: Seq[(SelectorExp, SelectorExp)] = extensions.map((this,_))
  def extend(toExtend: SelectorExp): SelectorExp = toExtend match {
    case l: SelectorListExp => SelectorListExp(l.selectors.flatMap(s => if (extensions.contains(s)) List(s,this) else List(s)))
    case _: SelectorExp => if (extensions.contains(toExtend)) SelectorListExp(List(toExtend, this)) else toExtend
  }

  def intersect(other: SelectorExp): Option[(SelectorExp, SelectorExp, SelectorExp)] = other match {
    case l: SelectorListExp => if (l.selectors.contains(this)) Some((null, this, SelectorListExp(l.selectors.filterNot(_ == this)))) else None
    case _: SelectorExp => if (this == other) Some((null, this, null)) else None
  }

}
