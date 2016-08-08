package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent}
import galenscovell.hinterstar.util.{CrewOperations, Resources}


class Subsystem(x: Int, y: Int, size: Int, ss: String) extends Actor {
  val tx: Int = x
  val ty: Int = y
  val subsystemSize: Int = size
  val name: String = ss

  private var frames: Int = 60
  private var glowing: Boolean = true
  private var sprite: Sprite = _

  private val maxOccupancy: Int = setMaxOccupancy
  private var assignedCrewmates: Int = 0

  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (isInUse) {
        if (!occupancyFull) {
          CrewOperations.assignCrewmate(getThisSubsystem)
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

  private def getThisSubsystem: Subsystem = {
    this
  }

  private def isInUse: Boolean = {
    name != "None"
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
    if (isInUse) {
      batch.setColor(0.2f, 0.9f, 0.2f, frameAlpha)
      batch.draw(
        Resources.spSubsystemMarker,
        tx * subsystemSize - (subsystemSize / 4),
        (subsystemSize * 2) - (ty * subsystemSize) - (subsystemSize / 4),
        subsystemSize * 1.5f,
        subsystemSize * 1.5f
      )
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
