package sless.ast.exp.selector

import sless.ast.exp.Expression

/**
  * A selector expression is a selector for a style rule.
  */
abstract class SelectorExp(extensions: Seq[SelectorExp] = List()) extends Expression with Extendable {

  def replace(el: SelectorExp, rep: SelectorExp): SelectorExp = if (el == this) rep else this
  def grounded: Boolean = true
  def ground(parent: SelectorExp): SelectorExp =
    if (!grounded) extensions.foldLeft(replace(SelectorParentExp()(), parent))((cur,e) => cur.addExtension(e.ground(parent)))
    else SelectorCombinatorExp(parent, this, " ")(extensions)

  def addExtension(toExtend: SelectorExp): SelectorExp
  def addExtensions(extensions: Seq[SelectorExp]) : SelectorExp = extensions.foldLeft(this)((cur,sel) => cur.addExtension(sel))
  def extend(toExtend: SelectorExp): SelectorExp = {print(this + "---" + toExtend + "---" + extensions + "--\n");toExtend match {
    case l: SelectorListExp => SelectorListExp(l.selectors.flatMap(s => if (extensions.contains(s)) List(s,this) else List(s)))
    case _: SelectorExp => if (extensions.contains(toExtend)) SelectorListExp(List(toExtend, this)) else toExtend
  }}

  def intersect(other: SelectorExp): Option[(SelectorExp, SelectorExp, SelectorExp)] = other match {
    case l: SelectorListExp => if (l.selectors.contains(this)) Some((null, this, SelectorListExp(l.selectors.filterNot(_ == this)))) else None
    case _: SelectorExp => if (this == other) Some((null, this, null)) else None
  }

}
