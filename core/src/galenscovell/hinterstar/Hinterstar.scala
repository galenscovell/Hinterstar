package galenscovell.hinterstar

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{Game, Gdx}
import galenscovell.hinterstar.ui.screens._
import galenscovell.hinterstar.util.ResourceManager


class Hinterstar extends Game {
  var spriteBatch: SpriteBatch = null
  var loadingScreen: AbstractScreen = null
  var mainMenuScreen: AbstractScreen = null
  var startScreen: AbstractScreen = null
  var gameScreen: AbstractScreen = null


  def create(): Unit =  {
    spriteBatch = new SpriteBatch
    loadingScreen = new LoadScreen(this)
    mainMenuScreen = new MainMenuScreen(this)
    setScreen(loadingScreen)
  }

  override def dispose(): Unit =  {
    loadingScreen.dispose()
    mainMenuScreen.dispose()
    if (startScreen != null) {
      startScreen.dispose()
    }
    if (gameScreen != null) {
      gameScreen.dispose()
    }
    ResourceManager.dispose()
  }

  def createStartScreen(): Unit = {
    if (startScreen != null) {
      startScreen.dispose()
    }
    startScreen = new StartScreen(this)
  }

  def createGameScreen(): Unit =  {
    if (gameScreen != null) {
      gameScreen.dispose()
    }
    gameScreen = new GameScreen(this)
  }

  def loadGame(): Unit = {

  }

  def createPreferenceScreen(): Unit =  {

  }

  def quitGame(): Unit = {
    Gdx.app.exit()
  }
}
