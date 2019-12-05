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

  override protected def fromRules(rules: Seq[RuleExp]): CssExp = CssExp(rules)

  override protected def className(s: SelectorExp, string: String): SelectorExp = ???
  override protected def id(s: SelectorExp, string: String): SelectorExp = ???
  override protected def attribute(s: SelectorExp, attr: String, value: ValueExp[_]): SelectorExp = ???
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

  override def prop(string: String): PropertyExp = PropertyExp(string)
  override protected def assign(p: PropertyExp, value: ValueExp[_]): DeclarationExp = DeclarationExp(p, value)

  override def value(string: String): ValueExp[_] = ValueExp[String](string)

  override def compile(sheet: CssExp): String = sheet.compile()
  override def pretty(sheet: CssExp, spaces: Int): String = sheet.pretty(spaces)

}