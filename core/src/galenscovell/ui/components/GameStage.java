package galenscovell.ui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.ui.screens.GameScreen;
import galenscovell.util.Constants;

public class GameStage extends Stage {
    private GameScreen rootScreen;

    public GameStage(GameScreen rootScreen, SpriteBatch spriteBatch) {
        super(new FitViewport(Constants.SCREEN_X, Constants.SCREEN_Y), spriteBatch);
        this.rootScreen = rootScreen;
        construct();
    }

    private void construct() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        this.addActor(mainTable);
    }
}
