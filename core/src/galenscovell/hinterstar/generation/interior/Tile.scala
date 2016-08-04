package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent}
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener


class Tile(x: Int, y: Int, ss: String) extends Actor {
  val tx: Int = x
  val ty: Int = y
  val subsystem: String = ss
  private var sprite: Sprite = _

  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (subsystem != null) {
        println(subsystem)
      }
    }
  })

  override def draw(batch: Batch, parentAlpha: Float): Unit = {

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
