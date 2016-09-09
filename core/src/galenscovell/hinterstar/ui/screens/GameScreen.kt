package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.graphics.*
import galenscovell.hinterstar.processing.GestureHandler
import galenscovell.hinterstar.ui.components.gamescreen.stages.*
import galenscovell.hinterstar.util.*
import java.util.*


class GameScreen constructor(private val root: Hinterstar) : Screen {
    private val actionCamera: OrthographicCamera = OrthographicCamera(
            Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private val interfaceCamera: OrthographicCamera = OrthographicCamera(
            Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    lateinit private var actionStage: ActionStage
    lateinit private var interfaceStage: InterfaceStage

    private val input: InputMultiplexer = InputMultiplexer()
    private val gestureHandler: GestureDetector = GestureDetector(GestureHandler(this))
    private var travelFrames: Int = 0
    private var travelPanelOpen: Boolean = false

    private val timestep: Float = (1 / 60.0f) * 30
    private var accumulator: Float = 0f
    private var paused: Boolean = false

    private var normalBg: ParallaxBackground? = null
    private var blurBg: ParallaxBackground? = null
    private var currentBackground: ParallaxBackground? = null

    private var bg0: String = (Math.random() * 8).toInt().toString()       // Value between 0-7
    private var bg1: String = ((Math.random() * 4).toInt() + 8).toString() // Value between 8-12
    private var bg2: String = "stars0"
    private var bg3: String = "stars1"
    private var bg2Blur: String = "stars0_blur"
    private var bg3Blur: String = "stars1_blur"

    private val lerp: Float = 0.9f
    private val originVector: Vector2 = Vector2(Constants.EXACT_X / 2, Constants.EXACT_Y / 2)
    private val cameraXmin: Float = Constants.EXACT_X * 0.375f
    private val cameraXmax: Float = Constants.EXACT_X * 0.625f

    init {
        SystemOperations.setup(this)
        val actionViewport: FitViewport = FitViewport(Constants.EXACT_X, Constants.EXACT_Y, actionCamera)
        val interfaceViewport: FitViewport = FitViewport(Constants.EXACT_X, Constants.EXACT_Y, interfaceCamera)
        actionStage = ActionStage(this, actionViewport, root.spriteBatch)
        interfaceStage = InterfaceStage(this, interfaceViewport, root.spriteBatch, actionStage.getPlayer())

        createBackground()
        currentBackground = normalBg

        enableInput()
        actionStage.toggleInteriorOverlay()
    }



    private fun enableInput(): Unit {
        input.addProcessor(interfaceStage)
        input.addProcessor(actionStage)
        input.addProcessor(gestureHandler)
        Gdx.input.inputProcessor = input
    }

    fun getActionStage(): ActionStage {
        return actionStage
    }

    fun getInterfaceStage(): InterfaceStage {
        return interfaceStage
    }

    fun toMainMenu(): Unit {
        root.screen = root.mainMenuScreen
    }

    fun beginTravel(): Unit {
        if (!paused) {
            interfaceStage.togglePause()
        }
        getActionStage().disableInteriorOverlay()
        input.clear()
        travelFrames = 500
    }

    fun openTravelPanel(): Unit {
        travelPanelOpen = true
        SystemOperations.setTargetsInRange()
    }

    fun closeTravelPanel(): Unit {
        travelPanelOpen = false
    }

    fun setPause(setting: Boolean): Unit {
        paused = setting
    }

    fun transitionSector(bg0: String, bg1: String, bg2: String, bg3: String, bg2Blur: String,
                         bg3Blur: String): Unit {
        this.bg0 = bg0
        this.bg1 = bg1
        this.bg2 = bg2
        this.bg3 = bg3
        this.bg2Blur = bg2Blur
        this.bg3Blur = bg3Blur

        actionStage.updatePlayerAnimation()
        actionStage.root.addAction(Actions.sequence(
                Actions.delay(3f),
                Actions.fadeOut(1.0f),
                Actions.fadeIn(1.0f)
        ))

        interfaceStage.root.addAction(Actions.sequence(
                Actions.delay(3f),
                Actions.fadeOut(1.0f),
                warpTransitionAction,
                Actions.fadeIn(1.0f),
                Actions.delay(2.75f),
                showViewButtonsAction,
                Actions.delay(1f),
                enableInputAction
        ))
    }

    private fun travel(): Unit {
        if (travelFrames > 400) {
            currentBackground!!.modifySpeed(Vector2(500f - travelFrames, 0f))
        } else if (travelFrames == 400) {
            currentBackground = blurBg
            currentBackground!!.setSpeed(Vector2(2500f, 0f))
        } else if (travelFrames == 90) {
            currentBackground = normalBg
        } else if (travelFrames < 70) {
            currentBackground!!.modifySpeed(Vector2(-(70f - travelFrames), 0f))
        }
        travelFrames -= 1

        if (travelFrames == 0) {
            currentBackground!!.setSpeed(Vector2(2f, 0f))
            actionStage.toggleInteriorOverlay()
        }
    }

    private fun createBackground(): Unit {
        val normalParallaxLayers: Array<ParallaxLayer?> = Array(4, { i -> null})
        val blurParallaxLayers: Array<ParallaxLayer?> = Array(4, { i -> null})

        val layer0: ParallaxLayer = ParallaxLayer(
                TextureRegion(Texture(Gdx.files.internal("backgrounds/$bg0.png"))),
                Vector2(0.0f, 0.0f),
                Vector2(0f, 0f),
                generateRandomColor(),
                Vector2(0f, 0f)
        )
        val layer1: ParallaxLayer = ParallaxLayer(
                TextureRegion(Texture(Gdx.files.internal("backgrounds/$bg1.png"))),
                Vector2(0.0f, 0.0f),
                Vector2(0f, 0f),
                generateRandomColor(),
                Vector2(0f, 0f)
        )

        normalParallaxLayers[0] = layer0
        normalParallaxLayers[1] = layer1
        normalParallaxLayers[2] = ParallaxLayer(
                TextureRegion(Texture(Gdx.files.internal("backgrounds/$bg2.png"))),
                Vector2(0.4f, 0.4f),
                Vector2(0f, 0f),
                Color.WHITE,
                Vector2(0f, 0f)
        )
        normalParallaxLayers[3] = ParallaxLayer(
                TextureRegion(Texture(Gdx.files.internal("backgrounds/$bg3.png"))),
                Vector2(0.8f, 0.8f),
                Vector2(0f, 0f),
                Color.WHITE,
                Vector2(0f, 0f)
        )

        blurParallaxLayers[0] = layer0
        blurParallaxLayers[1] = layer1
        blurParallaxLayers[2] = ParallaxLayer(
                TextureRegion(Texture(Gdx.files.internal("backgrounds/$bg2Blur.png"))),
                Vector2(0.4f, 0.4f),
                Vector2(0f, 0f),
                Color.WHITE,
                Vector2(0f, 0f)
        )
        blurParallaxLayers[3] = ParallaxLayer(
                TextureRegion(Texture(Gdx.files.internal("backgrounds/$bg3Blur.png"))),
                Vector2(0.8f, 0.8f),
                Vector2(0f, 0f),
                Color.WHITE,
                Vector2(0f, 0f)
        )

        normalBg = ParallaxBackground(
                root.spriteBatch,
                normalParallaxLayers,
                Constants.EXACT_X,
                Constants.EXACT_Y,
                Vector2(2f, 0f)
        )

        blurBg = ParallaxBackground(
                root.spriteBatch,
                blurParallaxLayers,
                Constants.EXACT_X,
                Constants.EXACT_Y,
                Vector2(2f, 0f)
        )
    }

    private fun generateRandomColor(): Color {
        val random: Random = Random()
        val r: Float = random.nextFloat() / 2
        val g: Float = random.nextFloat()
        val b: Float = random.nextFloat()

        return Color(r, g, b, 1f)
    }



    /***********************
     * Gesture operations *
     ***********************/
    fun actionPan(dx: Float, dy: Float): Unit {
        val newCameraX: Float = actionStage.camera.position.x - dx
        if (newCameraX >= cameraXmin && newCameraX < cameraXmax) {
            actionStage.camera.translate(-dx, 0f, 0f)
        }
    }



    /**********************
     * Screen Operations *
     **********************/
    override fun render(delta: Float): Unit {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)

        // Handle travel and background animations
        if (travelFrames > 0) {
            travel()
        }

        if (currentBackground != null) {
            currentBackground!!.render(delta)
        }

        // Pause stops combat and crew movement for all players
        if (!paused) {
            if (accumulator > timestep) {
                accumulator -= timestep
                actionStage.combatUpdate()
                CrewOperations.updateCrewmatePositions()
            }
            accumulator += delta
        }

        // Update and render primary game stages
        actionStage.act(delta)
        actionStage.draw()
        actionStage.combatRender(delta)

        interfaceStage.act(delta)
        interfaceStage.draw()

        // Draw map panel shapes or crewmate flags if not currently travelling
        if (travelPanelOpen) {
            SystemOperations.drawShapes()
        } else if (travelFrames == 0) {
            root.spriteBatch.begin()
            CrewOperations.drawCrewmatePositions(delta, root.spriteBatch)
            root.spriteBatch.end()
        }
    }

    override fun show(): Unit {
        enableInput()
    }

    override fun resize(width: Int, height: Int): Unit {
        actionStage.viewport?.update(width, height, true)
        interfaceStage.viewport?.update(width, height, true)
    }

    override fun hide(): Unit {
        Gdx.input.inputProcessor = null
    }

    override fun dispose(): Unit {
        actionStage.dispose()
        interfaceStage.dispose()
    }

    override fun pause(): Unit {}

    override fun resume(): Unit {}



    /***************************
     * Custom Scene2D Actions *
     ***************************/
    private var warpTransitionAction: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            createBackground()
            normalBg!!.setSpeed(Vector2(2500f, 0f))
            blurBg!!.setSpeed(Vector2(2500f, 0f))
            currentBackground = blurBg
            return true
        }
    }
    private var showViewButtonsAction: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            interfaceStage.showUI()
            return true
        }
    }
    private var enableInputAction: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            enableInput()
            return true
        }
    }
}
