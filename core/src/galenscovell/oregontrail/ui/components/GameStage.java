package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.ui.screens.GameScreen;
import galenscovell.oregontrail.util.*;

public class GameStage extends Stage {
    private GameScreen rootScreen;
    private NavigationTable navigationTable;
    private ActionTable actionTable;
    private DetailTable detailTable;

    public GameStage(GameScreen rootScreen, SpriteBatch spriteBatch) {
        super(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), spriteBatch);
        this.rootScreen = rootScreen;
        construct();
    }

    private void construct() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        this.navigationTable = new NavigationTable(this);
        this.actionTable = new ActionTable(this);
        this.detailTable = new DetailTable(this);

        mainTable.add(navigationTable).width(Constants.EXACT_X).height(60).center();
        mainTable.row();
        mainTable.add(actionTable).width(Constants.EXACT_X).height(320).center();
        mainTable.row();
        mainTable.add(detailTable).width(Constants.EXACT_X).height(100).center();

        this.addActor(mainTable);
    }

    public void incrementDate() {
        detailTable.updateDate();
    }
}
