package galenscovell.oregontrail.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.oregontrail.graphics.*;
import galenscovell.oregontrail.processing.states.*;
import galenscovell.oregontrail.ui.components.GameStage;
import galenscovell.oregontrail.util.*;

public class GameScreen extends AbstractScreen {
    private final int timestep = 15;
    private double accumulator;
    private State currentState, actionState, menuState;
    private ParallaxBackground parallaxBackground;

    public GameScreen(OregonTrailMain root) {
        super(root);
    }

    @Override
    public void create() {
        ParallaxLayer[] parallaxLayers = new ParallaxLayer[2];
        parallaxLayers[0] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion("bg1"), new Vector2(0.5f, 0.5f), new Vector2(0, 0));
        parallaxLayers[1] = new ParallaxLayer(ResourceManager.uiAtlas.findRegion("bg2"), new Vector2(0.75f, 0.75f), new Vector2(0, 500));
        this.parallaxBackground = new ParallaxBackground(root.spriteBatch, parallaxLayers, Constants.EXACT_X, Constants.EXACT_Y, new Vector2(25, 0));

        this.stage = new GameStage(this, root.spriteBatch);
        // this.actionState = new ActionState(this);
        // this.menuState = new MenuState(this);
        // this.currentState = actionState;
    }

    @Override
    public void render(float delta) {
        // Update
        if (accumulator > timestep) {
            accumulator = 0;
            // currentState.update(delta);
            stage.act(delta);
        }
        accumulator++;
        // Render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        parallaxBackground.render(delta);
        stage.draw();
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
}
