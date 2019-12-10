package sless.dsl

trait BetterValuesDSL extends ValueDSL {

  def inherit: Value
  def initial: Value

  type MarginWidth
  val margin: (MarginWidth*) => Value
  def auto: MarginWidth
  def length[T: Numeric](x: T, symbol: String): MarginWidth

  implicit class BetterValuesShortHand[T: Numeric](x: T) {
    def in: MarginWidth = length(x, "in")
    def mm: MarginWidth = length(x, "mm")
    def cm: MarginWidth = length(x, "cm")
    def px: MarginWidth = length(x, "px")
    def pt: MarginWidth = length(x, "pt")
    def pc: MarginWidth = length(x, "pc")
    def em: MarginWidth = length(x, "em")
    def ex: MarginWidth = length(x, "ex")
    def ch: MarginWidth = length(x, "ch")
    def rem: MarginWidth = length(x, "rem")
    def vw: MarginWidth = length(x, "vw")
    def vh: MarginWidth = length(x, "vh")
    def vmin: MarginWidth = length(x, "vmin")
    def vmax: MarginWidth = length(x, "vmax")
    def percent: MarginWidth = length(x, "%")
  }

}
