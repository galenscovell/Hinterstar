package galenscovell.hinterstar.util

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.g2d.freetype.*
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import galenscovell.hinterstar.graphics.CurrentSystemAnimation


object Resources {
    val assetManager: AssetManager = AssetManager()
    lateinit var atlas: TextureAtlas

    lateinit var textFieldStyle: TextFieldStyle

    lateinit var labelTinyStyle: LabelStyle
    lateinit var labelSmallStyle: LabelStyle
    lateinit var labelMediumStyle: LabelStyle
    lateinit var labelLargeStyle: LabelStyle
    lateinit var labelXLargeStyle: LabelStyle

    lateinit var npTest0: NinePatchDrawable
    lateinit var npTest1: NinePatchDrawable
    lateinit var npTest2: NinePatchDrawable
    lateinit var npTest3: NinePatchDrawable
    lateinit var npTest4: NinePatchDrawable
    lateinit var greenButtonNp0: NinePatchDrawable
    lateinit var greenButtonNp1: NinePatchDrawable
    lateinit var blueButtonNp0: NinePatchDrawable
    lateinit var blueButtonNp1: NinePatchDrawable
    lateinit var npFontCursor: NinePatchDrawable
    lateinit var npTextFieldBg: NinePatchDrawable
    lateinit var npHullHealthFill: NinePatchDrawable
    lateinit var npHullHealthEmpty: NinePatchDrawable
    lateinit var npCrewHealthFill: NinePatchDrawable
    lateinit var npCrewHealthEmpty: NinePatchDrawable

    lateinit var buttonMenuStyle: TextButtonStyle
    lateinit var buttonMapStyle0: TextButtonStyle
    lateinit var buttonMapStyle1: TextButtonStyle
    lateinit var buttonMapStyle2: TextButtonStyle
    lateinit var buttonEventStyle: TextButtonStyle
    lateinit var toggleButtonStyle: TextButtonStyle
    lateinit var greenButtonStyle: TextButtonStyle
    lateinit var blueButtonStyle: TextButtonStyle

    lateinit var mapGlow: Sprite
    lateinit var spTest0: Sprite
    lateinit var spTest1: Sprite
    lateinit var spTest2: Sprite
    lateinit var spTest3: Sprite
    lateinit var spTest4: Sprite
    lateinit var spCrewmate: Sprite
    lateinit var spSubsystemMarker: Sprite
    lateinit var spMovementIcon: Sprite

    lateinit var hullHealthBarStyle: ProgressBarStyle
    lateinit var crewHealthBarStyle: ProgressBarStyle
    lateinit var weaponBarStyle: ProgressBarStyle

    lateinit var currentMarker: CurrentSystemAnimation



    fun load(): Unit {
        assetManager.load("atlas/atlas.pack", TextureAtlas::class.java)
        val resolver: FileHandleResolver = InternalFileHandleResolver()
        assetManager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
        assetManager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))

        generateFont("ui/Terminus.ttf", 14, 0f, Color.WHITE, Color.BLACK, "tinyFont.ttf")
        generateFont("ui/Terminus.ttf", 16, 0f, Color.WHITE, Color.BLACK, "smallFont.ttf")
        generateFont("ui/Terminus.ttf", 18, 0f, Color.WHITE, Color.BLACK, "mediumFont.ttf")
        generateFont("ui/Terminus.ttf", 22, 0f, Color.WHITE, Color.BLACK, "largeFont.ttf")
        generateFont("ui/cubeOne.ttf", 48, 0f, Color.TEAL, Color.BLACK, "xLargeFont.ttf")
    }

    fun done(): Unit {
        atlas = assetManager.get("atlas/atlas.pack", TextureAtlas::class.java)
        loadNinepatches()
        loadLabelStyles()
        loadButtonStyles()
        loadSprites()
        loadAnimations()
        loadTextField()
        loadProgressBars()
    }

    fun dispose(): Unit {
        assetManager.dispose()
        atlas.dispose()
    }



    /*********************************
     * Font and Resource Generation *
     ********************************/
    private fun generateFont(fontName: String, size: Int, borderWidth: Float, fontColor: Color, borderColor: Color, outName: String): Unit {
        val params: FreetypeFontLoader.FreeTypeFontLoaderParameter = FreetypeFontLoader.FreeTypeFontLoaderParameter()
                params.fontFileName = fontName
        params.fontParameters.size = size
        params.fontParameters.borderWidth = borderWidth
        params.fontParameters.borderColor = borderColor
        params.fontParameters.color = fontColor
        params.fontParameters.magFilter = TextureFilter.Linear
        params.fontParameters.minFilter = TextureFilter.Linear
        assetManager.load(outName, BitmapFont::class.java, params)
    }

    private fun loadNinepatches(): Unit {
        npTest0 = NinePatchDrawable(atlas.createPatch("test-np-0"))
        npTest1 = NinePatchDrawable(atlas.createPatch("test-np-1"))
        npTest2 = NinePatchDrawable(atlas.createPatch("test-np-2"))
        npTest3 = NinePatchDrawable(atlas.createPatch("test-np-3"))
        npTest4 = NinePatchDrawable(atlas.createPatch("test-np-4"))

        greenButtonNp0 = NinePatchDrawable(atlas.createPatch("green_button0_np"))
        greenButtonNp1 = NinePatchDrawable(atlas.createPatch("green_button1_np"))
        blueButtonNp0 = NinePatchDrawable(atlas.createPatch("blue_button0_np"))
        blueButtonNp1 = NinePatchDrawable(atlas.createPatch("blue_button1_np"))

        npFontCursor = NinePatchDrawable(atlas.createPatch("font-cursor-np"))
        npTextFieldBg = NinePatchDrawable(atlas.createPatch("text-field-bg-np"))

        npHullHealthFill = NinePatchDrawable(atlas.createPatch("health-bar-fill-np"))
        npHullHealthEmpty = NinePatchDrawable(atlas.createPatch("health-bar-empty-np"))
        npCrewHealthFill = NinePatchDrawable(atlas.createPatch("crew-health-bar-fill-np"))
        npCrewHealthEmpty = NinePatchDrawable(atlas.createPatch("crew-health-bar-empty-np"))
    }

    private fun loadLabelStyles(): Unit {
        labelTinyStyle = LabelStyle(assetManager.get("tinyFont.ttf", BitmapFont::class.java), Color.WHITE)
        labelSmallStyle = LabelStyle(assetManager.get("smallFont.ttf", BitmapFont::class.java), Color.WHITE)
        labelMediumStyle = LabelStyle(assetManager.get("mediumFont.ttf", BitmapFont::class.java), Color.WHITE)
        labelLargeStyle = LabelStyle(assetManager.get("largeFont.ttf", BitmapFont::class.java), Color.WHITE)
        labelXLargeStyle = LabelStyle(assetManager.get("xLargeFont.ttf", BitmapFont::class.java), Color.WHITE)
    }

    private fun loadButtonStyles(): Unit {
        buttonMenuStyle = TextButtonStyle(npTest4, npTest3, npTest4, assetManager.get("mediumFont.ttf", BitmapFont::class.java))
        buttonMenuStyle.pressedOffsetY = -2f
        buttonMapStyle0 = TextButtonStyle(npTest1, npTest4, npTest4, assetManager.get("mediumFont.ttf", BitmapFont::class.java))
        buttonMapStyle0.pressedOffsetY = -2f
        buttonMapStyle1 = TextButtonStyle(npTest1, npTest3, npTest3, assetManager.get("mediumFont.ttf", BitmapFont::class.java))
        buttonMapStyle1.pressedOffsetY = -2f
        buttonMapStyle2 = TextButtonStyle(npTest1, npTest2, npTest2, assetManager.get("mediumFont.ttf", BitmapFont::class.java))
        buttonMapStyle2.pressedOffsetY = -2f
        buttonEventStyle = TextButtonStyle(npTest1, npTest2, npTest2, assetManager.get("mediumFont.ttf", BitmapFont::class.java))
        buttonEventStyle.pressedOffsetY = -2f
        toggleButtonStyle = TextButtonStyle(npTest4, npTest3, npTest3, assetManager.get("mediumFont.ttf", BitmapFont::class.java))
        toggleButtonStyle.pressedOffsetY = -2f
        greenButtonStyle = TextButtonStyle(greenButtonNp0, greenButtonNp1, greenButtonNp0, assetManager.get("mediumFont.ttf", BitmapFont::class.java))
//    greenButtonStyle.downFontColor = new Color(0.0f, 0.7f, 0.41f, 1.0f)
//    greenButtonStyle.checkedFontColor = Color.WHITE
        greenButtonStyle.pressedOffsetY = -1f
        blueButtonStyle = TextButtonStyle(blueButtonNp0, blueButtonNp1, blueButtonNp0, assetManager.get("mediumFont.ttf", BitmapFont::class.java))
        //    blueButtonStyle.downFontColor = new Color(0.0f, 0.7f, 0.41f, 1.0f)
        //    blueButtonStyle.checkedFontColor = Color.WHITE
        blueButtonStyle.pressedOffsetY = -1f
    }

    private fun loadSprites(): Unit {
        mapGlow = Sprite(atlas.createSprite("map_glow"))
        spTest0 = Sprite(atlas.createSprite("test-box-0"))
        spTest1 = Sprite(atlas.createSprite("test-box-1"))
        spTest2 = Sprite(atlas.createSprite("test-box-2"))
        spTest3 = Sprite(atlas.createSprite("test-box-3"))
        spTest4 = Sprite(atlas.createSprite("test-box-4"))
        spCrewmate = Sprite(atlas.createSprite("test-crewmate"))
        spSubsystemMarker = Sprite(atlas.createSprite("subsystem_marker"))
        spMovementIcon = Sprite(atlas.createSprite("icon_movement"))
    }

    private fun loadAnimations(): Unit {
        currentMarker = CurrentSystemAnimation()
    }

    private fun loadTextField(): Unit {
        textFieldStyle = TextFieldStyle(
                assetManager.get("mediumFont.ttf", BitmapFont::class.java),
                Color.WHITE,  // Font color
                npFontCursor, // Cursor ninepatch
                npTest0,      // Selection ninepatch
                npTextFieldBg // Background ninepatch
        )
        // This line is a workaround for inner padding in the TextField (10px padding, inner left)
        textFieldStyle.background.leftWidth = textFieldStyle!!.background.leftWidth + 10
    }

    private fun loadProgressBars(): Unit {
        hullHealthBarStyle = ProgressBarStyle(
                npCrewHealthEmpty,
                npCrewHealthFill
        )
        hullHealthBarStyle.knobBefore = npCrewHealthFill

        crewHealthBarStyle = ProgressBarStyle(
                npCrewHealthEmpty,
                npCrewHealthFill
        )
        crewHealthBarStyle.knobBefore = npCrewHealthFill

        weaponBarStyle = ProgressBarStyle(
                npHullHealthEmpty,
                npHullHealthFill
        )
        weaponBarStyle.knobBefore = npHullHealthFill
    }
}

