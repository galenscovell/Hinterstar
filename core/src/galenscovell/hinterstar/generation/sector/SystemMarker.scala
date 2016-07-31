package galenscovell.hinterstar.generation.sector

import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.generation.sector.MarkerType.MarkerType
import galenscovell.hinterstar.util._


/**
  * A SystemMarker is a node on the map grid.
  * SystemMarkers are the graphical components of Systems which have sprites and are rendered.
  * SystemMarker also carry state (eg EMPTY, CURRENT, EXPLORED, UNEXPLORED).
  */
class SystemMarker(x: Int, y: Int) extends Actor {
  val sx: Int = x * Constants.SYSTEMMARKER_SIZE
  val sy: Int = y * Constants.SYSTEMMARKER_SIZE

  private var frames: Int = 60
  private var markerType: MarkerType = MarkerType.EMPTY
  private var sprite: Sprite = _
  private var glowing: Boolean = true
  private var system: System = _

  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (!isEmpty) {
        SystemRepo.setSelection(getSystem)
      }
    }
  })


  /**
    * Return this SystemMarker.
    */
  def getThisSystemMarker: SystemMarker = {
    this
  }

  /**
    * Return this SystemMarker's associated System.
    */
  def getSystem: System = {
    system
  }

  /**
    * Associate a System with this SystemMarker.
    */
  def setSystem(s: System): Unit = {
    system = s
  }

  /**
    * Return true if this SystemMarker is currently EMPTY (no sprite).
    */
  def isEmpty: Boolean = {
    markerType == MarkerType.EMPTY
  }

  /**
    * Return true if this SystemMarker is the CURRENT place the Player is.
    */
  def isCurrent: Boolean = {
    markerType == MarkerType.CURRENT
  }

  /**
    * Return true if this SystemMarker has been EXPLORED by the Player.
    */
  def isExplored: Boolean = {
    markerType == MarkerType.EXPLORED
  }

  /**
    * Return true if this SystemMarker is UNEXPLORED by the Player.
    */
  def isUnexplored: Boolean = {
    markerType == MarkerType.UNEXPLORED
  }



  /**
    * Make this SystemMarker EMPTY (remove its sprite)
    */
  def becomeEmpty(): Unit = {
    markerType = MarkerType.EMPTY
  }

  /**
    * Make this SystemMarker the CURRENT Player System.
    */
  def becomeCurrent(): Unit = {
    Resources.currentMarker.setTarget(
      sx + Constants.SYSTEM_MARKER_X - Constants.SYSTEMMARKER_SIZE,
      Constants.EXACT_Y - sy + Constants.SYSTEM_MARKER_Y - Constants.SYSTEMMARKER_SIZE
    )
    markerType = MarkerType.CURRENT
  }

  /**
    * Mark this SystemMarker as EXPLORED by the Player.
    */
  def becomeExplored(): Unit = {
    markerType = MarkerType.EXPLORED
  }

  /**
    * Mark this SystemMarker as UNEXPLORED by the Player.
    */
  def becomeUnexplored(): Unit = {
    sprite = Resources.spTest0
    markerType = MarkerType.UNEXPLORED
  }



  /**
    * Draw this SystemMarker's sprite.
    */
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

  /**
    * Draw this SystemMarker's animation effects (glowing/color).
    */
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
