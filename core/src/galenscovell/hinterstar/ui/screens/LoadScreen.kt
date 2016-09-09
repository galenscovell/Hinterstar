package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.util.*


class LoadScreen(private val root: Hinterstar) : Screen {
    private val camera: OrthographicCamera = OrthographicCamera(
            Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    val viewport: FitViewport = FitViewport(Constants.EXACT_X, Constants.EXACT_Y, camera)
    private var stage: Stage = Stage(viewport, root.spriteBatch)
    private var loadingBar: ProgressBar = createBar()



    private fun create(): Unit {
        val loadingMain: Table = Table()
        loadingMain.setFillParent(true)
        val barTable: Table = Table()
        barTable.add(loadingBar).width(400f).expand().fill()
        loadingMain.add(barTable).expand().fill()
        stage.addActor(loadingMain)
    }

    private fun createBar(): ProgressBar {
        val fill: TextureRegionDrawable = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("loadingFill.png"))))
        val empty: TextureRegionDrawable = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("loadingEmpty.png"))))
        val barStyle: ProgressBar.ProgressBarStyle = ProgressBar.ProgressBarStyle(empty, fill)
        val bar: ProgressBar = ProgressBar(0f, 10f, 1f, false, barStyle)
        barStyle.knobBefore = fill
        bar.value = 0f
        bar.setAnimateDuration(0.1f)
        return bar
    }



    /**********************
     * Screen Operations *
     **********************/
    override fun render(delta: Float): Unit {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
        if (Resources.assetManager.update()) {
            Resources.done()
            stage.getRoot().addAction(Actions.sequence(Actions.fadeOut(0.4f), toMainMenuScreen))
        }
        loadingBar.setValue(Resources.assetManager.getLoadedAssets().toFloat())
    }

    override fun show(): Unit {
        Resources.load()
        create()
        stage.getRoot().getColor().a = 0f
        stage.getRoot().addAction(Actions.sequence(Actions.fadeIn(0.4f)))
    }

    override fun resize(width: Int, height: Int): Unit {
        if (stage != null) {
            stage.getViewport().update(width, height, true)
        }
    }

    override fun hide(): Unit {
        Gdx.input.inputProcessor = null
    }

    override fun dispose(): Unit {
        stage.dispose()
    }

    override fun pause(): Unit {}

    override fun resume(): Unit {}



    /***************************
     * Custom Scene2D Actions *
     ***************************/
    private var toMainMenuScreen: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            root.screen = root.mainMenuScreen
            return true
        }
    }
}
