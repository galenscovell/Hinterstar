package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Group, InputEvent}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.util.{CrewOperations, Resources}

import scala.collection.mutable


class Crewmate(var name: String, proficiencies: mutable.Map[String, Int], var assignmentName: String, var health: Int) {
  private var assignment: Tile = _
  private var weapon: Weapon = _

  private val sprite: Sprite = Resources.spCrewmate
  private val healthBar: ProgressBar = new ProgressBar(0, 100, 1, true, Resources.crewHealthBarStyle)
  healthBar.setValue(100)
  private val crewInnerTable: Table = new Table
  private val crewBox: Group = new Group
  private val assignmentIconTable: Table = new Table
  private val detailLabel: Label = new Label("...", Resources.labelTinyStyle)

  private val flag: CrewmateFlag = new CrewmateFlag(name)

  constructBox()



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

  def getFlag: CrewmateFlag = {
    flag
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

  def setWeapon(w: Weapon): Unit = {
    weapon = w
    setDetail(w.getName)
  }

  def removeWeapon(): Unit = {
    weapon = null
    setDetail("...")
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
    crewInnerTable.setBackground(Resources.npGray)
  }

  def unhighlightTable(): Unit = {
    crewInnerTable.setBackground(Resources.npDarkGray)
  }

  def setAssignmentIcon(): Unit = {
    var assignmentIcon: Image = null
    assignmentIconTable.clear()

    if (assignment != null && !flag.hasPath) {
      assignmentIcon = new Image(assignment.getIcon)

      if (assignment.isWeaponSubsystem) {
        assignmentIconTable.addListener(new ClickListener() {
          override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
            CrewOperations.showWeaponSelect(getThisCrewmate)
          }
        })
        assignmentIcon.setColor(0.7f, 0.2f, 0.2f, 1)
      }
    } else {
      assignmentIcon = new Image(Resources.spMovementIcon)
    }

    assignmentIconTable.add(assignmentIcon)
  }

  def getDetail: String = {
    detailLabel.getText.toString
  }

  private def setDetail(detail: String): Unit = {
    detailLabel.setText(detail)
  }

  private def constructBox(): Unit = {
    if (crewBox != null) {
      crewBox.clear()
      crewInnerTable.clear()
    }

    crewInnerTable.setBackground(Resources.npDarkGray)
    crewInnerTable.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        CrewOperations.selectCrewmate(getThisCrewmate)
      }
    })

    val leftTable: Table = new Table

    val spriteTable: Table = new Table
    spriteTable.setBackground(Resources.npBlue)
    val sprite: Image = new Image(getSprite)
    spriteTable.add(sprite).expand.fillX

    leftTable.add(spriteTable).expand.fill.width(32).height(32).left.top
    leftTable.row
    leftTable.add(healthBar).expand.fill.width(32).height(28).left

    val rightTable: Table = new Table

    val nameLabel: Label = new Label(name, Resources.labelTinyStyle)
    nameLabel.setAlignment(Align.center, Align.left)

    detailLabel.setAlignment(Align.center, Align.left)

    rightTable.add(nameLabel).expand.fill.width(80)
    rightTable.row
    rightTable.add(detailLabel).expand.fill.width(80)

    crewInnerTable.add(leftTable).expand.width(32).height(60).left
    crewInnerTable.add(rightTable).expand.fill.width(88).height(60)

    crewBox.addActor(crewInnerTable)
    crewInnerTable.setSize(120, 60)
    crewInnerTable.setPosition(0, 0)

    crewBox.addActor(assignmentIconTable)
    assignmentIconTable.setSize(32, 32)
    assignmentIconTable.setPosition(102, 40)
  }
}
