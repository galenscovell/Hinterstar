package galenscovell.hinterstar.things.ships

import galenscovell.hinterstar.things.parts.Part

import scala.collection.mutable.ArrayBuffer


trait Ship {
  // Ship chassis name
  def getName: String

  // Each ship chassis has a certain amount of available part installation points
  // eg. 'Engine install point' -> 2, ship can install up to 2 engine parts
  //     'Storage install point' -> 4, ship can install up to 4 storage parts
  def getInstallPoints: Map[String, Int]

  // Parts are not all necessarily active at a given time
  // A part can only be activated if there is enough energy to support it
  // Player can turn parts on and off to divert energy where it's most important for them
  // Parts require maintenance and can be upgraded
  def getParts: ArrayBuffer[Part]

  // Attack is dependent on installed combat parts
  def getAttack: Int

  // Storage is dependent on installed storage parts
  // Storage holds resources
  // As storage is filled, ship weight goes up
  def getStorage: Int

  // Energy is dependent on installed generator
  // Most parts require energy
  def getEnergy: Int

  // Hull is dependent on installed armor plating parts
  // Armor parts tend to be heavy and require no energy,
  //  though there are hybrid parts that utilize energy rather than density
  def getHull: Int

  // Shield is dependent on installed shield parts
  def getShield: Int

  // Speed is dependent on strength of intalled engine minus weight
  def getSpeed: Int
}
