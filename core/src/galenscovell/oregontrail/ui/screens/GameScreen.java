package galenscovell.oregontrail.ui.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.oregontrail.graphics.*;
import galenscovell.oregontrail.processing.controls.*;
import galenscovell.oregontrail.processing.states.*;
import galenscovell.oregontrail.ui.components.GameStage;
import galenscovell.oregontrail.util.*;

public class GameScreen extends AbstractScreen {
    private final int timestep = 60;
    private double accumulator;
    private State currentState, actionState, menuState;
    private ParallaxBackground currentbackground, normalStars, blurStars;
    private InputMultiplexer input;

    private int travelTicker;
    private boolean traveling;

    public GameScreen(OregonTrailMain root) {
        super(root);
        create();
    }

    @Override
    public void create() {
        this.normalStars = createBackground("bg1", "bg2");
        this.blurStars = createBackground("bg1_blur", "bg2_blur");
        this.currentbackground = normalStars;
        this.stage = new GameStage(this, root.spriteBatch);
        this.actionState = new ActionState(this);
        this.menuState = new MenuState(this);
        this.currentState = actionState;
        setupInput();
    }

    @Override
    public void render(float delta) {
        // Update
        if (accumulator > timestep) {
            accumulator = 0;
            if (!traveling) {
                currentState.update(delta, (GameStage)stage);
                stage.act(delta);
            }
        }
        if (traveling) {
            travel();
        }
        accumulator++;
        // Render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        currentbackground.render(delta);
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
    }

    public void changeState(StateType stateType) {
        currentState.exit();
        if (stateType == StateType.ACTION) {
            currentState = actionState;
        } else if (stateType == StateType.MENU) {
            currentState = menuState;
        }
        currentState.enter();
    }

    public void passInputToState(float x, float y) {
        currentState.handleInput(x, y);
    }

    public StateType getState() {
        return currentState.getStateType();
    }

    public void toMainMenu() {
        root.setScreen(root.mainMenuScreen);
    }

    public ParallaxBackground createBackground(String bg1, String bg2) {
        ParallaxLayer[] parallaxLayers = new ParallaxLayer[2];
        parallaxLayers[0] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg1), new Vector2(0.5f, 0.5f), new Vector2(0, 0));
        parallaxLayers[1] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg2), new Vector2(0.75f, 0.75f), new Vector2(0, 0));
        ParallaxBackground parallaxBackground = new ParallaxBackground(root.spriteBatch, parallaxLayers, Constants.EXACT_X, Constants.EXACT_Y, new Vector2(40, 0));
        return parallaxBackground;
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
            currentbackground = blurStars;
            currentbackground.setSpeed(new Vector2(2500, 0));
        } else if (travelTicker == 120) {
            currentbackground = normalStars;
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
}
