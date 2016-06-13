package galenscovell.hinterstar.things.ships

import galenscovell.hinterstar.things.parts.Part

import scala.collection.mutable.{ArrayBuffer, Map}


class Ship(n: String, desc: String, sp: Map[String, Array[Part]]) {
  private val name: String = n
  private val description: String = desc
  private val startingParts: Map[String, Array[Part]] = sp
  private val parts: ArrayBuffer[Part] = ArrayBuffer()


  def getName: String = {
    name
  }

  def getDescription: String = {
    description
  }

  def getStartingParts: Map[String, Array[Part]] = {
    startingParts
  }

  def getParts: ArrayBuffer[Part] = {
    parts
  }

  def getStat(stat: String): Int = {
    var value: Int = 0
    for (p: Part <- parts) {
      if (p.getType == stat) {
        value += p.getStat
      }
    }
    value
  }
}
