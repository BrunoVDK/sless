package sless.ast

import sless.ast.exp._
import sless.dsl._

/**
  * The extended base class implementing the traits for the base DSL as well as all other extensions in the assignment.
  */
class ExtendedBase extends Base with CommentDSL with NestedSelectorDSL with BetterValuesDSL with MergeDSL with ExtendDSL {

  override protected def commentRule(rule: Rule, str: String): Rule = rule.copy(rule.selector, rule.elements, str)
  override protected def commentDeclaration(declaration: Declaration, str: String): Declaration =
    declaration.copy(declaration.property, declaration.value, str)

  override type RuleOrDeclaration = sless.ast.exp.RuleOrDeclaration
  override val Parent: Selector = SelectorExp(List(SelectorParentExp))
  override protected def bindWithNesting(s: Selector, rules: Seq[RuleOrDeclaration]): Rule = RuleExp(s, rules)

  override def mergeSheets(cssSheets: Css*): Css = cssSheets.reduceLeft((x,y) => x.merge(y))

  override protected def extendI(s: Selector, selector: Selector): Selector =
    // s.addExtension(selector, s)
    selector.elements.foldLeft(s)((newS, exp) => newS.addExtension(SelectorExp(List(exp)), s))

  override def inherit: ValueExp = ValueInheritExp
  override def initial: ValueExp = ValueInitialExp

  override type MarginWidth = ValueMarginWidthExp
  override val margin: Seq[ValueMarginWidthExp] => ValueMarginExp = ValueMarginExp
  override def auto: MarginWidth = ValueMarginWidthExp(Auto)

  override def in[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Inches)
  override def mm[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Millimeters)
  override def cm[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Centimeters)
  override def px[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Pixels)
  override def pt[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Points)
  override def pc[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Picas)
  override def em[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Ems)
  override def ex[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Exs)
  override def ch[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Chs)
  override def rem[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Rems)
  override def vw[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Vws)
  override def vh[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Vhs)
  override def vmin[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Vmin)
  override def vmax[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Vmax)
  override def percent[T: Numeric](x: T): ValueMarginWidthExp = Length(x, Percent)

}

object ExtendedBase extends ExtendedBase with MixinDSL