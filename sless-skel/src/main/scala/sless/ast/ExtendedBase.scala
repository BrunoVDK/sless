package sless.ast

import sless.ast.exp._
import sless.dsl._

class ExtendedBase extends Base with CommentDSL with NestedSelectorDSL with BetterValuesDSL with MergeDSL with ExtendDSL {

  override protected def commentRule(rule: Rule, str: String): Rule = rule.copy(rule.selector, rule.elements, str)
  override protected def commentDeclaration(declaration: Declaration, str: String): Declaration =
    declaration.copy(declaration.property, declaration.value, str)

  override type RuleOrDeclaration = sless.ast.exp.RuleOrDeclaration
  override val Parent: Selector = SelectorParent
  override protected def bindWithNesting(s: Selector, rules: Seq[RuleOrDeclaration]): Rule = RuleExp(s, rules)

  override def mergeSheets(cssSheets: Css*): Css = cssSheets.reduce((x,y) => x.merge(y))

  override protected def extendI(s: Selector, selector: Selector): Selector =

}

object ExtendedBase extends ExtendedBase with MixinDSL