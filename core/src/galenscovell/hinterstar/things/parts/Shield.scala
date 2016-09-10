package galenscovell.hinterstar.things.parts

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.scenes.scene2d.Actor
import galenscovell.hinterstar.util.Resources


class Shield(width: Int, height: Int) extends Actor {
  private val sprite: Sprite = Resources.shieldLayer



  /**********************
    *     Rendering     *
    **********************/
  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    batch.draw(sprite, getX - 40, getY - 40, width, height)
  }
}
