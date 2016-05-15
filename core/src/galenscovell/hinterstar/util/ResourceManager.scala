package galenscovell.hinterstar.util

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.graphics.g2d.freetype._
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import galenscovell.hinterstar.graphics.CurrentLocationAnimation


object ResourceManager {
  val assetManager: AssetManager = new AssetManager
  var uiAtlas: TextureAtlas = null
  var textFieldStyle: TextFieldStyle = null
  var labelTinyStyle: LabelStyle = null
  var labelMediumStyle: LabelStyle = null
  var labelDetailStyle: LabelStyle = null
  var labelMenuStyle: LabelStyle = null
  var labelTitleStyle: LabelStyle = null
  var npTest0: NinePatchDrawable = null
  var npTest1: NinePatchDrawable = null
  var npTest2: NinePatchDrawable = null
  var npTest3: NinePatchDrawable = null
  var npTest4: NinePatchDrawable = null
  var npFontCursor: NinePatchDrawable = null
  var npTextFieldBg: NinePatchDrawable = null
  var buttonMenuStyle: TextButtonStyle = null
  var buttonMapStyle0: TextButtonStyle = null
  var buttonMapStyle1: TextButtonStyle = null
  var buttonMapStyle2: TextButtonStyle = null
  var buttonEventStyle: TextButtonStyle = null
  var mapGlow: Sprite = null
  var spTest0: Sprite = null
  var spTest1: Sprite = null
  var spTest2: Sprite = null
  var spTest3: Sprite = null
  var spTest4: Sprite = null
  var currentMarker: CurrentLocationAnimation = null


  def load(): Unit = {
    assetManager.load("atlas/uiAtlas.pack", classOf[TextureAtlas])
    val resolver: FileHandleResolver = new InternalFileHandleResolver
    assetManager.setLoader(classOf[FreeTypeFontGenerator], new FreeTypeFontGeneratorLoader(resolver))
    assetManager.setLoader(classOf[BitmapFont], ".ttf", new FreetypeFontLoader(resolver))

    generateFont("ui/Golden Age.ttf", 12, 0, Color.WHITE, Color.BLACK, "tinyFont.ttf")
    generateFont("ui/Golden Age.ttf", 14, 0, Color.WHITE, Color.BLACK, "smallFont.ttf")
    generateFont("ui/Golden Age.ttf", 16, 0, Color.WHITE, Color.BLACK, "mediumFont.ttf")
    generateFont("ui/Golden Age.ttf", 21, 0, Color.WHITE, Color.BLACK, "largeFont.ttf")
    generateFont("ui/Golden Age.ttf", 36, 0, Color.TEAL, Color.BLACK, "extraLargeFont.ttf")
  }

  def done(): Unit = {
    uiAtlas = assetManager.get("atlas/uiAtlas.pack", classOf[TextureAtlas])
    loadNinepatches()
    loadLabelStyles()
    loadButtonStyles()
    loadSprites()
    loadAnimations()
    loadTextField()
  }

  def dispose(): Unit = {
    assetManager.dispose()
    uiAtlas.dispose()
  }


  /****************************************************
    * Font and Resource Generation
    */
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
    npFontCursor = new NinePatchDrawable(uiAtlas.createPatch("font-cursor-np"))
    npTextFieldBg = new NinePatchDrawable(uiAtlas.createPatch("text-field-bg-np"))
  }

  private def loadLabelStyles(): Unit = {
    labelTinyStyle = new LabelStyle(assetManager.get("tinyFont.ttf", classOf[BitmapFont]), Color.WHITE)
    labelDetailStyle = new LabelStyle(assetManager.get("smallFont.ttf", classOf[BitmapFont]), Color.WHITE)
    labelMediumStyle = new LabelStyle(assetManager.get("mediumFont.ttf", classOf[BitmapFont]), Color.WHITE)
    labelMenuStyle = new LabelStyle(assetManager.get("largeFont.ttf", classOf[BitmapFont]), Color.WHITE)
    labelTitleStyle = new LabelStyle(assetManager.get("extraLargeFont.ttf", classOf[BitmapFont]), Color.WHITE)
  }

  private def loadButtonStyles(): Unit = {
    buttonMenuStyle = new TextButtonStyle(npTest4, npTest3, npTest4, assetManager.get("largeFont.ttf", classOf[BitmapFont]))
    buttonMenuStyle.pressedOffsetY = -2
    buttonMapStyle0 = new TextButtonStyle(npTest1, npTest4, npTest4, assetManager.get("largeFont.ttf", classOf[BitmapFont]))
    buttonMapStyle0.pressedOffsetY = -2
    buttonMapStyle1 = new TextButtonStyle(npTest1, npTest3, npTest3, assetManager.get("largeFont.ttf", classOf[BitmapFont]))
    buttonMapStyle1.pressedOffsetY = -2
    buttonMapStyle2 = new TextButtonStyle(npTest1, npTest2, npTest2, assetManager.get("largeFont.ttf", classOf[BitmapFont]))
    buttonMapStyle2.pressedOffsetY = -2
    buttonEventStyle = new TextButtonStyle(npTest1, npTest2, npTest2, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
    buttonEventStyle.pressedOffsetY = -2
  }

  private def loadSprites(): Unit = {
    mapGlow = new Sprite(uiAtlas.createSprite("map_glow"))
    spTest0 = new Sprite(uiAtlas.createSprite("test-box-0"))
    spTest1 = new Sprite(uiAtlas.createSprite("test-box-1"))
    spTest2 = new Sprite(uiAtlas.createSprite("test-box-2"))
    spTest3 = new Sprite(uiAtlas.createSprite("test-box-3"))
    spTest4 = new Sprite(uiAtlas.createSprite("test-box-4"))
  }

  private def loadAnimations(): Unit = {
    currentMarker = new CurrentLocationAnimation
  }

  private def loadTextField(): Unit = {
    textFieldStyle = new TextFieldStyle(
      assetManager.get("mediumFont.ttf", classOf[BitmapFont]),
      Color.WHITE,  // Font color
      npFontCursor, // Cursor ninepatch
      npTest0,      // Selection ninepatch
      npTextFieldBg // Background ninepatch
    )
    textFieldStyle.background.setLeftWidth(textFieldStyle.background.getLeftWidth + 10)
  }
}

