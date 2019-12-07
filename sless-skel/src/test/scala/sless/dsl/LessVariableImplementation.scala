package sless.dsl

import sless.ast.Base

object LessVariableImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with Compilable
  val dsl: DSL = Base

  import LessVariableImplementation.dsl._

  /**
    * Create a Css Declaration for a background-color in the given color
    * -> background-color: $color;
    * @param color
    * @return
    */
  def coloredBg(color: String): dsl.Declaration = prop("background-color") := value(color)

  /**
    * Create a css sheet that colors an element with the given id in red
    * -> *#id { background-color: red; }
    * ! Use coloredBg in your implementation
    * @param id
    * @return
    */
  def colorNamedRed(id: String): dsl.Css = css((All ## id) {prop("background-color") := value("red")})

  /**
    * Create a rule for the given element type that has an aspect ratio of 2/1
    * -> elementType { height: $height; width: $height * 2; }
    * @param height
    * @return
    */
  def doubledWidth(elementType: String, height: Int): dsl.Rule = tipe(elementType) (
      prop("height") := value(height.toString),
      prop("width") := value((height * 2).toString)
  )

}
