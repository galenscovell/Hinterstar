package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.ui.components.startscreen.*
import galenscovell.hinterstar.util.*


class StartScreen(private val root: Hinterstar) : Screen {
    private val camera: OrthographicCamera = OrthographicCamera(
            Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private val viewport: FitViewport = FitViewport(Constants.EXACT_X, Constants.EXACT_Y, camera)
    private val stage = Stage(viewport, root.spriteBatch)

    private val crewPanel: CrewSelectPanel = CrewSelectPanel()
    private val shipPanel: ShipSelectPanel = ShipSelectPanel()



    private fun create(): Unit {
        shipPanel.updateShipDetails()
        shipPanel.updateShipDisplay(true)

        val mainTable: Table = Table()
        mainTable.setFillParent(true)

        val titleTable: Table = createTitleTable()
        val contentTable: Table = Table()

        contentTable.add(crewPanel).width(240f).height(400f).left().padRight(20f)
        contentTable.add(shipPanel).width(520f).height(400f).right()

        mainTable.add(titleTable).width(770f).height(50f).pad(5f)
        mainTable.row()
        mainTable.add(contentTable).width(780f).height(400f)

        stage.addActor(mainTable)
        mainTable.addAction(Actions.sequence(
                Actions.fadeOut(0f),
                Actions.fadeIn(0.3f))
        )
    }

    private fun createTitleTable(): Table {
        val titleTable: Table = Table()
        val noticeTable: Table = Table()

        val returnButton: TextButton = TextButton("Main Menu", Resources.greenButtonStyle)
        returnButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                stage.root.addAction(Actions.sequence(
                        Actions.fadeOut(0.3f),
                        toMainMenuAction)
                )
            }
        })

        val embarkButton: TextButton = TextButton("Embark", Resources.greenButtonStyle)
        embarkButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                PlayerData.clear()
                PlayerData.saveCrew(crewPanel.getCrewmates())
                PlayerData.loadCrew()
                PlayerData.saveShip(shipPanel.getShip())
                PlayerData.loadShip()
                PlayerData.saveWeapons()
                PlayerData.loadWeapons()

                root.createGameScreen()
                stage.root.addAction(Actions.sequence(
                        Actions.fadeOut(0.3f),
                        toGameScreenAction)
                )
            }
        })

        titleTable.add(returnButton).width(200f).height(50f)
        titleTable.add(noticeTable).width(364f).height(50f)
        titleTable.add(embarkButton).width(200f).height(50f)

        return titleTable
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
    private var toMainMenuAction: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            root.screen = root.mainMenuScreen
            return true
        }
    }
    private var toGameScreenAction: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            root.screen = root.gameScreen
            return true
        }
    }
}
