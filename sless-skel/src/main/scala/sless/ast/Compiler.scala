package sless.ast

import sless.dsl.Compilable

class Compiler extends Compilable {
  override def compile(sheet: Compiler.this.Css): String = ???
  override def pretty(sheet: Compiler.this.Css, spaces: Int): String = ???
}
