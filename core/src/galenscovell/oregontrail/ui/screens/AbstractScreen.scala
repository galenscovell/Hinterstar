package galenscovell.oregontrail.ui.screens

import com.badlogic.gdx._
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import galenscovell.oregontrail.OregonTrailMain


class AbstractScreen(val gameRoot: OregonTrailMain) extends Screen {
  protected final val root: OregonTrailMain = gameRoot
  protected var stage: Stage = null


  protected def create(): Unit = {

  }

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    stage.act(delta)
    stage.draw()
  }

  override def resize(width: Int, height: Int): Unit = {
    if (stage != null) {
      stage.getViewport.update(width, height, true)
    }
  }

  override def show(): Unit = {
    create()
    Gdx.input.setInputProcessor(stage)
  }

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def pause(): Unit =  {

  }

  override def resume(): Unit =  {

  }

  override def dispose(): Unit = {
    if (stage != null) {
      stage.dispose()
    }
  }
}
