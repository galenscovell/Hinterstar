package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx._
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import galenscovell.hinterstar.Hinterstar


class AbstractScreen(val gameRoot: Hinterstar) extends Screen {
  protected val root: Hinterstar = gameRoot
  protected var stage: Stage = _


  protected def create(): Unit = {}

  override def render(delta: Float): Unit = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.gl.glClearColor(0, 0, 0, 1)
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

  override def pause(): Unit =  {}

  override def resume(): Unit =  {}

  override def dispose(): Unit = {
    if (stage != null) {
      stage.dispose()
    }
  }
}
