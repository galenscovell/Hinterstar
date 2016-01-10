package galenscovell.oregontrail.processing.states;

public interface IState {
    void enter();
    void exit();
    void update(float delta);
    void handleInput(float x, float y);
    void handleInterfaceEvent(int moveType);
    StateType getStateType();
}
