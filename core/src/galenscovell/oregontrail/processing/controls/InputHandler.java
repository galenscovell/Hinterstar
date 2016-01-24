package galenscovell.oregontrail.processing.controls;

import com.badlogic.gdx.InputAdapter;
import galenscovell.oregontrail.ui.screens.GameScreen;

public class InputHandler extends InputAdapter {
    private final GameScreen game;
    private int startX, startY;

    public InputHandler(GameScreen game) {
        this.game = game;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        startX = x;
        startY = y;
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (Math.abs(x - startX) < 10 && Math.abs(y - startY) < 10) {
            game.passInputToState(x, y);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean scrolled(int amount) {
        // game.screenZoom(amount * 1000);
        return true;
    }
}