package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import galenscovell.hinterstar.generation.interior.Subsystem
import galenscovell.hinterstar.util.Resources

import scala.collection.mutable


class Crewmate(n: String, p: mutable.Map[String, Int], a: String, h: Int) {
  private var name: String = n
  private val proficiencies: mutable.Map[String, Int] = p
  private var assignment: Subsystem = _
  private var health: Int = h

  private val sprite: Sprite = Resources.spCrewmate
  private val healthBar: ProgressBar = new ProgressBar(0, 100, 1, false, Resources.healthBarStyle)
  healthBar.setValue(100)


  def getName: String = {
    name
  }

  def getAllProficiencies: mutable.Map[String, Int] = {
    proficiencies
  }

  def getAProficiency(proficiency: String): Int = {
    proficiencies(proficiency)
  }

  def getAssignment: Subsystem = {
    assignment
  }

  def getAssignedSubsystemName: String = {
    if (assignment == null) {
      "None"
    } else {
      assignment.getName
    }
  }

  def getHealth: Int = {
    health
  }

  def getSprite: Sprite = {
    sprite
  }

  def getHealthBar: ProgressBar = {
    healthBar
  }



  def setName(n: String): Unit = {
    name = n
  }

  def updateProficiency(proficiency: String, value: Int): Unit = {
    proficiencies.updated(proficiency, proficiencies(proficiency) + value)
  }

  def setAssignment(ss: Subsystem): Unit = {
    assignment = ss
  }

  def updateHealth(value: Int): Unit = {
    health += value
    if (health > 100) {
      health = 100
    } else if (health < 0) {
      health = 0
    }
    healthBar.setValue(health)
  }
}
