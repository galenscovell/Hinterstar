package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.ui.screens.GameScreen;
import galenscovell.oregontrail.util.*;

public class GameStage extends Stage {
    private GameScreen rootScreen;

    public GameStage(GameScreen rootScreen, SpriteBatch spriteBatch) {
        super(new FitViewport(Constants.SCREEN_X, Constants.SCREEN_Y), spriteBatch);
        this.rootScreen = rootScreen;
        construct();
    }

    private void construct() {
        Table mainTable = new Table();
        // mainTable.setBackground(ResourceManager.background);
        mainTable.setFillParent(true);

        NavigationTable navigationTable = new NavigationTable(this);
        ActionTable actionTable = new ActionTable(this);
        DetailTable detailTable = new DetailTable(this);

        mainTable.add(navigationTable).width(Constants.SCREEN_X).height(10).center();
        mainTable.row();
        mainTable.add(actionTable).width(Constants.SCREEN_X).height(80).center();
        mainTable.row();
        mainTable.add(detailTable).width(Constants.SCREEN_X).height(30).center();

        this.addActor(mainTable);
    }
}
