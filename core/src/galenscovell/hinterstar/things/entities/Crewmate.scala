package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.util.Resources

import scala.collection.mutable


class Crewmate(var name: String, proficiencies: mutable.Map[String, Int], var assignmentName: String,
               var health: Int) {
  private var tileX: Int = 0
  private var tileY: Int = 0
  private var assignment: Tile = _

  private val sprite: Sprite = Resources.spCrewmate
  private val healthBar: ProgressBar = new ProgressBar(0, 100, 1, true, Resources.crewHealthBarStyle)
  healthBar.setValue(100)

  private var selected: Boolean = false



  /********************
    *     Getters     *
    ********************/
  def getThisCrewmate: Crewmate = {
    this
  }

  def getName: String = {
    name
  }

  def getAllProficiencies: mutable.Map[String, Int] = {
    proficiencies
  }

  def getAProficiency(proficiency: String): Int = {
    proficiencies(proficiency)
  }

  def getTileX: Int = {
    tileX
  }

  def getTileY: Int = {
    tileY
  }

  def getAssignment: Tile = {
    assignment
  }

  def getAssignmentName: String = {
    if (assignment != null) {
      assignmentName = assignment.getName
    }
    assignmentName
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



  /********************
    *     Setters     *
    ********************/
  def setName(n: String): Unit = {
    name = n
  }

  def updateProficiency(proficiency: String, value: Int): Unit = {
    proficiencies.updated(proficiency, proficiencies(proficiency) + value)
  }

  def setAssignment(t: Tile): Unit = {
    assignment = t
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



  /********************
    *    Rendering    *
    ********************/
  def highlight(): Unit = {
    selected = true
  }

  def unhighlight(): Unit = {
    selected = false
  }

  def draw(delta: Float, batch: Batch): Unit = {
    batch.draw(sprite, assignment.getX + 52, assignment.getY + 52, 48, 48)
  }
}
