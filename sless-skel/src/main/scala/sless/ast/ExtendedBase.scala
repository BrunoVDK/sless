package sless.ast

import sless.ast.exp._
import sless.dsl._

object ExtendedBase extends BaseExtend with MixinDSL

class BaseComment extends BaseLint with CommentDSL {
  override protected def commentRule(rule: Rule, str: String): Rule = rule.copy(rule.selector, rule.elements, str)
  override protected def commentDeclaration(declaration: Declaration, str: String): Declaration =
    declaration.copy(declaration.property, declaration.value, str)
}

class BaseValues extends BaseComment with BetterValuesDSL {

}

class BaseNesting extends BaseValues with NestedSelectorDSL {
  override type RuleOrDeclaration = sless.ast.exp.RuleOrDeclaration
  override val Parent: SelectorExp = SelectorParent
  override protected def bindWithNesting(s: SelectorExp, rules: Seq[RuleOrDeclaration]): Rule = new RuleExp(s, rules)
}

class BaseMerge extends BaseNesting with MergeDSL {
  override def mergeSheets(cssSheets: CssExp*): CssExp = cssSheets.reduce((x,y) => x.merge(y))
}

class BaseExtend extends BaseMerge with ExtendDSL {
  override protected def extendI(s: SelectorExp, selector: SelectorExp): SelectorExp = ???
}