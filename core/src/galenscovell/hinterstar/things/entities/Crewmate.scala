package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.util.{CrewOperations, Resources}

import scala.collection.mutable


class Crewmate(n: String, p: mutable.Map[String, Int], a: String, h: Int) {
  private var name: String = n
  private val proficiencies: mutable.Map[String, Int] = p
  private var assignment: Tile = _
  private var health: Int = h

  private val sprite: Sprite = Resources.spCrewmate
  private val healthBar: ProgressBar = new ProgressBar(0, 100, 1, false, Resources.healthBarStyle)
  healthBar.setValue(100)
  private var crewTable: Table = constructTable


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

  def getAssignment: Tile = {
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

  def getCrewTable(refresh: Boolean): Table = {
    crewTable = constructTable
    crewTable
  }

  def highlightTable(): Unit = {
    crewTable.setBackground(Resources.npTest2)
  }

  def unhighlightTable(): Unit = {
    crewTable.setBackground(Resources.npTest4)
  }



  def setName(n: String): Unit = {
    name = n
  }

  def updateProficiency(proficiency: String, value: Int): Unit = {
    proficiencies.updated(proficiency, proficiencies(proficiency) + value)
  }

  def setAssignment(ss: Tile): Unit = {
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



  private def constructTable: Table = {
    val table: Table = new Table
    table.setBackground(Resources.npTest4)

    table.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        CrewOperations.selectCrewmate(getThisCrewmate)
      }
    })

    val crewmateDetail: Table = new Table

    val spriteTable: Table = new Table
    spriteTable.setBackground(Resources.npTest3)
    val sprite: Image = new Image(getSprite)
    spriteTable.add(sprite).expand.fillX

    val detailTop: Table = new Table
    val nameLabel: Label = new Label(name, Resources.labelTinyStyle)
    nameLabel.setAlignment(Align.center)

    val healthBarTable: Table = new Table
    healthBarTable.add(healthBar).width(80).height(16)

    detailTop.add(nameLabel).expand.fill.height(40)
    detailTop.row
    detailTop.add(healthBarTable).expand.fill.center

    crewmateDetail.add(spriteTable).expand.fill.width(40).height(40).left.top
    crewmateDetail.add(detailTop).expand.fill.width(88).height(40).top

    val assignmentLabel: Label = new Label(getAssignedSubsystemName, Resources.labelTinyStyle)
    assignmentLabel.setAlignment(Align.center)

    table.add(crewmateDetail).expand.fill.height(40)
    table.row
    table.add(assignmentLabel).expand.fill.height(20)

    table
  }
}
