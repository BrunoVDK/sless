package sless.ast.exp.measure

sealed trait Unit {
  val symbol: String = ""
  override def toString: String = symbol
}

case object UnitIn extends Unit { override val symbol: String = "in" }
case object UnitMm extends Unit { override val symbol: String = "mm" }
case object UnitCm extends Unit { override val symbol: String = "cm" }
case object UnitPx extends Unit { override val symbol: String = "px" }
case object UnitPt extends Unit { override val symbol: String = "pt" }
case object UnitPc extends Unit { override val symbol: String = "pc" }

case object UnitEm extends Unit { override val symbol: String = "em" }
case object UnitEx extends Unit { override val symbol: String = "ex" }
case object UnitCh extends Unit { override val symbol: String = "ch" }
case object UnitRem extends Unit { override val symbol: String = "rem" }
case object UnitVw extends Unit { override val symbol: String = "vw" }
case object UnitVh extends Unit { override val symbol: String = "vh" }
case object UnitVmin extends Unit { override val symbol: String = "vmin" }
case object UnitVmax extends Unit { override val symbol: String = "vmax" }
case object UnitPercent extends Unit { override val symbol: String = "%" }

object Unit {
  def unit(symbol: String): Unit = symbol match {
    case "in" => UnitIn
    case "mm" => UnitMm
    case "cm" => UnitCm
    case "px" => UnitPx
    case "pt" => UnitPt
    case "pc" => UnitPc
    case "em" => UnitEm
    case "ex" => UnitEx
    case "ch" => UnitCh
    case "rem" => UnitRem
    case "vw" => UnitVw
    case "vh" => UnitVh
    case "vmin" => UnitVmin
    case "vmax" => UnitVmax
    case "%" => UnitPercent
    case _ => null
  }
}