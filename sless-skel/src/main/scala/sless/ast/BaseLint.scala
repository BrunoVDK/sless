package sless.ast

import sless.dsl.LintDSL

class BaseLint extends Base with LintDSL {

  override def removeEmptyRules(css: Css): (Boolean, Css) = css.withoutEmptyRules

  override def aggregateMargins(css: Css): (Boolean, Css) = css.aggregate(prop("margin-top"),
    prop("margin-left"),
    prop("margin-bottom"),
    prop("margin-right"))

  override def limitFloats(css: Css, n: Integer): Boolean = css.occurrences(prop("float")) > n

}
