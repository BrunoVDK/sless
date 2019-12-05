package sless.ast

import sless.dsl._
import sless.ast.exp._
import sless.ast.exp.selector._

class Base extends PropertyDSL with SelectorDSL with ValueDSL with Compilable {

  override type Rule = RuleExp
  override type Css = CssExp
  override type Selector = SelectorExp
  override type Declaration = DeclarationExp
  override type Property = PropertyExp
  override type Value = ValueExp[_]

  override protected def fromRules(rules: Seq[Rule]): Css = CssExp(rules)

  override protected def className(s: Selector, string: String): Selector = SelectorAll()
  override protected def id(s: Selector, string: String): Selector = SelectorAll()
  override protected def attribute(s: Selector, attr: String, value: ValueExp[_]): Selector = SelectorAll()
  override protected def pseudoClass(s: Selector, string: String): Selector = SelectorAll()
  override protected def pseudoElement(s: Selector, string: String): Selector = SelectorAll()

  override protected def adjacent(s: Selector, selector: Selector): Selector = SelectorAll()
  override protected def general(s: Selector, selector: Selector): Selector = SelectorAll()
  override protected def child(s: Selector, selector: Selector): Selector = SelectorAll()
  override protected def descendant(s: Selector, selector: Selector): Selector = SelectorAll()

  override protected def group(selectors: Seq[Selector]): Selector = SelectorListExp(selectors)
  override def tipe(string: String): Selector = SelectorTypeExp(string)
  override val All: Selector = SelectorAll()

  override protected def bindTo(s: Selector, declarations: Seq[Declaration]): Rule = RuleExp(s, declarations)

  override def prop(string: String): Property = PropertyExp(string)
  override protected def assign(p: Property, value: Value): Declaration = DeclarationExp(p, value)

  override def value(string: String): Value = ValueExp[String](string)

  override def compile(sheet: Css): String = sheet.compile()
  override def pretty(sheet: Css, spaces: Int): String = sheet.pretty(spaces)

}