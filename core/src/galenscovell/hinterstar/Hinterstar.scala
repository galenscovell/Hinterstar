package galenscovell.hinterstar

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{Game, Gdx}
import galenscovell.hinterstar.ui.screens._
import galenscovell.hinterstar.util.{PlayerData, ResourceManager}


/**
  * Main entry point for application, contains:
  *     root spriteBatch used throughout game
  *     all Screens used throughout game
  */
class Hinterstar extends Game {
  var spriteBatch: SpriteBatch = null
  var loadingScreen: AbstractScreen = null
  var mainMenuScreen: AbstractScreen = null
  var startScreen: AbstractScreen = null
  var gameScreen: AbstractScreen = null


  /**
    * Init PlayerData prefs, spriteBatch, loadingScreen and mainMenuScreen.
    * Set current Screen to loadingScreen.
    */
  def create(): Unit =  {
    PlayerData.init()
    spriteBatch = new SpriteBatch
    loadingScreen = new LoadScreen(this)
    mainMenuScreen = new MainMenuScreen(this)
    setScreen(loadingScreen)
  }


  /**
    * Dispose of resources used in all Screens.
    */
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


  /**
    * Construct StartScreen, disposing of previous if present.
    */
  def createStartScreen(): Unit = {
    if (startScreen != null) {
      startScreen.dispose()
    }
    startScreen = new StartScreen(this)
  }


  /**
    * Construct GameScreen, disposing of previous if present.
    */
  def createGameScreen(): Unit =  {
    if (gameScreen != null) {
      gameScreen.dispose()
    }
    gameScreen = new GameScreen(this)
  }


  /**
    *
    */
  def loadGame(): Unit = {
    // TODO: Not yet implemented

  }


  /**
    *
    */
  def createPreferenceScreen(): Unit =  {
    // TODO: Not yet implemented

  }


  /**
    * Call exit(), activating dispose() and closing application.
    */
  def quitGame(): Unit = {
    Gdx.app.exit()
  }
}
