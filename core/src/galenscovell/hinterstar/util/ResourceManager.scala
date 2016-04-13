package galenscovell.hinterstar.util

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.graphics.g2d.freetype._
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import galenscovell.hinterstar.graphics.CurrentLocationAnimation


object ResourceManager {
  val assetManager: AssetManager = new AssetManager
  var uiAtlas: TextureAtlas = null
  var label_tinyStyle: LabelStyle = null
  var label_mediumStyle: LabelStyle = null
  var label_detailStyle: LabelStyle = null
  var label_menuStyle: LabelStyle = null
  var label_titleStyle: LabelStyle = null
  var np_test0: NinePatchDrawable = null
  var np_test1: NinePatchDrawable = null
  var np_test2: NinePatchDrawable = null
  var np_test3: NinePatchDrawable = null
  var np_test4: NinePatchDrawable = null
  var button_menuStyle: TextButtonStyle = null
  var button_mapStyle0: TextButtonStyle = null
  var button_mapStyle1: TextButtonStyle = null
  var button_mapStyle2: TextButtonStyle = null
  var button_eventStyle: TextButtonStyle = null
  var mapGlow: Sprite = null
  var sp_test0: Sprite = null
  var sp_test1: Sprite = null
  var sp_test2: Sprite = null
  var sp_test3: Sprite = null
  var sp_test4: Sprite = null
  var currentMarker: CurrentLocationAnimation = null
  var prefs: Preferences = null


  def load(): Unit = {
    assetManager.load("atlas/uiAtlas.pack", classOf[TextureAtlas])
    val resolver: FileHandleResolver = new InternalFileHandleResolver
    assetManager.setLoader(classOf[FreeTypeFontGenerator], new FreeTypeFontGeneratorLoader(resolver))
    assetManager.setLoader(classOf[BitmapFont], ".ttf", new FreetypeFontLoader(resolver))

    generateFont("ui/kenvector_future_thin.ttf", 12, 0, Color.WHITE, Color.BLACK, "tinyFont.ttf")
    generateFont("ui/kenvector_future_thin.ttf", 14, 0, Color.WHITE, Color.BLACK, "smallFont.ttf")
    generateFont("ui/kenvector_future_thin.ttf", 16, 0, Color.WHITE, Color.BLACK, "mediumFont.ttf")
    generateFont("ui/kenvector_future.ttf", 21, 0, Color.WHITE, Color.BLACK, "largeFont.ttf")
    generateFont("ui/kenvector_future.ttf", 36, 0, Color.TEAL, Color.BLACK, "extraLargeFont.ttf")
  }

  def done(): Unit = {
    uiAtlas = assetManager.get("atlas/uiAtlas.pack", classOf[TextureAtlas])
    loadNinepatches()
    loadLabelStyles()
    loadButtonStyles()
    loadSprites()
    loadAnimations()

    // Load user preferences
//    prefs = Gdx.app.getPreferences("trail_settings");
//    prefs.putBoolean("sfx", true);
//    prefs.putBoolean("music", true);
//    prefs.flush();
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
    np_test0 = new NinePatchDrawable(uiAtlas.createPatch("test-np-0"))
    np_test1 = new NinePatchDrawable(uiAtlas.createPatch("test-np-1"))
    np_test2 = new NinePatchDrawable(uiAtlas.createPatch("test-np-2"))
    np_test3 = new NinePatchDrawable(uiAtlas.createPatch("test-np-3"))
    np_test4 = new NinePatchDrawable(uiAtlas.createPatch("test-np-4"))
  }

  private def loadLabelStyles(): Unit = {
    label_tinyStyle = new LabelStyle(assetManager.get("tinyFont.ttf", classOf[BitmapFont]), Color.WHITE)
    label_detailStyle = new LabelStyle(assetManager.get("smallFont.ttf", classOf[BitmapFont]), Color.WHITE)
    label_mediumStyle = new LabelStyle(assetManager.get("mediumFont.ttf", classOf[BitmapFont]), Color.WHITE)
    label_menuStyle = new LabelStyle(assetManager.get("largeFont.ttf", classOf[BitmapFont]), Color.WHITE)
    label_titleStyle = new LabelStyle(assetManager.get("extraLargeFont.ttf", classOf[BitmapFont]), Color.WHITE)
  }

  private def loadButtonStyles(): Unit = {
    button_menuStyle = new TextButtonStyle(np_test4, np_test3, np_test4, assetManager.get("largeFont.ttf", classOf[BitmapFont]))
    button_menuStyle.pressedOffsetY = -1
    button_mapStyle0 = new TextButtonStyle(np_test1, np_test4, np_test4, assetManager.get("largeFont.ttf", classOf[BitmapFont]))
    button_mapStyle0.pressedOffsetY = -1
    button_mapStyle1 = new TextButtonStyle(np_test1, np_test3, np_test3, assetManager.get("largeFont.ttf", classOf[BitmapFont]))
    button_mapStyle1.pressedOffsetY = -1
    button_mapStyle2 = new TextButtonStyle(np_test1, np_test2, np_test2, assetManager.get("largeFont.ttf", classOf[BitmapFont]))
    button_mapStyle2.pressedOffsetY = -1
    button_eventStyle = new TextButtonStyle(np_test1, np_test2, np_test2, assetManager.get("mediumFont.ttf", classOf[BitmapFont]))
    button_eventStyle.pressedOffsetY = -1
  }

  private def loadSprites(): Unit = {
    mapGlow = new Sprite(uiAtlas.createSprite("map_glow"))
    sp_test0 = new Sprite(uiAtlas.createSprite("test-box-0"))
    sp_test1 = new Sprite(uiAtlas.createSprite("test-box-1"))
    sp_test2 = new Sprite(uiAtlas.createSprite("test-box-2"))
    sp_test3 = new Sprite(uiAtlas.createSprite("test-box-3"))
    sp_test4 = new Sprite(uiAtlas.createSprite("test-box-4"))
  }

  private def loadAnimations(): Unit = {
    currentMarker = new CurrentLocationAnimation
  }
}

