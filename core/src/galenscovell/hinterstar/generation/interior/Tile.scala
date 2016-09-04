package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent}
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util._


class Tile(tx: Int, ty: Int, tileSize: Int, overlayHeight: Int, name: String,
           hasWeapon: Boolean, isPlayerSubsystem: Boolean, traversible: Boolean) extends Actor {
  private var frames: Int = 100
  private var glowing: Boolean = true
  private var assignedCrewmates: Int = 0

  private val maxOccupancy: Int = setMaxOccupancy
  private val icon: Sprite = createSprite
  private val infoDisplay: SubsystemInfo = constructInfo

  private var neighbors: Array[Tile] = _

  initialize()


  private def initialize(): Unit = {
    if (isSubsystem) {
      if (isPlayerSubsystem) {
        // Set clicklistener for player subsystems (for crewmate assignment)
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
      } else {
        // Set clicklistener for enemy subsystems (for targetting)
        this.addListener(new ActorGestureListener() {
          override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
            if (CrewOperations.weaponSelected) {
              CrewOperations.targetEnemySubsystem(getThisTile)
            }
          }
        })
      }
    }

    this.setPosition(tx * tileSize, (tileSize * (overlayHeight - 1)) - (ty * tileSize))
  }



  /********************
    *     Getters     *
    ********************/
  def getTx: Int = {
    tx
  }

  def getTy: Int = {
    ty
  }

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
    hasWeapon
  }

  def isTraversible: Boolean = {
    traversible
  }

  def getIcon: Sprite = {
    icon
  }

  def getNeighbors: Array[Tile] = {
    neighbors
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
      (tileSize / 2) - 4,
      (tileSize / 2) - 4
    ))
  }



  /********************
    *     Setters     *
    ********************/
  def setNeighbors(tiles: Array[Tile]): Unit = {
    neighbors = tiles
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
  private def createSprite: Sprite = {
    if (isSubsystem) {
      val iconName: String = name match {
        case "Weapon Control" => "weapon"
        case "Shield Control" => "shield"
        case "Engine Room" => "engine"
        case "Helm" => "helm"
        case "Medbay" => "medbay"
        case _ => ""
      }

      new Sprite(Resources.atlas.createSprite("icon_" + iconName))
    } else {
      null
    }
  }

  private def constructInfo: SubsystemInfo = {
    if (isSubsystem) {
      // Render above or below depending on relative position of subsystem
      var infoY: Float = getY
      if (overlayHeight - ty > (overlayHeight / 2)) {
        infoY += (overlayHeight * tileSize)
      }
      new SubsystemInfo(name, icon, maxOccupancy, tx * tileSize, infoY.toInt, tileSize)
    } else {
      null
    }
  }

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
      batch.draw(Resources.spSubsystemMarker, getX, getY, tileSize, tileSize)

      infoDisplay.draw(batch, parentAlpha)
      batch.setColor(1, 1, 1, 1)
    }
  }
}
