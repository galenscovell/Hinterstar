package galenscovell.hinterstar.things.ships

import galenscovell.hinterstar.things.parts.Weapon


class Ship(n: String, desc: String, w: Array[Weapon], s: Array[String]) {
  private val name: String = n
  private val description: String = desc
  private val subsystems: Array[String] = s
  private var weapons: Array[Weapon] = w


  def getName: String = {
    name
  }

  def getDescription: String = {
    description
  }

  def getWeapons: Array[Weapon] = {
    weapons
  }

  def setWeapons(w: Array[Weapon]): Unit = {
    weapons = w
  }

  def getSubsystems: Array[String] = {
    subsystems
  }
}
