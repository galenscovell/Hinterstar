package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.{Group, InputEvent}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.util.{CrewOperations, Resources}

import scala.collection.mutable


class Crewmate(var name: String, proficiencies: mutable.Map[String, Int], var assignmentName: String, var health: Int) {
  private var assignment: Tile = _
  private var additionalDetail: String = _
  private var weapon: Weapon = _

  private val sprite: Sprite = Resources.spCrewmate
  private val healthBar: ProgressBar = new ProgressBar(0, 100, 1, true, Resources.crewHealthBarStyle)
  healthBar.setValue(100)
  private val crewInnerTable: Table = new Table
  private val crewBox: Group = new Group

  constructBox()
//  crewBox.setDebug(true)



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

  def getAssignment: Tile = {
    assignment
  }

  def getAdditionalDetail: String = {
    if (additionalDetail != null && additionalDetail != "") {
      additionalDetail
    } else {
      "..."
    }
  }

  def getWeapon: Weapon = {
    weapon
  }

  def getAssignedSubsystemName: String = {
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

  def setAssignment(subsystem: Tile): Unit = {
    assignment = subsystem
  }

  def setAdditionalDetail(detail: String): Unit = {
    additionalDetail = detail
  }

  def setWeapon(w: Weapon): Unit = {
    weapon = w
    additionalDetail = w.getName
  }

  def removeWeapon(): Unit = {
    weapon = null
    additionalDetail = ""
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



  /***********************
    *    UI Component    *
    ***********************/
  def getCrewBox: Group = {
    constructBox()
    crewBox
  }

  def highlightTable(): Unit = {
    crewInnerTable.setBackground(Resources.npTest2)
  }

  def unhighlightTable(): Unit = {
    crewInnerTable.setBackground(Resources.npTest4)
  }

  private def constructBox(): Unit = {
    if (crewBox != null) {
      crewBox.clear()
      crewInnerTable.clear()
    }

    crewInnerTable.setBackground(Resources.npTest4)
    crewInnerTable.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        CrewOperations.selectCrewmate(getThisCrewmate)
      }
    })

    val leftTable: Table = new Table

    val spriteTable: Table = new Table
    spriteTable.setBackground(Resources.npTest3)
    val sprite: Image = new Image(getSprite)
    spriteTable.add(sprite).expand.fillX

    leftTable.add(spriteTable).expand.fill.width(32).height(32).left.top
    leftTable.row
    leftTable.add(healthBar).expand.fill.width(32).height(28).left

    val rightTable: Table = new Table

    val nameLabel: Label = new Label(name, Resources.labelTinyStyle)
    nameLabel.setAlignment(Align.center, Align.left)

    val additionalLabel: Label = new Label(getAdditionalDetail, Resources.labelTinyStyle)
    additionalLabel.setAlignment(Align.center, Align.left)

    rightTable.add(nameLabel).expand.fill.width(68)
    rightTable.row
    rightTable.add(additionalLabel).expand.fill.width(68)

    crewInnerTable.add(leftTable).expand.width(32).height(60).left
    crewInnerTable.add(rightTable).expand.fill.width(80).height(60)

    crewBox.addActor(crewInnerTable)
    crewInnerTable.setSize(112, 60)
    crewInnerTable.setPosition(0, 0)

    if (assignment != null) {
      val assignmentIcon: Image = new Image(assignment.getIcon)
      crewBox.addActor(assignmentIcon)
      assignmentIcon.setSize(32, 32)
      assignmentIcon.setPosition(94, 40)
    }
  }
}
