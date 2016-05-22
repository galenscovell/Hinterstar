package galenscovell.hinterstar.things.ships

import galenscovell.hinterstar.things.parts.Part

import scala.collection.mutable.ArrayBuffer


class Ship0 extends Ship {
  private val name: String = "Ship-0"
  private val installPoints: Map[String, Int] = Map[String, Int]()
  private val parts: ArrayBuffer[Part] = ArrayBuffer()


  override def getName: String = {
    name
  }

  override def getInstallPoints: Map[String, Int] = {
    installPoints
  }

  override def getParts: ArrayBuffer[Part] = {
    parts
  }

  override def getStatFromParts(stat: String): Int = {
    var value: Int = 0
    for (p: Part <- parts) {
      if (p.getType == stat) {
        value += p.getStat
      }
    }
    value
  }
}
