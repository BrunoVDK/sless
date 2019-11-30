package sless.ast

import sless.dsl._

object Base extends BaseDSL with SelectorDSL {

  override type Rule = RuleExp
  override type Css = CssExp
  override type Selector = SelectorExp
  override type Declaration = DeclarationExp
  override type Property = PropertyExp
  override type Value = ValueExp

  override protected def fromRules(rules: Seq[RuleExp]): CssExp = {return null}

  implicit val sel = SelectorExp()

}
