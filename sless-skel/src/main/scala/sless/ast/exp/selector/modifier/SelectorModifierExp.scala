package sless.ast.exp.selector.modifier

import sless.ast.exp.selector.SelectorExp

abstract class SelectorModifierExp(s: SelectorExp, suffix: String = "")(implicit extensions: Seq[SelectorExp] = List())
  extends SelectorExp(extensions) {
  override def compile(): String = s.compile() + suffix
  override def pretty(spaces: Int): String = s.pretty(spaces) + suffix
  override def grounded: Boolean = s.grounded
  override def extensionPairs: Seq[(SelectorExp, SelectorExp)] = extensions.map((this,_)) ++ s.extensionPairs
}