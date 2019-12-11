package sless.ast.exp

/**
  * A unit represents a unit for a measure.
  */
abstract sealed class Unit(symbol: String) { override def toString: String = symbol }

case object Inches extends Unit("in")
case object Millimeters extends Unit("mm")
case object Centimeters extends Unit("cm")
case object Pixels extends Unit("px")
case object Points extends Unit("pt")
case object Picas extends Unit("pc")
case object Ems extends Unit("em")
case object Exs extends Unit("ex")
case object Chs extends Unit("ch")
case object Rems extends Unit("rem")
case object Vws extends Unit("vw")
case object Vhs extends Unit("vh")
case object Vmin extends Unit("vmin")
case object Vmax extends Unit("vmax")
case object Percent extends Unit("%")
