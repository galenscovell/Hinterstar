package galenscovell.oregontrail.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.oregontrail.processing.states.*;
import galenscovell.oregontrail.ui.components.GameStage;

public class GameScreen extends AbstractScreen {
    private final int timestep = 15;
    private int accumulator;
    private State currentState, actionState, menuState;

    public GameScreen(OregonTrailMain root) {
        super(root);
    }

    @Override
    public void create() {
        this.stage = new GameStage(this, root.spriteBatch);
        // this.actionState = new ActionState(this);
        // this.menuState = new MenuState(this);
        this.currentState = actionState;
    }

    @Override
    public void render(float delta) {
        // Update
        if (accumulator > this.timestep) {
            accumulator = 0;
            currentState.update(delta);
        }
        stage.act(delta);
        accumulator++;
        // Render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // renderer.render((double) accumulator / timestep);
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

    public void passInterfaceEventToState(int moveType) {
        currentState.handleInterfaceEvent(moveType);
    }

    public StateType getState() {
        return currentState.getStateType();
    }

    public void toMainMenu() {
        root.setScreen(root.mainMenuScreen);
    }
}
