package galenscovell.hinterstar.things.ships

import galenscovell.hinterstar.things.parts.Weapon


/**
  * A Ship is the container for the Player's current loadout.
  * Ship's have:
  *     a name
  *     a description
  *     a Map of part ArrayBuffers
  */
class Ship(n: String, desc: String, sp: Array[Weapon]) {
  private val name: String = n
  private val description: String = desc
  private val parts: Array[Weapon] = sp


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
  def getParts: Array[Weapon] = {
    parts
  }
}
