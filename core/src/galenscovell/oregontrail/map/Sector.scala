package galenscovell.oregontrail.map

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.oregontrail.util._


class Sector(x: Int, y: Int) extends Actor {
  val sx: Int = x
  val sy: Int = y
  private var frames: Int = 60
  private var sectorType: Short = 0
  private var sprite: Sprite = null
  private var glowing: Boolean = true

  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (!isEmpty) {
        Repository.setSelection(getThisSector)
      }
    }
  })


  def getThisSector(): Sector = {
    this
  }

  def isEmpty(): Boolean = {
    sectorType == Constants.SECTOR_EMPTY
  }

  def isCurrent(): Boolean = {
    sectorType == Constants.SECTOR_CURRENT
  }

  def isExplored(): Boolean = {
    sectorType == Constants.SECTOR_EXPLORED
  }

  def isUnexplored(): Boolean = {
    sectorType == Constants.SECTOR_UNEXPLORED
  }

  def becomeEmpty(): Unit = {
    sectorType = Constants.SECTOR_EMPTY
  }

  def becomeCurrent(): Unit = {
    ResourceManager.currentMarker.setTarget(sx * Constants.SECTORSIZE - Constants.SECTORSIZE, Gdx.graphics.getHeight - (sy * Constants.SECTORSIZE) - (4 * Constants.SECTORSIZE))
    sectorType = Constants.SECTOR_CURRENT
  }

  def becomeExplored(): Unit = {
    sectorType = Constants.SECTOR_EXPLORED
  }

  def becomeUnexplored(): Unit = {
    sprite = ResourceManager.sp_test0
    sectorType = Constants.SECTOR_UNEXPLORED
  }

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (!isEmpty) {
      glow(batch)
      batch.draw(sprite, sx * Constants.SECTORSIZE, Gdx.graphics.getHeight - (sy * Constants.SECTORSIZE) - (3 * Constants.SECTORSIZE), Constants.SECTORSIZE, Constants.SECTORSIZE)
      if (isCurrent) {
        ResourceManager.currentMarker.render(batch)
      }
    }
  }

  private def glow(batch: Batch): Unit = {
    if (glowing) {
      frames += 1
    }
    else {
      frames -= 2
    }

    if (frames == 120) {
      glowing = false
    }
    else if (frames == 40) {
      glowing = true
    }

    val frameAlpha: Float = (frames / 120.0f)
    if (isExplored) {
      batch.setColor(0.4f, 0.4f, 1.0f, frameAlpha)
    }
    else {
      batch.setColor(0.4f, 1.0f, 0.4f, frameAlpha)
    }

    batch.draw(ResourceManager.mapGlow, sx * Constants.SECTORSIZE - Constants.SECTORSIZE, Gdx.graphics.getHeight - (sy * Constants.SECTORSIZE) - (4 * Constants.SECTORSIZE), Constants.SECTORSIZE * 3, Constants.SECTORSIZE * 3)
    batch.setColor(1, 1, 1, 1)
  }
}
