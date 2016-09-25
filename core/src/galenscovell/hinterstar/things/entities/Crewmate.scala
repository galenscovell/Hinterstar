package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Touchable}
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.util._

import scala.collection.mutable


class Crewmate(var name: String, proficiencies: mutable.Map[String, Int], var assignmentName: String,
               var health: Int) extends Table {
  private val spriteWidth: Int = 48
  private val spriteHeight: Int = 48

  private var assignment: Tile = _

  private val sprite: Sprite = Resources.spCrewmate
  private val stats: CrewStats = new CrewStats(this)

  private var selected: Boolean = false
  private val path: mutable.Stack[Tile] = mutable.Stack()
  private var nextTile: Tile = _

  private var currentCoords: Vector2 = _
  private var nextCoords: Vector2 = _

  construct()



  private def construct(): Unit = {
    this.setFillParent(true)

    this.setTouchable(Touchable.enabled)
    this.addListener(new ClickListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean = {
        CrewOperations.selectCrewmate(getThisCrewmate)
        true
      }
    })
  }



  /********************
    *     Getters     *
    ********************/
  def getThisCrewmate: Crewmate = {
    this
  }

  override def getName: String = {
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

  def getStatsTable: CrewStats = {
    stats
  }

  def getActorCoordinates: Vector2 = {
    // This is confusing -- one would think to use getX() and getY(), but _don't_
    // Those return the location of the actor within the table, which we would then
    // attempt to transform to stage coordinates.
    // What we need to do is determine where a point is _within_ the current actor.
    // Vector2(0, 0) is the bottom left corner of the actor
    // Vector2(0, getHeight()) is the top left corner of the actor
    // We want the centerpoint of the tile
    this.localToStageCoordinates(new Vector2(
      (48 / 2) - 4,
      (48 / 2) - 4
    ))
  }

  def isSelected: Boolean = {
    selected
  }



  /********************
    *     Setters     *
    ********************/
  override def setName(n: String): Unit = {
    name = n
  }

  def updateProficiency(proficiency: String, value: Int): Unit = {
    proficiencies.updated(proficiency, proficiencies(proficiency) + value)
  }

  def setAssignment(t: Tile): Unit = {
    if (hasParent) {
      remove()
    }
    assignment = t
    assignment.addActor(this)
    currentCoords = assignment.getActorCoordinates
  }

  def updateHealth(value: Int): Unit = {
    health += value
    if (health > 100) {
      health = 100
    } else if (health < 0) {
      health = 0
    }
    stats.setHealthValue(health)
  }

  def setPath(newPath: Array[Tile]): Unit = {
    path.clear()
    for (t: Tile <- newPath) {
      path.push(t)
    }
  }



  /********************
    *    Updating     *
    ********************/
  def update(): Unit = {
    if (path.nonEmpty) {
      nextTile = path.pop()
      setAssignment(nextTile)
    }
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

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (isSelected) {
      batch.setColor(0.18f, 0.8f, 0.44f, 1.0f)
    }

    batch.draw(
      sprite,
      currentCoords.x + 12, currentCoords.y + 6,
      spriteWidth, spriteHeight
    )

    super.draw(batch, parentAlpha)

    if (isSelected) {
      batch.setColor(1, 1, 1, 1)
    }
  }

  def drawStats(delta: Float, batch: Batch): Unit = {
    stats.draw(batch, 1f)
  }
}
