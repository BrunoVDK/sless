package sless.ast

import sless.dsl._
import sless.ast.exp._

class Base extends PropertyDSL with SelectorDSL with ValueDSL with Compilable {

  override type Rule = RuleExp
  override type Css = CssExp
  override type Selector = SelectorExp
  override type Declaration = DeclarationExp
  override type Property = PropertyExp
  override type Value = ValueExp

  override protected def fromRules(rules: Seq[Rule]): Css = CssExp(rules)

  override protected def className(s: Selector, string: String): Selector = SelectorModifierExp(s, "." + string)
  override protected def id(s: Selector, string: String): Selector = SelectorModifierExp(s, "#" + string)
  override protected def attribute(s: Selector, attr: String, value: Value): Selector = SelectorAttributeExp(s, attr, value)
  override protected def pseudoClass(s: Selector, string: String): Selector = SelectorModifierExp(s, ":" + string)
  override protected def pseudoElement(s: Selector, string: String): Selector = SelectorModifierExp(s, "::" + string)

  override protected def adjacent(s1: Selector, s2: Selector): Selector = SelectorCombinatorExp(s1, s2, "+")
  override protected def general(s1: Selector, s2: Selector): Selector = SelectorCombinatorExp(s1, s2, "~")
  override protected def child(s1: Selector, s2: Selector): Selector = SelectorCombinatorExp(s1, s2, ">")
  override protected def descendant(s1: Selector, s2: Selector): Selector = SelectorCombinatorExp(s1, s2, " ")

  override protected def group(selectors: Seq[Selector]): Selector = SelectorListExp(selectors)
  override def tipe(string: String): Selector = SelectorTypeExp(string)
  override val All: Selector = SelectorAllExp()

  override protected def bindTo(s: Selector, declarations: Seq[Declaration]): Rule = RuleGroundExp(s, declarations)

  override def prop(string: String): Property = PropertyExp(string)
  override protected def assign(p: Property, value: Value): Declaration = DeclarationExp(p, value)

  override def value(string: String): Value = ValueStringExp(string)

  override def compile(sheet: Css): String = sheet.compile()
  override def pretty(sheet: Css, spaces: Int): String = sheet.pretty(spaces)

}

class BaseComment extends BaseLint with CommentDSL {
  override protected def commentRule(rule: Rule, str: String): Rule =
    new Rule(rule.selector, rule.elements) with CommentableBefore {override val comment: String = str}
  override protected def commentDeclaration(declaration: Declaration, str: String): Declaration =
    new Declaration(declaration.property, declaration.value) with CommentableAfter {override val comment: String = str}
}

class BaseLint extends Base with LintDSL {
  override def removeEmptyRules(css: Css): (Boolean, Css) = css.withoutEmptyRules
  override def aggregateMargins(css: Css): (Boolean, Css) = css.aggregate(prop("margin-top"),
    prop("margin-right"),
    prop("margin-bottom"),
    prop("margin-left"))
  override def limitFloats(css: Css, n: Integer): Boolean = css.occurrences(prop("float")) > n
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

object Base extends BaseExtend with MixinDSL {

}