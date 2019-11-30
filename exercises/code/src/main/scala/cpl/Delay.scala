package cpl

/**
  * Implement Delay:
  *
  * - add the constructor arguments that you require
  * - implement map and flatMap
  * - implement eval, note that computation must be delayed AND cached,
  * experiment with different field declarations (val, def, lazy val)
  */
class Delay[A](function: () => A) {
  lazy val eval : A = function()
  def flatMap[B](f: A => Delay[B]): Delay[B] = new Delay(() => f(eval).eval)
  def map[B](f: A => B): Delay[B] = new Delay(() => f(eval))
}

object Delay {
  def apply[A](constant: A): Delay[A] = Delay.fromFunction(() => constant)
  def fromFunction[A](f: () => A): Delay[A] = new Delay(f)
}
