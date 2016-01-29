package galenscovell.oregontrail.util;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import galenscovell.oregontrail.graphics.CurrentLocationAnimation;

public class ResourceManager {
    public static AssetManager assetManager;
    public static TextureAtlas uiAtlas;
    public static LabelStyle label_tinyStyle, label_mediumStyle, label_detailStyle, label_menuStyle, label_titleStyle;
    public static NinePatchDrawable buttonUp, buttonDown, mapback;
    public static TextButtonStyle button_fullStyle;
    public static Sprite mapGlow, mapSelect;
    public static CurrentLocationAnimation currentMarker;
    public static Preferences prefs;

    public static void create() {
        assetManager = new AssetManager();
        load();
    }

    public static void load() {
        assetManager.load("atlas/uiAtlas.pack", TextureAtlas.class);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        generateFont("ui/kenpixel_blocks.ttf", 12, 0, Color.WHITE, Color.BLACK, "tinyFont.ttf");
        generateFont("ui/kenpixel_blocks.ttf", 14, 0, Color.WHITE, Color.BLACK, "smallFont.ttf");
        generateFont("ui/kenpixel_blocks.ttf", 16, 0, Color.WHITE, Color.BLACK, "mediumFont.ttf");
        generateFont("ui/kenpixel_blocks.ttf", 24, 0, Color.WHITE, Color.BLACK, "largeFont.ttf");
        generateFont("ui/kenpixel_blocks.ttf", 36, 1, Color.TEAL, Color.BLACK, "extraLargeFont.ttf");
    }

    public static void done() {
        uiAtlas = assetManager.get("atlas/uiAtlas.pack", TextureAtlas.class);

        loadNinepatches();
        loadLabelStyles();
        loadButtonStyles();
        loadSprites();
        loadAnimations();

//        // Load user preferences
//        prefs = Gdx.app.getPreferences("flicker_settings");
//        prefs.putBoolean("sfx", true);
//        prefs.putBoolean("music", true);
//        prefs.flush();
    }

    public static void dispose() {
        assetManager.dispose();
        uiAtlas.dispose();
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
        buttonUp = new NinePatchDrawable(uiAtlas.createPatch("buttonup"));
        buttonDown = new NinePatchDrawable(uiAtlas.createPatch("buttondown"));
        mapback = new NinePatchDrawable(uiAtlas.createPatch("map_back"));
    }

    private static void loadLabelStyles() {
        label_tinyStyle = new LabelStyle(assetManager.get("tinyFont.ttf", BitmapFont.class), Color.WHITE);
        label_detailStyle = new LabelStyle(assetManager.get("smallFont.ttf", BitmapFont.class), Color.WHITE);
        label_mediumStyle = new LabelStyle(assetManager.get("mediumFont.ttf", BitmapFont.class), Color.WHITE);
        label_menuStyle = new LabelStyle(assetManager.get("largeFont.ttf", BitmapFont.class), Color.WHITE);
        label_titleStyle = new LabelStyle(assetManager.get("extraLargeFont.ttf", BitmapFont.class), Color.WHITE);
    }

    private static void loadButtonStyles() {
        button_fullStyle = new TextButtonStyle(buttonUp, buttonDown, buttonUp, assetManager.get("largeFont.ttf", BitmapFont.class));
        button_fullStyle.pressedOffsetY = -2;
    }

    private static void loadSprites() {
        mapGlow = new Sprite(uiAtlas.createSprite("map_glow"));
        mapSelect = new Sprite(uiAtlas.createSprite("map_select"));
//        highlightBlue = new Sprite(uiAtlas.createSprite("highlight_blue"));
//        highlightBlue.flip(false, true);
//        highlightOrange = new Sprite(uiAtlas.createSprite("highlight_orange"));
//        highlightOrange.flip(false, true);
    }

    private static void loadAnimations() {
        currentMarker = new CurrentLocationAnimation();
    }
}
