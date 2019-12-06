package sless.ast

import sless.ast.exp.CssExp
import sless.dsl.LintDSL

class BaseLint extends Base with LintDSL {

  override def removeEmptyRules(css: CssExp): (Boolean, CssExp) = (css.hasEmptyRule(), css.removeEmptyRules())

  override def aggregateMargins(css: CssExp): (Boolean, CssExp) = ???

  override def limitFloats(css: CssExp, n: Integer): Boolean = ???

}
