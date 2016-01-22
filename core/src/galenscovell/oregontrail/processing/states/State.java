package galenscovell.oregontrail.processing.states;

import galenscovell.oregontrail.ui.components.GameStage;

public interface State {
    void enter();
    void exit();
    void update(float delta, GameStage stage);
    void handleInput(float x, float y);
    StateType getStateType();
}
