package galenscovell.hinterstar.util

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.graphics.g2d.freetype._
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import galenscovell.hinterstar.graphics.CurrentSystemAnimation


object Resources {
  val assetManager: AssetManager = new AssetManager

  var uiAtlas, environmentAtlas, shipAtlas, crewAtlas: TextureAtlas = _

  var textFieldStyle: TextFieldStyle = _

  var labelTinyStyle, labelMediumStyle, labelDetailStyle, labelMenuStyle,
      labelTitleStyle: LabelStyle = _

  var npTest0, npTest1, npTest2, npTest3, npTest4, greenButtonNp0,
      greenButtonNp1, blueButtonNp0, blueButtonNp1,
      npFontCursor, npTextFieldBg, npHealthFill, npHealthEmpty: NinePatchDrawable = _

  var buttonMenuStyle, buttonMapStyle0, buttonMapStyle1, buttonMapStyle2,
      buttonEventStyle, toggleButtonStyle, greenButtonStyle, blueButtonStyle: TextButtonStyle = _

  var mapGlow, spTest0, spTest1, spTest2, spTest3, spTest4,
      spCrewmate, spSubsystemMarker: Sprite = _

  var healthBarStyle: ProgressBarStyle = _

  var currentMarker: CurrentSystemAnimation = _


  def load(): Unit = {
    assetManager.load("atlas/uiAtlas.pack", classOf[TextureAtlas])
    assetManager.load("atlas/environmentAtlas.pack", classOf[TextureAtlas])
    assetManager.load("atlas/shipAtlas.pack", classOf[TextureAtlas])
    assetManager.load("atlas/crewAtlas.pack", classOf[TextureAtlas])
    val resolver: FileHandleResolver = new InternalFileHandleResolver
    assetManager.setLoader(classOf[FreeTypeFontGenerator], new FreeTypeFontGeneratorLoader(resolver))
    assetManager.setLoader(classOf[BitmapFont], ".ttf", new FreetypeFontLoader(resolver))

    generateFont("ui/Terminus.ttf", 14, 0, Color.WHITE, Color.BLACK, "tinyFont.ttf")
    generateFont("ui/Terminus.ttf", 16, 0, Color.WHITE, Color.BLACK, "smallFont.ttf")
    generateFont("ui/Terminus.ttf", 18, 0, Color.WHITE, Color.BLACK, "mediumFont.ttf")
    generateFont("ui/Terminus.ttf", 22, 0, Color.WHITE, Color.BLACK, "largeFont.ttf")
    generateFont("ui/cubeOne.ttf", 48, 0, Color.TEAL, Color.BLACK, "titleFont.ttf")
  }

  def done(): Unit = {
    uiAtlas = assetManager.get("atlas/uiAtlas.pack", classOf[TextureAtlas])
    environmentAtlas = assetManager.get("atlas/environmentAtlas.pack", classOf[TextureAtlas])
    shipAtlas = assetManager.get("atlas/shipAtlas.pack", classOf[TextureAtlas])
    crewAtlas = assetManager.get("atlas/crewAtlas.pack", classOf[TextureAtlas])
    loadNinepatches()
    loadLabelStyles()
    loadButtonStyles()
    loadSprites()
    loadAnimations()
    loadTextField()
    loadProgressBars()
  }

  def dispose(): Unit = {
    assetManager.dispose()
    uiAtlas.dispose()
    environmentAtlas.dispose()
    shipAtlas.dispose()
    crewAtlas.dispose()
  }



  /*********************************
    * Font and Resource Generation *
    ********************************/
  private def generateFont(fontName: String, size: Int, borderWidth: Int, fontColor: Color, borderColor: Color, outName: String): Unit = {
    val params: FreetypeFontLoader.FreeTypeFontLoaderParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter
    params.fontFileName = fontName
    params.fontParameters.size = size
    params.fontParameters.borderWidth = borderWidth
    params.fontParameters.borderColor = borderColor
    params.fontParameters.color = fontColor
    params.fontParameters.magFilter = TextureFilter.Linear
    params.fontParameters.minFilter = TextureFilter.Linear
    assetManager.load(outName, classOf[BitmapFont], params)
  }

  private def loadNinepatches(): Unit = {
    npTest0 = new NinePatchDrawable(uiAtlas.createPatch("test-np-0"))
    npTest1 = new NinePatchDrawable(uiAtlas.createPatch("test-np-1"))
    npTest2 = new NinePatchDrawable(uiAtlas.createPatch("test-np-2"))
    npTest3 = new NinePatchDrawable(uiAtlas.createPatch("test-np-3"))
    npTest4 = new NinePatchDrawable(uiAtlas.createPatch("test-np-4"))

    greenButtonNp0 = new NinePatchDrawable(uiAtlas.createPatch("green_button0_np"))
    greenButtonNp1 = new NinePatchDrawable(uiAtlas.createPatch("green_button1_np"))
    blueButtonNp0 = new NinePatchDrawable(uiAtlas.createPatch("blue_button0_np"))
    blueButtonNp1 = new NinePatchDrawable(uiAtlas.createPatch("blue_button1_np"))

    npFontCursor = new NinePatchDrawable(uiAtlas.createPatch("font-cursor-np"))
    npTextFieldBg = new NinePatchDrawable(uiAtlas.createPatch("text-field-bg-np"))

    npHealthFill = new NinePatchDrawable(uiAtlas.createPatch("health-bar-fill-np"))
    npHealthEmpty = new NinePatchDrawable(uiAtlas.createPatch("health-bar-empty-np"))
  }

  private def loadLabelStyles(): Unit = {
    labelTinyStyle = new LabelStyle(assetManager.get("tinyFont.ttf", classOf[BitmapFont]), Color.WHITE)
    labelDetailStyle = new LabelStyle(assetManager.get("smallFont.ttf", classOf[BitmapFont]), Color.WHITE)
    labelMediumStyle = new LabelStyle(assetManager.get("mediumFont.ttf", classOf[BitmapFont]), Color.WHITE)
    labelMenuStyle = new LabelStyle(assetManager.get("largeFont.ttf", classOf[BitmapFont]), Color.WHITE)
    labelTitleStyle = new LabelStyle(assetManager.get("titleFont.ttf", classOf[BitmapFont]), Color.WHITE)
  }

  private def loadButtonStyles(): Unit = {
    buttonMenuStyle = new TextButtonStyle(npTest4, npTest3, npTest4, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
    buttonMenuStyle.pressedOffsetY = -2
    buttonMapStyle0 = new TextButtonStyle(npTest1, npTest4, npTest4, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
    buttonMapStyle0.pressedOffsetY = -2
    buttonMapStyle1 = new TextButtonStyle(npTest1, npTest3, npTest3, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
    buttonMapStyle1.pressedOffsetY = -2
    buttonMapStyle2 = new TextButtonStyle(npTest1, npTest2, npTest2, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
    buttonMapStyle2.pressedOffsetY = -2
    buttonEventStyle = new TextButtonStyle(npTest1, npTest2, npTest2, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
    buttonEventStyle.pressedOffsetY = -2
    toggleButtonStyle = new TextButtonStyle(npTest4, npTest3, npTest3, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
    toggleButtonStyle.pressedOffsetY = -2
    greenButtonStyle = new TextButtonStyle(greenButtonNp0, greenButtonNp1, greenButtonNp0, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
//    greenButtonStyle.downFontColor = new Color(0.0f, 0.7f, 0.41f, 1.0f)
//    greenButtonStyle.checkedFontColor = Color.WHITE
    greenButtonStyle.pressedOffsetY = -1
    blueButtonStyle = new TextButtonStyle(blueButtonNp0, blueButtonNp1, blueButtonNp0, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
    //    blueButtonStyle.downFontColor = new Color(0.0f, 0.7f, 0.41f, 1.0f)
    //    blueButtonStyle.checkedFontColor = Color.WHITE
    blueButtonStyle.pressedOffsetY = -1
  }

  private def loadSprites(): Unit = {
    mapGlow = new Sprite(uiAtlas.createSprite("map_glow"))
    spTest0 = new Sprite(uiAtlas.createSprite("test-box-0"))
    spTest1 = new Sprite(uiAtlas.createSprite("test-box-1"))
    spTest2 = new Sprite(uiAtlas.createSprite("test-box-2"))
    spTest3 = new Sprite(uiAtlas.createSprite("test-box-3"))
    spTest4 = new Sprite(uiAtlas.createSprite("test-box-4"))
    spCrewmate = new Sprite(crewAtlas.createSprite("test-crewmate"))
    spSubsystemMarker = new Sprite(uiAtlas.createSprite("subsystem_marker"))
  }

  private def loadAnimations(): Unit = {
    currentMarker = new CurrentSystemAnimation
  }

  private def loadTextField(): Unit = {
    textFieldStyle = new TextFieldStyle(
      assetManager.get("mediumFont.ttf", classOf[BitmapFont]),
      Color.WHITE,  // Font color
      npFontCursor, // Cursor ninepatch
      npTest0,      // Selection ninepatch
      npTextFieldBg // Background ninepatch
    )
    // This line is a workaround for inner padding in the TextField (10px padding, inner left)
    textFieldStyle.background.setLeftWidth(textFieldStyle.background.getLeftWidth + 10)
  }

  private def loadProgressBars(): Unit = {
    healthBarStyle = new ProgressBarStyle(
      npHealthEmpty,
      npHealthFill
    )
    healthBarStyle.knobBefore = npHealthFill
  }
}

