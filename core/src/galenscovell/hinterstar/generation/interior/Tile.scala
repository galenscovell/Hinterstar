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

  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (subsystem != null) {
        CrewOperations.assignCrewmate(subsystem)
      }
    }
  })

  def hasSubsystem: Boolean = {
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
