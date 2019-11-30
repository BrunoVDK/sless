package sless.ast

import sless.dsl.SelectorDSL

case class SelectorExp() extends SelectorDSL {
  override protected def className(s: SelectorExp.this.type, string: String): SelectorExp.this.type = ???

  override protected def id(s: SelectorExp.this.type, string: String): SelectorExp.this.type = ???

  override protected def attribute(s: SelectorExp.this.type, attr: String, value: SelectorExp.this.type): SelectorExp.this.type = ???

  override protected def pseudoClass(s: SelectorExp.this.type, string: String): SelectorExp.this.type = ???

  override protected def pseudoElement(s: SelectorExp.this.type, string: String): SelectorExp.this.type = ???

  /** -> s + selector { ... } */
  override protected def adjacent(s: SelectorExp.this.type, selector: SelectorExp.this.type): SelectorExp.this.type = ???

  /** -> s ~ selector { ... } */
  override protected def general(s: SelectorExp.this.type, selector: SelectorExp.this.type): SelectorExp.this.type = ???

  /** -> s > selector { ... } */
  override protected def child(s: SelectorExp.this.type, selector: SelectorExp.this.type): SelectorExp.this.type = ???

  /** -> s selector { ... } */
  override protected def descendant(s: SelectorExp.this.type, selector: SelectorExp.this.type): SelectorExp.this.type = ???

  override protected def group(selectors: Seq[SelectorExp.this.type]): SelectorExp.this.type = ???

  override def tipe(string: String): SelectorExp.this.type = ???

  override val All: SelectorExp.this.type = _

  override protected def bindTo(s: SelectorExp.this.type, declarations: Seq[SelectorExp.this.type]): SelectorExp.this.type = ???

  override type Rule = this.type
  override type Css = this.type
  override type Selector = this.type
  override type Declaration = this.type
  override type Property = this.type
  override type Value = this.type

  override protected def fromRules(rules: Seq[SelectorExp.this.type]): SelectorExp.this.type = ???
}
