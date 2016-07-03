package galenscovell.hinterstar.things.ships

import galenscovell.hinterstar.things.parts.Part

import scala.collection.mutable.{ArrayBuffer, Map}


/**
  * A Ship is the container for the Player's current loadout.
  * Ship's have:
  *     a name
  *     a description
  *     a Map of part ArrayBuffers
  */
class Ship(n: String, desc: String, sp: Map[String, ArrayBuffer[Part]]) {
  private val name: String = n
  private val description: String = desc
  private val parts: Map[String, ArrayBuffer[Part]] = sp


  /**
    * Return the Ship's name.
    */
  def getName: String = {
    name
  }

  /**
    * Return the Ship's description.
    */
  def getDescription: String = {
    description
  }

  /**
    * Return the Ship's current Parts.
    */
  def getParts: Map[String, ArrayBuffer[Part]] = {
    parts
  }

  /**
    * Return the Ship's current stat based on its current Parts.
    */
  def getStat(stat: String): Int = {
    var value: Int = 0
    val partArray: ArrayBuffer[Part] = parts.get(stat).get

    for (p: Part <- partArray) {
      value += p.getStat
    }
    value
  }
}
