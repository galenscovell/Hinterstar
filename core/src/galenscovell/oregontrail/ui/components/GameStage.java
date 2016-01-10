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

        Table topTable = new Table();

        Table bottomTable = new Table();
        mainTable.add(topTable).width(Constants.SCREEN_X).height(70).center().bottom().padBottom(4);
        mainTable.row();
        mainTable.add(bottomTable).width(Constants.SCREEN_X).height(46).center().bottom();

        this.addActor(mainTable);
    }
}
