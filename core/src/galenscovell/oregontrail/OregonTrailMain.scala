package galenscovell.oregontrail

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import galenscovell.oregontrail.ui.screens._
import galenscovell.oregontrail.util.ResourceManager


class OregonTrailMain extends Game {
  var spriteBatch: SpriteBatch = null
  var loadingScreen: AbstractScreen = null
  var mainMenuScreen: AbstractScreen = null
  var gameScreen: AbstractScreen = null


  def create(): Unit =  {
    this.spriteBatch = new SpriteBatch
    this.loadingScreen = new LoadScreen(this)
    this.mainMenuScreen = new MainMenuScreen(this)
    setScreen(loadingScreen)
  }

  override def dispose(): Unit =  {
    mainMenuScreen.dispose()
    if (gameScreen != null) {
      gameScreen.dispose()
    }
    ResourceManager.dispose()
  }

  def newGame(): Unit =  {
    this.gameScreen = new GameScreen(this)
  }

  def loadGame(): Unit = {

  }
}
