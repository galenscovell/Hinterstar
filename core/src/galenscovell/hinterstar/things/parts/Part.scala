package galenscovell.hinterstar.things.parts


class Part(n: String, t: String, desc: String, s: Int, re: Int, c: Int) {
  private val name: String = n
  private val partType: String = t
  private val description: String = desc
  private val stat: Int = s
  private val requiredEnergy: Int = re
  private val cost: Int = c

  private var health: Int = 100
  private var active: Boolean = false


  def getName: String = {
    name
  }

  def getType: String = {
    partType
  }

  def getDescription: String = {
    description
  }

  def getStat: Int = {
    stat
  }

  def getCost: Int = {
    cost
  }

  def getHealth: Int = {
    health
  }

  def getEnergyRequired: Int = {
    requiredEnergy
  }

  def repair: Boolean = {
    // TODO: Not yet implemented
    true
  }

  def isActive: Boolean = {
    // TODO: Not yet implemented
    active
  }

  def activate: Boolean = {
    // TODO: Not yet implemented
    active
  }

  def deactivate(): Unit = {
    // TODO: Not yet implemented
    active = false
  }
}
