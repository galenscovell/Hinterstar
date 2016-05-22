package galenscovell.hinterstar.things.parts


trait Part {
  // Part name
  def getName: String

  // Get part type
  def getType: String

  // Get part stats
  def getStat: Int

  // Return current health of part
  // When health is low, part has chance of malfunctioning
  // When health is zero, part breaks
  def getHealth: Int

  // Restore lost health for part
  // Depending on profession of assigned teammate, this may fail
  def repair: Boolean

  // Return if part is currently activated
  def isActive: Boolean

  // Activate part if there is enough available energy
  def activate: Boolean

  // Find required energy to activate part
  def getEnergyRequired: Int
}
