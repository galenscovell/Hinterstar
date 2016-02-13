package galenscovell.oregontrail.ui.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.oregontrail.graphics.*;
import galenscovell.oregontrail.processing.controls.*;
import galenscovell.oregontrail.ui.components.*;
import galenscovell.oregontrail.util.*;

public class GameScreen extends AbstractScreen {
    private InputMultiplexer input;
    private int travelTicker;
    private boolean traveling;

    public ParallaxBackground currentbackground, normalBg, blurBg;
    public String bg0, bg1, bg2, bg0Blur, bg1Blur, bg2Blur;
    public LocationPanel locationPanel;

    public GameScreen(OregonTrailMain root) {
        super(root);
        create();
    }

    @Override
    public void create() {
        Repository.setup(this);
        this.stage = new GameStage(this, root.spriteBatch);
        // Create initial background
        normalBg = createBackground("purple_bg", "bg1", "bg2");
        blurBg = createBackground("purple_bg", "bg1_blur", "bg2_blur");
        currentbackground = normalBg;
        setupInput();
    }

    @Override
    public void render(float delta) {
        stage.act();
        if (traveling) {
            travel();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (currentbackground != null) {
            currentbackground.render(delta);
        }
        stage.draw();
        Repository.drawPath();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
    }

    public Stage getGameStage() {
        return stage;
    }

    public void toMainMenu() {
        root.setScreen(root.mainMenuScreen);
    }

    public void setTravel() {
        traveling = true;
        travelTicker = 600;
    }

    public boolean isTraveling() {
        return traveling;
    }

    public void travel() {
        if (travelTicker > 500) {
            currentbackground.modifySpeed(new Vector2((600 - travelTicker), 0));
        } else if (travelTicker == 500) {
            currentbackground = blurBg;
            currentbackground.setSpeed(new Vector2(2500, 0));
        } else if (travelTicker == 120) {
            currentbackground = normalBg;
        } else if (travelTicker < 70) {
            currentbackground.modifySpeed(new Vector2(-(70 - travelTicker), 0));
        }
        travelTicker--;

        if (travelTicker == 0) {
            currentbackground.setSpeed(new Vector2(40, 0));
            traveling = false;
        }
    }

    private void setupInput() {
        this.input = new InputMultiplexer();
        input.addProcessor(stage);
        input.addProcessor(new InputHandler(this));
        Gdx.input.setInputProcessor(input);
    }

    public void setBackground(String bg0, String bg1, String bg2, String bg0Blur, String bg1Blur, String bg2Blur) {
        this.bg0 = bg0;
        this.bg1 = bg1;
        this.bg2 = bg2;
        this.bg0Blur = bg0Blur;
        this.bg1Blur = bg1Blur;
        this.bg2Blur = bg2Blur;

        String[] locationDetail = Repository.currentLocation.getDetails();
        this.locationPanel = new LocationPanel(locationDetail[0], locationDetail[1]);

        stage.getRoot().addAction(Actions.sequence(Actions.delay(4), Actions.fadeOut(1.0f), transition, Actions.fadeIn(1.5f), Actions.delay(3), Actions.removeActor(locationPanel)));
    }

    private ParallaxBackground createBackground(String bg0, String bg1, String bg2) {
        ParallaxBackground parallaxBackground;

        if (bg0.length() > 0) {
            // Three-layered background
            ParallaxLayer[] parallaxLayers = new ParallaxLayer[3];
            parallaxLayers[0] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg0), new Vector2(0.02f, 0.02f), new Vector2(0, 0));
            parallaxLayers[1] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg1), new Vector2(0.5f, 0.5f), new Vector2(0, 0));
            parallaxLayers[2] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg2), new Vector2(0.75f, 0.75f), new Vector2(0, 0));
            parallaxBackground = new ParallaxBackground(root.spriteBatch, parallaxLayers, Constants.EXACT_X, Constants.EXACT_Y, new Vector2(40, 0));
        } else {
            // Two-layered background
            ParallaxLayer[] parallaxLayers = new ParallaxLayer[2];
            parallaxLayers[0] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg1), new Vector2(0.5f, 0.5f), new Vector2(0, 0));
            parallaxLayers[1] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg2), new Vector2(0.75f, 0.75f), new Vector2(0, 0));
            parallaxBackground = new ParallaxBackground(root.spriteBatch, parallaxLayers, Constants.EXACT_X, Constants.EXACT_Y, new Vector2(40, 0));
        }
        return parallaxBackground;
    }

    Action transition = new Action() {
        public boolean act(float delta) {
            normalBg = createBackground(bg0, bg1, bg2);
            normalBg.setSpeed(new Vector2(2500, 0));
            blurBg = createBackground(bg0Blur, bg1Blur, bg2Blur);
            blurBg.setSpeed(new Vector2(2500, 0));
            currentbackground = blurBg;
            stage.addActor(locationPanel);
            Repository.clearSelection();
            return true;
        }
    };
}
