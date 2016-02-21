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
    public static LabelStyle label_tinyStyle,
            label_mediumStyle,
            label_detailStyle,
            label_menuStyle,
            label_titleStyle;
    public static NinePatchDrawable np_test0,
            np_test1,
            np_test2,
            np_test3,
            np_test4;
    public static TextButtonStyle button_menuStyle,
            button_mapStyle0,
            button_mapStyle1,
            button_mapStyle2;
    public static Sprite mapGlow,
            sp_test0,
            sp_test1,
            sp_test2,
            sp_test3,
            sp_test4;
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

        generateFont("ui/kenvector_future_thin.ttf", 12, 0, Color.WHITE, Color.BLACK, "tinyFont.ttf");
        generateFont("ui/kenvector_future_thin.ttf", 14, 0, Color.WHITE, Color.BLACK, "smallFont.ttf");
        generateFont("ui/kenvector_future_thin.ttf", 16, 0, Color.WHITE, Color.BLACK, "mediumFont.ttf");
        generateFont("ui/kenvector_future.ttf", 21, 0, Color.WHITE, Color.BLACK, "largeFont.ttf");
        generateFont("ui/kenvector_future.ttf", 36, 0, Color.TEAL, Color.BLACK, "extraLargeFont.ttf");
    }

    public static void done() {
        uiAtlas = assetManager.get("atlas/uiAtlas.pack", TextureAtlas.class);

        loadNinepatches();
        loadLabelStyles();
        loadButtonStyles();
        loadSprites();
        loadAnimations();

//        // Load user preferences
//        prefs = Gdx.app.getPreferences("trail_settings");
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
        np_test0 = new NinePatchDrawable(uiAtlas.createPatch("test-np-0"));
        np_test1 = new NinePatchDrawable(uiAtlas.createPatch("test-np-1"));
        np_test2 = new NinePatchDrawable(uiAtlas.createPatch("test-np-2"));
        np_test3 = new NinePatchDrawable(uiAtlas.createPatch("test-np-3"));
        np_test4 = new NinePatchDrawable(uiAtlas.createPatch("test-np-4"));
    }

    private static void loadLabelStyles() {
        label_tinyStyle = new LabelStyle(assetManager.get("tinyFont.ttf", BitmapFont.class), Color.WHITE);
        label_detailStyle = new LabelStyle(assetManager.get("smallFont.ttf", BitmapFont.class), Color.WHITE);
        label_mediumStyle = new LabelStyle(assetManager.get("mediumFont.ttf", BitmapFont.class), Color.WHITE);
        label_menuStyle = new LabelStyle(assetManager.get("largeFont.ttf", BitmapFont.class), Color.WHITE);
        label_titleStyle = new LabelStyle(assetManager.get("extraLargeFont.ttf", BitmapFont.class), Color.WHITE);
    }

    private static void loadButtonStyles() {
        button_menuStyle = new TextButtonStyle(np_test4, np_test3, np_test4, assetManager.get("largeFont.ttf", BitmapFont.class));
        button_menuStyle.pressedOffsetY = -2;

        button_mapStyle0 = new TextButtonStyle(np_test1, np_test4, np_test4, assetManager.get("largeFont.ttf", BitmapFont.class));
        button_mapStyle0.pressedOffsetY = -2;
        button_mapStyle1 = new TextButtonStyle(np_test1, np_test3, np_test3, assetManager.get("largeFont.ttf", BitmapFont.class));
        button_mapStyle1.pressedOffsetY = -2;
        button_mapStyle2 = new TextButtonStyle(np_test1, np_test2, np_test2, assetManager.get("largeFont.ttf", BitmapFont.class));
        button_mapStyle2.pressedOffsetY = -2;
    }

    private static void loadSprites() {
        mapGlow = new Sprite(uiAtlas.createSprite("map_glow"));

        sp_test0 = new Sprite(uiAtlas.createSprite("test-box-0"));
        sp_test1 = new Sprite(uiAtlas.createSprite("test-box-1"));
        sp_test2 = new Sprite(uiAtlas.createSprite("test-box-2"));
        sp_test3 = new Sprite(uiAtlas.createSprite("test-box-3"));
        sp_test4 = new Sprite(uiAtlas.createSprite("test-box-4"));
    }

    private static void loadAnimations() {
        currentMarker = new CurrentLocationAnimation();
    }
}
