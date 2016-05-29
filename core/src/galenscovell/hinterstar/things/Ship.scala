package galenscovell.hinterstar.things

import galenscovell.hinterstar.things.parts.Part

import scala.collection.mutable.{ArrayBuffer, Map}


class Ship(n: String, desc: String, hardpoints: Map[String, Int]) {
  private val name: String = n
  private val description: String = desc
  private val installPoints: Map[String, Int] = hardpoints
  private val parts: ArrayBuffer[Part] = ArrayBuffer()


  def getName: String = {
    name
  }

  def getDescription: String = {
    description
  }

  def getInstallPoints: Map[String, Int] = {
    installPoints
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
