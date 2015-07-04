package example

import scala.scalajs.js
import org.scalajs.dom
import dom.document
import shared.SharedMessages
import scala.scalajs.js.annotation.JSExport

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
    appendPar(document.body, "hello world")
  }

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }

  @JSExport
  def addClickedMessage(): Unit = {
    appendPar(document.body, "You clicked the button!")
  }
}
