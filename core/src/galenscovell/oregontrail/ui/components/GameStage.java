package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.ui.screens.GameScreen;
import galenscovell.oregontrail.util.*;

public class GameStage extends Stage {
    public final GameScreen gameScreen;
    private final NavButtons navButtons;
    private final ActionTable actionTable;
    private final DetailTable detailTable;
    private final NavigationMap navigationMap;

    public GameStage(GameScreen gameScreen, SpriteBatch spriteBatch) {
        super(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), spriteBatch);
        this.gameScreen = gameScreen;
        this.navButtons = new NavButtons(this);
        this.actionTable = new ActionTable(this);
        this.detailTable = new DetailTable(this);
        this.navigationMap = new NavigationMap(this);
        construct();
    }

    private void construct() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        mainTable.add(navButtons).width(Constants.EXACT_X / 2).height(2 + Constants.TILESIZE * 2);
        mainTable.row();
        mainTable.add(actionTable).width(Constants.EXACT_X).height(340);
        mainTable.row();
        mainTable.add(detailTable).width(Constants.EXACT_X).height(110);

        this.addActor(mainTable);
    }

    public void toggleMap(boolean beganTravel) {
        if (navigationMap.hasParent()) {
            navigationMap.addAction(Actions.sequence(
                    toggleMapAction,
                    Actions.moveTo(-800, 0, 0.2f, Interpolation.sine),
                    Actions.removeActor())
            );
            if (!beganTravel) {
                Repository.clearSelection();
            }
        } else {
            this.addActor(navigationMap);
            navigationMap.addAction(Actions.sequence(
                    Actions.moveTo(-800, 0, 0),
                    Actions.moveTo(0, 0, 0.2f, Interpolation.sine),
                    toggleMapAction)
            );
        }
    }

    public void updateDistanceLabel(String d) {
        navigationMap.updateDistanceLabel(d);
    }

    Action toggleMapAction = new Action() {
        public boolean act(float delta) {
            gameScreen.toggleMap();
            return true;
        }
    };
}
