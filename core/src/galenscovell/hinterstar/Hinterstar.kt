package galenscovell.hinterstar

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import galenscovell.hinterstar.ui.screens.*
import galenscovell.hinterstar.util.*


class Hinterstar() : Game() {
    lateinit var spriteBatch: SpriteBatch
    lateinit var loadScreen: LoadScreen
    lateinit var mainMenuScreen: MainMenuScreen
    var startScreen: StartScreen? = null
    var gameScreen: GameScreen? = null



    override fun create(): Unit {
        PlayerData.init()
        spriteBatch = SpriteBatch()
        loadScreen = LoadScreen(this)
        mainMenuScreen = MainMenuScreen(this)
        setScreen(loadScreen)
    }

    override fun dispose(): Unit {
        loadScreen.dispose()
        mainMenuScreen.dispose()
        startScreen?.dispose()
        gameScreen?.dispose()
        Resources.dispose()
    }

    fun createStartScreen(): Unit {
        startScreen?.dispose()
        startScreen = StartScreen(this)
    }

    fun createGameScreen(): Unit {
        gameScreen?.dispose()
        gameScreen = GameScreen(this)
    }

    fun loadGame(): Unit {
        // TODO: Not yet implemented, PlayerData.load() everything
    }

    fun createPreferenceScreen(): Unit {
        // TODO: Not yet implemented
    }

    fun quitGame(): Unit {
        Gdx.app.exit()
    }
}
