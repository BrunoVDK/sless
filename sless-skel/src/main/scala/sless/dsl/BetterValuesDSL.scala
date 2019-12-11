package sless.dsl

trait BetterValuesDSL extends ValueDSL {

  def inherit: Value
  def initial: Value

  type MarginWidth
  val margin: (MarginWidth*) => Value

  def auto: MarginWidth
  def in[T: Numeric](x: T): MarginWidth
  def mm[T: Numeric](x: T): MarginWidth
  def cm[T: Numeric](x: T): MarginWidth
  def px[T: Numeric](x: T): MarginWidth
  def pt[T: Numeric](x: T): MarginWidth
  def pc[T: Numeric](x: T): MarginWidth
  def em[T: Numeric](x: T): MarginWidth
  def ex[T: Numeric](x: T): MarginWidth
  def ch[T: Numeric](x: T): MarginWidth
  def rem[T: Numeric](x: T): MarginWidth
  def vw[T: Numeric](x: T): MarginWidth
  def vh[T: Numeric](x: T): MarginWidth
  def vmin[T: Numeric](x: T): MarginWidth
  def vmax[T: Numeric](x: T): MarginWidth
  def percent[T: Numeric](x: T): MarginWidth

  implicit class BetterValuesShortHand[T: Numeric](x: T) {
    def in: MarginWidth = BetterValuesDSL.this.in(x)
    def mm: MarginWidth = BetterValuesDSL.this.mm(x)
    def cm: MarginWidth = BetterValuesDSL.this.cm(x)
    def px: MarginWidth = BetterValuesDSL.this.px(x)
    def pt: MarginWidth = BetterValuesDSL.this.pt(x)
    def pc: MarginWidth = BetterValuesDSL.this.pc(x)
    def em: MarginWidth = BetterValuesDSL.this.em(x)
    def ex: MarginWidth = BetterValuesDSL.this.ex(x)
    def ch: MarginWidth = BetterValuesDSL.this.ch(x)
    def rem: MarginWidth = BetterValuesDSL.this.rem(x)
    def vw: MarginWidth = BetterValuesDSL.this.vw(x)
    def vh: MarginWidth = BetterValuesDSL.this.vh(x)
    def vmin: MarginWidth = BetterValuesDSL.this.vmin(x)
    def vmax: MarginWidth = BetterValuesDSL.this.vmax(x)
    def percent: MarginWidth = BetterValuesDSL.this.percent(x)
  }

}
