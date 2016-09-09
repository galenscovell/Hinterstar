package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.util.*


class MainMenuScreen(private val root: Hinterstar) : Screen {
    private val camera: OrthographicCamera = OrthographicCamera(
            Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private val viewport: FitViewport = FitViewport(Constants.EXACT_X, Constants.EXACT_Y, camera)
    private var stage: Stage = Stage(viewport, root.spriteBatch)



    private fun create(): Unit {
        val mainTable: Table = Table()
        mainTable.setFillParent(true)

        val titleTable: Table = Table()
        val titleLabel: Label = Label("Hinterstar", Resources.labelXLargeStyle)
        titleLabel.setAlignment(Align.center, Align.left)
        titleTable.add(titleLabel).width(760f).height(60f)

        val buttonTable: Table = Table()
        val newGameTable: Table = Table()
        newGameTable.background = Resources.npTest4
        val newGameButton: TextButton = TextButton("New Game", Resources.buttonMenuStyle)
        newGameButton.label.setAlignment(Align.center, Align.center)
        newGameButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                root.createStartScreen()
                stage.root.addAction(Actions.sequence(
                        Actions.fadeOut(0.3f),
                        toStartScreenAction)
                )
            }
        })
        val continueGameTable: Table = Table()
        continueGameTable.background = Resources.npTest4
        val continueGameButton: TextButton = TextButton("Continue Game", Resources.buttonMenuStyle)
        continueGameButton.label.setAlignment(Align.center, Align.center)
        continueGameButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float): Unit {

            }
        })
        val settingTable: Table = Table()
        settingTable.background = Resources.npTest4
        val settingButton: TextButton = TextButton("Preferences", Resources.buttonMenuStyle)
        settingButton.label.setAlignment(Align.center, Align.center)
        settingButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float): Unit {

            }
        })
        val quitTable: Table = Table()
        quitTable.background = Resources.npTest4
        val quitButton: TextButton = TextButton("Close", Resources.buttonMenuStyle)
        quitButton.label.setAlignment(Align.center, Align.center)
        quitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                stage.root.addAction(Actions.sequence(
                        Actions.fadeOut(0.3f),
                        quitGameAction)
                )
            }
        })

        val detailTable: Table = Table()
        val detailLabel: Label = Label("v0.1a 2016 Galen Scovell", Resources.labelSmallStyle)
        detailLabel.setAlignment(Align.center, Align.right)
        detailTable.add(detailLabel).width(760f).height(40f)

        buttonTable.add(newGameButton).width(550f).height(90f).pad(6f).left()
        buttonTable.add(newGameTable).width(212f).height(90f).expand().pad(6f).right()
        buttonTable.row()
        buttonTable.add(continueGameTable).width(212f).height(90f).expand().pad(6f).left()
        buttonTable.add(continueGameButton).width(550f).height(90f).pad(6f).right()
        buttonTable.row()
        buttonTable.add(settingButton).width(550f).height(90f).pad(6f).left()
        buttonTable.add(settingTable).width(212f).height(90f).expand().pad(6f).right()
        buttonTable.row()
        buttonTable.add(quitTable).width(212f).height(90f).expand().pad(6f).left()
        buttonTable.add(quitButton).width(550f).height(90f).pad(6f).right()

        mainTable.add(titleTable).width(780f).height(60f).expand().center().pad(6f)
        mainTable.row()
        mainTable.add(buttonTable).width(780f).height(380f).expand().center().pad(6f)
        mainTable.row()
        mainTable.add(detailTable).width(780f).height(40f).expand().center().pad(6f)

        stage.addActor(mainTable)
        mainTable.addAction(Actions.sequence(
                Actions.fadeOut(0f),
                Actions.fadeIn(0.3f))
        )
    }



    /**********************
     * Screen Operations *
     **********************/
    override fun render(delta: Float): Unit {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int): Unit {
        stage.viewport.update(width, height, true)
    }

    override fun show(): Unit {
        create()
        Gdx.input.inputProcessor = stage
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
    private var toStartScreenAction: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            root.screen = root.startScreen
            return true
        }
    }
    private var quitGameAction: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            root.quitGame()
            return true
        }
    }
}
