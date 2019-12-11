package sless.dsl

trait SelectorPseudoDSL extends SelectorDSL {

  type SelectorPseudo <: Selector

  override def pseudoClass(s: Selector, string: String): SelectorPseudo
  override def pseudoElement(s: Selector, string: String): SelectorPseudo

  protected def applyAttributes(s: SelectorPseudo, attr: Seq[String]): SelectorPseudo

  implicit class SelectorShorthand(s: SelectorPseudo) {
    def apply(attributes: String*): Selector = applyAttributes(s, attributes)
  }

}
