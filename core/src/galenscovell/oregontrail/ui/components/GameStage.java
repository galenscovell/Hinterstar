package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.things.entity.Player;
import galenscovell.oregontrail.ui.screens.GameScreen;
import galenscovell.oregontrail.util.*;

public class GameStage extends Stage {
    public final GameScreen gameScreen;
    private final Player player;
    private NavButtons navButtons;
    private Table actionTable;
    private DetailTable detailTable;
    private NavigationMap navigationMap;

    public GameStage(GameScreen gameScreen, SpriteBatch spriteBatch) {
        super(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), spriteBatch);
        this.gameScreen = gameScreen;
        this.player = new Player(this);
        this.navigationMap = new NavigationMap(this);
        construct();
    }

    private void construct() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        this.navButtons = new NavButtons(this);
        this.actionTable = new Table();
        actionTable.add(player).expand().fill().left().padLeft(80);

        this.detailTable = new DetailTable(this);

        mainTable.add(navButtons).width(Constants.EXACT_X / 2).height(2 + Constants.TILESIZE * 2);
        mainTable.row();
        mainTable.add(actionTable).width(Constants.EXACT_X).height(340);
        mainTable.row();
        mainTable.add(detailTable).width(Constants.EXACT_X).height(110);

        this.addActor(mainTable);
    }

    public void toggleMap() {
        if (!navigationMap.hasActions()) {
            if (navigationMap.hasParent()) {
                navigationMap.addAction(Actions.sequence(
                        toggleMapAction,
                        Actions.moveTo(-800, 0, 0.2f, Interpolation.sine),
                        Actions.removeActor())
                );
            } else {
                this.addActor(navigationMap);
                navigationMap.addAction(Actions.sequence(
                        Actions.moveTo(-800, 0),
                        Actions.moveTo(0, 0, 0.2f, Interpolation.sine),
                        toggleMapAction)
                );
            }
        }
    }

    public void toggleNavButtons() {
        navButtons.addAction(Actions.sequence(
                Actions.touchable(Touchable.disabled),
                Actions.moveBy(0, 2 + Constants.TILESIZE * 2, 0.5f, Interpolation.sine),
                Actions.delay(7.5f),
                Actions.moveBy(0, -(2 + Constants.TILESIZE * 2), 0.5f, Interpolation.sine),
                Actions.touchable(Touchable.enabled)));
    }

    public void updateDistanceLabel(String d) {
        navigationMap.updateDistanceLabel(d);
    }

    Action toggleMapAction = new Action() {
        public boolean act(float delta) {
            gameScreen.toggleMap();
            Repository.setTargetsInRange();
            return true;
        }
    };
}
