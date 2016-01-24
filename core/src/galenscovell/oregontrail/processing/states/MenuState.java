package galenscovell.oregontrail.processing.states;

import galenscovell.oregontrail.ui.components.GameStage;
import galenscovell.oregontrail.ui.screens.GameScreen;

public class MenuState implements State {
    private GameScreen gameScreen;

    public MenuState(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void enter() {
        System.out.println("\tEntered Menu State");
    }

    @Override
    public void exit() {
        System.out.println("\tLeft Menu State");
    }

    @Override
    public void update(float delta, GameStage stage) {

    }

    @Override
    public void handleInput(float x, float y) {
        System.out.println("Menu: " + x + ", " + y);
    }

    @Override
    public StateType getStateType() {
        return StateType.MENU;
    }
}