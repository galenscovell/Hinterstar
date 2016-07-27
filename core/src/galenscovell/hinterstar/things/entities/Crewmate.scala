package galenscovell.hinterstar.things.entities

import scala.collection.mutable


class Crewmate(n: String, p: mutable.Map[String, Int], a: String, h: Int) {
  private var name: String = n
  private val proficiencies: mutable.Map[String, Int] = p
  private var assignment: String = a
  private var health: Int = h


  def getName: String = {
    name
  }

  def getAllProficiencies: mutable.Map[String, Int] = {
    proficiencies
  }

  def getAProficiency(proficiency: String): Int = {
    proficiencies(proficiency)
  }

  def getAssignment: String = {
    assignment
  }

  def getHealth: Int = {
    health
  }



  def setName(n: String): Unit = {
    name = n
  }

  def updateProficiency(proficiency: String, value: Int): Unit = {
    proficiencies.updated(proficiency, proficiencies(proficiency) + value)
  }

  def setAssignment(a: String): Unit = {
    assignment = a
  }

  def updateHealth(value: Int): Unit = {
    health += value
  }
}
