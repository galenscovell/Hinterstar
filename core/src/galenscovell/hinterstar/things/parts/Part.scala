package galenscovell.hinterstar.things.parts


/**
  * Parts are used to alter Player stats and are a primary point of customization.
  * Part's have:
  *     a name
  *     a description
  *     a partType
  *     a stat value
  *     a requiredEnergy amount in order to be activated and used
  *     a health amount (when this hits 30, the part stat is lowered, 0 the part breaks)
  *     an active setting (true when currently activated)
  */
class Part(n: String, t: String, desc: String, s: Int, re: Int) {
  private val name: String = n
  private val partType: String = t
  private val description: String = desc
  private val stat: Int = s
  private val requiredEnergy: Int = re

  private var health: Int = 100
  private var active: Boolean = false


  /**
    * Return this Part's name.
    */
  def getName: String = {
    name
  }

  /**
    * Return this Part's type.
    */
  def getType: String = {
    partType
  }

  /**
    * Return this Part's description.
    */
  def getDescription: String = {
    description
  }

  /**
    * Return this Part's stat value.
    */
  def getStat: Int = {
    stat
  }

  /**
    * Return this Part's current health.
    */
  def getHealth: Int = {
    health
  }

  /**
    * Return the energy required to activate this Part.
    */
  def getEnergyRequired: Int = {
    requiredEnergy
  }




  /**
    * Repair the damage to this Part, increasing its health.
    */
  def repair: Boolean = {
    // TODO: Not yet implemented
    true
  }

  /**
    * Return true if this Part is currently activated.
    */
  def isActive: Boolean = {
    // TODO: Not yet implemented
    active
  }

  /**
    * Set Part to active and return true if this Part is able to be activated (enough energy is available).
    */
  def activate: Boolean = {
    // TODO: Not yet implemented
    active
  }

  /**
    * Set this Part as inactive.
    */
  def deactivate(): Unit = {
    // TODO: Not yet implemented
    active = false
  }
}
