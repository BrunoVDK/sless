package sless.ast

import sless.dsl.ValueDSL

case class ValueExp(string: String) {
  def value(string: String) = ValueExp(string)
}
