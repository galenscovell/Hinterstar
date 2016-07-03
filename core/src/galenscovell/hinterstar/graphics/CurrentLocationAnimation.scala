package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.g2d._
import galenscovell.hinterstar.util._


/**
  * CurrentLocationAnimation is an animation signifying the current Player location.
  */
class CurrentLocationAnimation {
  private final val sprite: TextureRegion = ResourceManager.uiAtlas.createSprite("current_marker")
  private final val size: Float = Constants.SECTORSIZE * 3
  private var frame: Float = 0.0f
  private var lx: Int = 0
  private var ly: Int = 0


  /**
    * Set the central location for this animation to revolve around.
    */
  def setTarget(x: Int, y: Int): Unit = {
    this.lx = x
    this.ly = y
  }

  /**
    * Render the animation revolving around the current location.
    */
  def render(batch: Batch): Unit = {
    batch.draw(sprite, lx, ly, size / 2, size / 2, size, size, 1, 1, -frame)
    if (frame < 360) {
      frame += 0.5f
    } else {
      frame = 0
    }
  }
}