package galenscovell.hinterstar

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{Game, Gdx}
import galenscovell.hinterstar.ui.screens._
import galenscovell.hinterstar.util._


class Hinterstar extends Game {
  var spriteBatch: SpriteBatch = _
  var loadScreen: LoadScreen = _
  var mainMenuScreen: MainMenuScreen = _
  var startScreen: StartScreen = _
  var gameScreen: GameScreen = _


  def create(): Unit =  {
    PlayerData.init()
    spriteBatch = new SpriteBatch
    loadScreen = new LoadScreen(this)
    mainMenuScreen = new MainMenuScreen(this)
    setScreen(loadScreen)
  }

  override def dispose(): Unit =  {
    loadScreen.dispose()
    mainMenuScreen.dispose()
    if (startScreen != null) {
      startScreen.dispose()
    }
    if (gameScreen != null) {
      gameScreen.dispose()
    }
    Resources.dispose()
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
    // TODO: Not yet implemented, PlayerData.load everything

  }

  def createPreferenceScreen(): Unit =  {
    // TODO: Not yet implemented

  }

  def quitGame(): Unit = {
    Gdx.app.exit()
  }
}
