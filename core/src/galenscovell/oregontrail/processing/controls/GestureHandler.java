package galenscovell.oregontrail.processing.controls;

import com.badlogic.gdx.input.GestureDetector;
import galenscovell.oregontrail.ui.screens.GameScreen;

public class GestureHandler extends GestureDetector.GestureAdapter {
    private final GameScreen game;

    public GestureHandler(GameScreen game) {
        this.game = game;
    }

    @Override
    public boolean zoom (float initialDistance, float endDistance){
        // game.screenZoom(endDistance - initialDistance);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        // game.screenPan(deltaX, deltaY);
        return true;
    }
}