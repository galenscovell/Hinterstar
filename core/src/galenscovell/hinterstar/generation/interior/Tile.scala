package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent}
import galenscovell.hinterstar.util.{CrewOperations, Resources}


class Tile(x: Int, y: Int, size: Int, height: Int, ss: String) extends Actor {
  val tx: Int = x
  val ty: Int = y
  val tileSize: Int = size
  val overlayHeight: Int = height
  val name: String = ss

  private var frames: Int = 60
  private var glowing: Boolean = true

  private val maxOccupancy: Int = setMaxOccupancy
  private var assignedCrewmates: Int = 0

  private val infoDisplay: SubsystemInfo = constructInfo


  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (isSubsystem) {
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
      case _ => 0
    }
  }



  override def getName: String = {
    name
  }

  private def getThisTile: Tile = {
    this
  }

  private def isSubsystem: Boolean = {
    name != "none"
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



  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (isSubsystem) {
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

      batch.setColor(0.2f, 0.9f, 0.2f, frameAlpha)
      batch.draw(
        Resources.spSubsystemMarker,
        tx * tileSize - (tileSize / 4),
        (tileSize * (overlayHeight - 1)) - (ty * tileSize) - (tileSize / 4),
        tileSize * 1.5f,
        tileSize * 1.5f
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
      case _ => "~"
    }
  }
}
