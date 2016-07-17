package galenscovell.hinterstar.map

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.util._


/**
  * A SystemMarker is a node on the map grid.
  * SystemMarkers are the graphical components of Locations which have sprites and are rendered.
  * SystemMarker also carry state (eg EMPTY, CURRENT, EXPLORED, UNEXPLORED).
  */
class SystemMarker(x: Int, y: Int) extends Actor {
  val sx: Int = x
  val sy: Int = y

  private var frames: Int = 60
  private var sectorType: Short = 0
  private var sprite: Sprite = _
  private var glowing: Boolean = true

  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (!isEmpty) {
        SystemRepo.setSelection(getThisSystemMarker)
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
    * Return true if this SystemMarker is currently EMPTY (no sprite).
    */
  def isEmpty: Boolean = {
    sectorType == Constants.SECTOR_EMPTY
  }

  /**
    * Return true if this SystemMarker is the CURRENT place the Player is.
    */
  def isCurrent: Boolean = {
    sectorType == Constants.SECTOR_CURRENT
  }

  /**
    * Return true if this SystemMarker has been EXPLORED by the Player.
    */
  def isExplored: Boolean = {
    sectorType == Constants.SECTOR_EXPLORED
  }

  /**
    * Return true if this SystemMarker is UNEXPLORED by the Player.
    */
  def isUnexplored: Boolean = {
    sectorType == Constants.SECTOR_UNEXPLORED
  }



  /**
    * Make this SystemMarker EMPTY (remove its sprite)
    */
  def becomeEmpty(): Unit = {
    sectorType = Constants.SECTOR_EMPTY
  }

  /**
    * Make this SystemMarker the CURRENT Player location.
    */
  def becomeCurrent(): Unit = {
    ResourceManager.currentMarker.setTarget(
      sx * Constants.SECTORSIZE - Constants.SECTORSIZE,
      Gdx.graphics.getHeight - (sy * Constants.SECTORSIZE) - (4 * Constants.SECTORSIZE)
    )
    sectorType = Constants.SECTOR_CURRENT
  }

  /**
    * Mark this SystemMarker as EXPLORED by the Player.
    */
  def becomeExplored(): Unit = {
    sectorType = Constants.SECTOR_EXPLORED
  }

  /**
    * Mark this SystemMarker as UNEXPLORED by the Player.
    */
  def becomeUnexplored(): Unit = {
    sprite = ResourceManager.spTest0
    sectorType = Constants.SECTOR_UNEXPLORED
  }



  /**
    * Draw this SystemMarker's sprite.
    */
  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (!isEmpty) {
      glow(batch)
      batch.draw(
        sprite, sx * Constants.SECTORSIZE,
        Gdx.graphics.getHeight - (sy * Constants.SECTORSIZE) - (3 * Constants.SECTORSIZE),
        Constants.SECTORSIZE,
        Constants.SECTORSIZE
      )
      if (isCurrent) {
        ResourceManager.currentMarker.render(batch)
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
      batch.setColor(0.4f, 0.4f, 1.0f, frameAlpha)
    } else {
      batch.setColor(0.4f, 1.0f, 0.4f, frameAlpha)
    }

    batch.draw(
      ResourceManager.mapGlow,
      sx * Constants.SECTORSIZE - Constants.SECTORSIZE,
      Gdx.graphics.getHeight - (sy * Constants.SECTORSIZE) - (4 * Constants.SECTORSIZE),
      Constants.SECTORSIZE * 3,
      Constants.SECTORSIZE * 3
    )
    batch.setColor(1, 1, 1, 1)
  }
}
