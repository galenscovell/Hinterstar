package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent}
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util._


class Tile(x: Int, y: Int, size: Int, height: Int, ss: String, hasWeapon: Boolean, forPlayerShip: Boolean) extends Actor {
  private val tx: Int = x
  private val ty: Int = y
  private val tileSize: Int = size
  private val overlayHeight: Int = height
  private val name: String = ss
  private val weaponSystem: Boolean = hasWeapon
  private val isPlayerSubsystem: Boolean = forPlayerShip

  private var frames: Int = 100
  private var glowing: Boolean = true

  private val maxOccupancy: Int = setMaxOccupancy
  private var assignedCrewmates: Int = 0

  private val infoDisplay: SubsystemInfo = constructInfo

  initialize()


  private def initialize(): Unit = {
    if (isSubsystem && isPlayerSubsystem) {
      this.addListener(new ActorGestureListener() {
        override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
          CrewOperations.assignCrewmate(getThisTile)
        }
      })

      // Start all player crewmates in their saved assigned subsystem
      // Start unassigned player crewmates in Medbay subsystem
      for (crewmate: Crewmate <- PlayerData.getCrew) {
        if (crewmate.getAssignedSubsystemName == name) {
          crewmate.setAssignment(getThisTile)
          assignCrewmate()
        }
      }
    }
  }

  private def constructInfo: SubsystemInfo = {
    if (isSubsystem) {
      // Render above or below depending on relative position of subsystem
      var infoY: Int = 0
      if (height - ty > (height / 2)) {
        infoY = (tileSize * (overlayHeight - 1)) - (ty * tileSize) + 48
      } else {
        infoY = (tileSize * (overlayHeight - 1)) - (ty * tileSize) - 48
      }
      val info: SubsystemInfo = new SubsystemInfo(name, maxOccupancy, tx * tileSize, infoY)
      info
    } else {
      null
    }
  }



  /********************
    *     Getters     *
    ********************/
  override def getName: String = {
    name
  }

  private def getThisTile: Tile = {
    this
  }

  def isSubsystem: Boolean = {
    name != "none"
  }

  def isWeaponSubsystem: Boolean = {
    weaponSystem
  }

  def getScreenCoordinates(): Vector2 = {
    val actorCoords: Vector2 = new Vector2(getX, getY)
    val stageCoords: Vector2 = this.localToStageCoordinates(actorCoords)
    val screenCoords: Vector2 = this.getStage.stageToScreenCoordinates(stageCoords)
    screenCoords
  }



  /**********************
    *     Occupancy     *
    **********************/
  def occupancyFull: Boolean = {
    assignedCrewmates == maxOccupancy
  }

  def assignCrewmate(): Unit = {
    assignedCrewmates += 1
    infoDisplay.updateOccupancy(1)
  }

  def removeCrewmate(): Unit = {
    assignedCrewmates -= 1
    infoDisplay.updateOccupancy(-1)
  }

  private def setMaxOccupancy: Int = {
    name match {
      case "Weapon Control" => 3
      case "Shield Control" => 3
      case "Engine Room" => 3
      case "Helm" => 1
      case "Medbay" => 6
      case _ => 0
    }
  }



  /**********************
    *     Rendering     *
    **********************/
  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (isSubsystem) {
      if (glowing) {
        frames += 1
      } else {
        frames -= 1
      }

      if (frames == 240) {
        glowing = false
      } else if (frames == 120) {
        glowing = true
      }

      val frameAlpha: Float = frames / 240.0f

      batch.setColor(0.2f, 0.9f, 0.2f, frameAlpha)
      batch.draw(
        Resources.spSubsystemMarker,
        tx * tileSize,
        (tileSize * (overlayHeight - 1)) - (ty * tileSize),
        tileSize,
        tileSize
      )

      infoDisplay.draw(batch, parentAlpha)
      batch.setColor(1, 1, 1, 1)
    }
  }

  def debugDraw: String = {
    name match {
      case "Weapon Control" => "W"
      case "Shield Control" => "S"
      case "Engine Room" => "E"
      case "Helm" => "H"
      case "Medbay" => "M"
      case _ => "~"
    }
  }
}
