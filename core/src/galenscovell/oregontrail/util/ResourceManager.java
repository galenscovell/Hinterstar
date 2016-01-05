package galenscovell.oregontrail.util;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class ResourceManager {
    public static AssetManager assetManager;
    public static TextureAtlas uiAtlas;
    public static LabelStyle label_menuStyle, label_titleStyle;
    public static TextButtonStyle button_fullStyle;
    public static Preferences prefs;

    public static void create() {
        assetManager = new AssetManager();
        load();
    }

    public static void load() {
//        assetManager.load("atlas/uiAtlas.pack", TextureAtlas.class);
    }

    public static void done() {
//        uiAtlas = assetManager.get("atlas/uiAtlas.pack", TextureAtlas.class);
//
//        loadNinepatches();
//        loadLabelStyles();
//        loadButtonStyles();
//        loadSprites();
//
//        // Load user preferences
//        prefs = Gdx.app.getPreferences("flicker_settings");
//        prefs.putBoolean("sfx", true);
//        prefs.putBoolean("music", true);
//        prefs.flush();
    }

    public static void dispose() {
        assetManager.dispose();
//        uiAtlas.dispose();
    }

    /***************************************************
     * Font and Resource Generation
     */
    private static void generateFont(String fontName, int size, int borderWidth, Color fontColor, Color borderColor, String outName) {
        FreeTypeFontLoaderParameter params = new FreeTypeFontLoaderParameter();
        params.fontFileName = fontName;
        params.fontParameters.size = size;
        params.fontParameters.borderWidth = borderWidth;
        params.fontParameters.borderColor = borderColor;
        params.fontParameters.color = fontColor;
        params.fontParameters.magFilter = TextureFilter.Linear;
        params.fontParameters.minFilter = TextureFilter.Linear;
        assetManager.load(outName, BitmapFont.class, params);
    }

    private static void loadNinepatches() {
//        frameUp = new NinePatchDrawable(uiAtlas.createPatch("frameup_brown"));
    }

    private static void loadLabelStyles() {
        label_menuStyle = new LabelStyle(assetManager.get("largeFont.ttf", BitmapFont.class), Color.WHITE);
        label_titleStyle = new LabelStyle(assetManager.get("extraLargeFont.ttf", BitmapFont.class), Color.WHITE);
    }

    private static void loadButtonStyles() {
//        button_fullStyle = new TextButtonStyle(buttonUp, buttonDown, buttonUp, assetManager.get("mediumFont.ttf", BitmapFont.class));
//        button_fullStyle.pressedOffsetY = -2;
    }

    private static void loadSprites() {
//        highlightBlue = new Sprite(uiAtlas.createSprite("highlight_blue"));
//        highlightBlue.flip(false, true);
//        highlightOrange = new Sprite(uiAtlas.createSprite("highlight_orange"));
//        highlightOrange.flip(false, true);
    }
}
