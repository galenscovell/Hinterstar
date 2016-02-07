package galenscovell.oregontrail.ui.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.oregontrail.graphics.*;
import galenscovell.oregontrail.processing.controls.*;
import galenscovell.oregontrail.ui.components.GameStage;
import galenscovell.oregontrail.util.*;

public class GameScreen extends AbstractScreen {
    public ParallaxBackground currentbackground, normalBg, blurBg;
    public String bg1, bg2, bg1Blur, bg2Blur;
    private InputMultiplexer input;

    private int travelTicker;
    private boolean traveling;

    public GameScreen(OregonTrailMain root) {
        super(root);
        create();
    }

    @Override
    public void create() {
        this.stage = new GameStage(this, root.spriteBatch);
        setBackground("bg1", "bg2", "bg1_blur", "bg2_blur");
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
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
    }

    public void toMainMenu() {
        root.setScreen(root.mainMenuScreen);
    }

    public void setBackground(String bg1, String bg2, String bg1Blur, String bg2Blur) {
        this.bg1 = bg1;
        this.bg2 = bg2;
        this.bg1Blur = bg1Blur;
        this.bg2Blur = bg2Blur;
        stage.getRoot().addAction(Actions.sequence(Actions.fadeOut(0.5f), transition, Actions.fadeIn(0.5f)));
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
            currentbackground.setSpeed(new Vector2(2500, 0));
        } else if (travelTicker < 70) {
            currentbackground.modifySpeed(new Vector2(-(70 - travelTicker), 0));
        }
        travelTicker--;

        if (travelTicker == 0) {
            // Set new location background and speed
            currentbackground.setSpeed(new Vector2(40, 0));
            traveling = false;
        }
    }

    private void setupInput() {
        this.input = new InputMultiplexer();
        input.addProcessor(stage);
        input.addProcessor(new InputHandler(this));
        input.addProcessor(new GestureDetector(new GestureHandler(this)));
        Gdx.input.setInputProcessor(input);
    }

    private ParallaxBackground createBackground(String bg1, String bg2) {
        ParallaxLayer[] parallaxLayers = new ParallaxLayer[2];
        parallaxLayers[0] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg1), new Vector2(0.5f, 0.5f), new Vector2(0, 0));
        parallaxLayers[1] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg2), new Vector2(0.75f, 0.75f), new Vector2(0, 0));
        ParallaxBackground parallaxBackground = new ParallaxBackground(root.spriteBatch, parallaxLayers, Constants.EXACT_X, Constants.EXACT_Y, new Vector2(40, 0));
        return parallaxBackground;
    }

    Action transition = new Action() {
        public boolean act(float delta) {
            normalBg = createBackground(bg1, bg2);
            blurBg = createBackground(bg1Blur, bg2Blur);
            currentbackground = normalBg;
            return true;
        }
    };
}
