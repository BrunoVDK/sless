package cpl.fp

trait PrettyPrint[A] { def prettyPrint(a: A): String }
sealed trait Exp

object Exp extends PrettyPrint[Exp] {

  /**
    * Interpret 'exp' into an Int with a pattern match
    */
  def eval(exp: Exp): Int = exp match {
      case lit: Literal => lit.x
      case add: Addition => Exp.eval(add.e1) + Exp.eval(add.e2)
  }

  override def prettyPrint(a: Exp): String = a match {
      case lit: Literal => lit.x.toString
      case add: Addition => Exp.prettyPrint(add.e1) + " + " + Exp.prettyPrint(add.e2)
  }

}

// Create two case classes, literal and addition. A literal contains
// an integer it represents and addition contains two expressions.

case class Literal(x: Int) extends Exp
case class Addition(e1: Exp, e2: Exp) extends Exp