package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.g2d._
import galenscovell.hinterstar.util._


class CurrentLocationAnimation {
  private final val sprite: TextureRegion = ResourceManager.uiAtlas.createSprite("current_marker")
  private val size: Float = Constants.SECTORSIZE * 3
  private var frame: Float = 0.0f
  private var x: Int = 0
  private var y: Int = 0


  def setTarget(x: Int, y: Int): Unit = {
    this.x = x
    this.y = y
  }

  def render(batch: Batch): Unit = {
    batch.draw(sprite, x, y, size / 2, size / 2, size, size, 1, 1, -frame)
    if (frame < 360) {
      frame += 0.5f
    }
    else {
      frame = 0
    }
  }
}