package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent}
import galenscovell.hinterstar.util.{CrewOperations, Resources}


class Tile(x: Int, y: Int, size: Int, ss: String) extends Actor {
  val tx: Int = x
  val ty: Int = y
  val tileSize: Int = size
  val subsystem: String = ss

  private var frames: Int = 60
  private var glowing: Boolean = true
  private var sprite: Sprite = _

  private val maxOccupancy: Int = setMaxOccupancy
  private var assignedCrewmates: Int = 0

  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (hasSubsystem) {
        if (!occupancyFull) {
          CrewOperations.assignCrewmate(getThisTile)
        }
      }
    }
  })


  // Occupancy Operations
  def occupancyFull: Boolean = {
    assignedCrewmates == maxOccupancy
  }

  def assignCrewmate(): Unit = {
    assignedCrewmates += 1
  }

  def removeCrewmate(): Unit = {
    assignedCrewmates -= 1
  }

  private def setMaxOccupancy: Int = {
    subsystem match {
      case "Weapon Control" => 3
      case "Shield Control" => 3
      case "Engine Room" => 3
      case "Helm" => 1
      case _ => 0
    }
  }



  def getSubsystemName: String = {
    subsystem
  }

  private def getThisTile: Tile = {
    this
  }

  private def hasSubsystem: Boolean = {
    subsystem != "None"
  }



  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (glowing) {
      frames += 1
    } else {
      frames -= 2
    }

    if (frames == 120) {
      glowing = false
    } else if (frames == 40) {
      glowing = true
    }

    val frameAlpha: Float = frames / 120.0f
    if (hasSubsystem) {
      batch.setColor(0.2f, 0.9f, 0.2f, frameAlpha)
      batch.draw(
        Resources.spSubsystemMarker,
        tx * tileSize - (tileSize / 4),
        (tileSize * 2) - (ty * tileSize) - (tileSize / 4),
        tileSize * 1.5f,
        tileSize * 1.5f
      )
      batch.setColor(1, 1, 1, 1)
    }
  }

  def debugDraw: String = {
    subsystem match {
      case "Weapon Control" => "W"
      case "Shield Control" => "S"
      case "Engine Room" => "E"
      case "Helm" => "H"
      case _ => "~"
    }
  }
}
