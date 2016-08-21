package galenscovell.hinterstar.generation.sector

import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.generation.sector.MarkerType.MarkerType
import galenscovell.hinterstar.util._


class SystemMarker(x: Int, y: Int) extends Actor {
  val sx: Int = x * Constants.SYSTEMMARKER_SIZE
  val sy: Int = y * Constants.SYSTEMMARKER_SIZE

  private var frames: Int = 60
  private var markerType: MarkerType = MarkerType.EMPTY
  private var sprite: Sprite = _
  private var glowing: Boolean = true
  private var system: System = _

  initialize()


  private def initialize(): Unit = {
    if (!isEmpty) {
      this.addListener(new ActorGestureListener() {
        override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
            SystemRepo.setSelection(getSystem)
        }
      })
    }
  }



  /********************
    *     Getters     *
    ********************/
  def getThisSystemMarker: SystemMarker = {
    this
  }

  def getSystem: System = {
    system
  }

  def isEmpty: Boolean = {
    markerType == MarkerType.EMPTY
  }

  def isCurrent: Boolean = {
    markerType == MarkerType.CURRENT
  }

  def isExplored: Boolean = {
    markerType == MarkerType.EXPLORED
  }

  def isUnexplored: Boolean = {
    markerType == MarkerType.UNEXPLORED
  }



  /********************
    *     Setters     *
    ********************/
  def setSystem(s: System): Unit = {
    system = s
  }

  def becomeEmpty(): Unit = {
    markerType = MarkerType.EMPTY
  }

  def becomeCurrent(): Unit = {
    Resources.currentMarker.setTarget(
      sx + Constants.SYSTEM_MARKER_X - Constants.SYSTEMMARKER_SIZE,
      Constants.EXACT_Y - sy + Constants.SYSTEM_MARKER_Y - Constants.SYSTEMMARKER_SIZE
    )
    markerType = MarkerType.CURRENT
  }

  def becomeExplored(): Unit = {
    markerType = MarkerType.EXPLORED
  }

  def becomeUnexplored(): Unit = {
    sprite = Resources.spTest0
    markerType = MarkerType.UNEXPLORED
  }



  /**********************
    *     Rendering     *
    **********************/
  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (!isEmpty) {
      glow(batch)
      batch.draw(
        sprite,
        sx + Constants.SYSTEM_MARKER_X,
        Constants.EXACT_Y - sy + Constants.SYSTEM_MARKER_Y,
        Constants.SYSTEMMARKER_SIZE,
        Constants.SYSTEMMARKER_SIZE
      )
      if (isCurrent) {
        Resources.currentMarker.render(batch)
      }
    }
  }

  private def glow(batch: Batch): Unit = {
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
    if (isExplored) {
      batch.setColor(0.2f, 0.2f, 0.75f, frameAlpha)
    } else {
      batch.setColor(0.2f, 0.75f, 0.2f, frameAlpha)
    }

    batch.draw(
      Resources.mapGlow,
      sx + Constants.SYSTEM_MARKER_X - Constants.SYSTEMMARKER_SIZE,
      Constants.EXACT_Y - sy + Constants.SYSTEM_MARKER_Y - Constants.SYSTEMMARKER_SIZE,
      Constants.SYSTEMMARKER_SIZE * 3,
      Constants.SYSTEMMARKER_SIZE * 3
    )
    batch.setColor(1, 1, 1, 1)
  }

  def debugDraw(): String = {
    markerType match {
      case MarkerType.EMPTY => "~"
      case MarkerType.UNEXPLORED => "U"
      case MarkerType.EXPLORED => "E"
      case MarkerType.CURRENT => "C"
      case _ => "!"
    }
  }
}
