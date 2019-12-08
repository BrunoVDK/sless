package sless.ast

import sless.dsl._
import sless.ast.exp._

object Base extends BaseLint

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

  override protected def bindTo(s: Selector, declarations: Seq[Declaration]): Rule = RuleExp(s, declarations)

  override def prop(string: String): Property = PropertyExp(string)
  override protected def assign(p: Property, value: Value): Declaration = DeclarationExp(p, value)

  override def value(string: String): Value = ValueStringExp(string)

  override def compile(sheet: Css): String = sheet.compile()
  override def pretty(sheet: Css, spaces: Int): String = sheet.pretty(spaces)

}

class BaseLint extends Base with LintDSL {
  override def removeEmptyRules(css: Css): (Boolean, Css) = css.withoutEmptyRules
  override def aggregateMargins(css: Css): (Boolean, Css) = css.aggregate(prop("margin-top"),
    prop("margin-right"),
    prop("margin-bottom"),
    prop("margin-left"))
  override def limitFloats(css: Css, n: Integer): Boolean = css.occurrences(prop("float")) > n
}