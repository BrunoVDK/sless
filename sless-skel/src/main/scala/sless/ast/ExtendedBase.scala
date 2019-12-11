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

  override protected def extendI(s: Selector, selector: Selector): Selector = s.addExtension(selector, s)

  override def inherit: ValueExp = ValueInheritExp
  override def initial: ValueExp = ValueInitialExp

  override type MarginWidth = ValueMarginWidthExp
  override val margin: Seq[ValueMarginWidthExp] => ValueMarginExp = ValueMarginExp
  override def auto: MarginWidth = ValueMarginWidthExp(Auto)
  override def length[T: Numeric](x: T, s: String): MarginWidth = ValueMarginWidthExp(Length(x, s))

}

object ExtendedBase extends ExtendedBase with MixinDSL