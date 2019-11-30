package cpl.oo

trait Exp {
  def eval: Int
}

trait PrettyPrintable { def prettyPrint: String }

// Create a class 'Literal' that represents integer literals,
// it evaluates to the literal it represents. Look at the tests if you're
// confused.
class Literal(var i: Int) extends Exp {
  override def eval: Int = i
}

// Create class 'Addition'. It evaluates to an addition. Look at the test.
class Addition(var i: Exp, var j: Exp) extends Exp {
  override def eval: Int = i.eval + j.eval
}

class Negation(var e: Exp) extends Exp {
  override def eval: Int = -e.eval
}
