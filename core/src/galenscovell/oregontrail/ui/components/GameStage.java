package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.ui.screens.GameScreen;
import galenscovell.oregontrail.util.Constants;

public class GameStage extends Stage {
    private GameScreen rootScreen;

    public GameStage(GameScreen rootScreen, SpriteBatch spriteBatch) {
        super(new FitViewport(Constants.SCREEN_X, Constants.SCREEN_Y), spriteBatch);
        this.rootScreen = rootScreen;
        construct();
    }

    private void construct() {
        this.setDebugAll(true);
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Table bottomTable = new Table();
        mainTable.add(bottomTable).width(440).height(300).center().bottom();

        this.addActor(mainTable);
    }
}
