package sless.ast

import sless.dsl._
import sless.ast.exp._
import sless.ast.exp.selector._
import sless.ast.exp.selector.combinator._
import sless.ast.exp.selector.modifier._
import sless.ast.exp.value.ValueExp

class Base extends PropertyDSL with SelectorDSL with ValueDSL with Compilable {

  override type Rule = RuleExp
  override type Css = CssExp
  override type Selector = SelectorExp
  override type Declaration = DeclarationExp
  override type Property = PropertyExp
  override type Value = ValueExp

  override protected def fromRules(rules: Seq[Rule]): Css = CssExp(rules)

  override protected def className(s: Selector, string: String): Selector = SelectorClassExp(s, string)
  override protected def id(s: Selector, string: String): Selector = SelectorIDExp(s, string)
  override protected def attribute(s: Selector, attr: String, value: ValueExp): Selector = SelectorAttributeExp(s, attr, value)
  override protected def pseudoClass(s: Selector, string: String): Selector = SelectorPseudoClassExp(s, string)
  override protected def pseudoElement(s: Selector, string: String): Selector = SelectorPseudoElementExp(s, string)

  override protected def adjacent(s: Selector, selector: Selector): Selector = SelectorAdjacentExp(s, selector)
  override protected def general(s: Selector, selector: Selector): Selector = SelectorGeneralExp(s, selector)
  override protected def child(s: Selector, selector: Selector): Selector = SelectorChildExp(s, selector)
  override protected def descendant(s: Selector, selector: Selector): Selector = SelectorDescendantExp(s, selector)

  override protected def group(selectors: Seq[Selector]): Selector = SelectorListExp(selectors)
  override def tipe(string: String): Selector = SelectorTypeExp(string)
  override val All: Selector = SelectorAll()

  override protected def bindTo(s: Selector, declarations: Seq[Declaration]): Rule = RuleExp(s, declarations)

  override def prop(string: String): Property = PropertyExp(string)
  override protected def assign(p: Property, value: Value): Declaration = DeclarationExp(p, value)

  override def value(string: String): Value = ValueExp(string)

  override def compile(sheet: Css): String = sheet.compile()
  override def pretty(sheet: Css, spaces: Int): String = sheet.pretty(spaces)

}