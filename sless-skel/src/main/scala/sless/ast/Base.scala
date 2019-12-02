package sless.ast

import sless.ast.exp.{CssExp, DeclarationExp, PropertyExp, RuleExp, SelectorExp, ValueExp}
import sless.dsl._

/*
  Deals with construction of ASTs by providing factory methods.
 */
class Base extends PropertyDSL with SelectorDSL with ValueDSL with Compilable {

  override type Rule = RuleExp
  override type Css = CssExp
  override type Selector = SelectorExp
  override type Declaration = DeclarationExp
  override type Property = PropertyExp
  override type Value = ValueExp

  override protected def fromRules(rules: Seq[RuleExp]): CssExp = CssExp(rules)

  // Selector

  override protected def className(s: SelectorExp, string: String): SelectorExp = ???

  override protected def id(s: SelectorExp, string: String): SelectorExp = ???

  override protected def attribute(s: SelectorExp, attr: String, value: ValueExp): SelectorExp = ???

  override protected def pseudoClass(s: SelectorExp, string: String): SelectorExp = ???

  override protected def pseudoElement(s: SelectorExp, string: String): SelectorExp = ???

  override protected def adjacent(s: SelectorExp, selector: SelectorExp): SelectorExp = ???

  override protected def general(s: SelectorExp, selector: SelectorExp): SelectorExp = ???

  override protected def child(s: SelectorExp, selector: SelectorExp): SelectorExp = ???

  override protected def descendant(s: SelectorExp, selector: SelectorExp): SelectorExp = ???

  override protected def group(selectors: Seq[SelectorExp]): SelectorExp = ???

  override def tipe(string: String): SelectorExp = ???

  override val All: SelectorExp = _

  override protected def bindTo(s: SelectorExp, declarations: Seq[DeclarationExp]): RuleExp = RuleExp(s, declarations)

  // Property

  override def prop(string: String): PropertyExp = PropertyExp(string)

  override protected def assign(p: PropertyExp, value: ValueExp): DeclarationExp = DeclarationExp(p, value)

  // Value

  override def value(string: String): ValueExp = ValueExp(string)

  // Compile

  override def compile(sheet: CssExp): String = sheet.compile()

  override def pretty(sheet: CssExp, spaces: Int): String = sheet.pretty(spaces)

}