package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.ui.screens.GameScreen;
import galenscovell.oregontrail.util.*;

public class GameStage extends Stage {
    public final GameScreen rootScreen;
    private Table navigationTable;
    private NavigationMap navigationMap;
    private ActionTable actionTable;
    private DetailTable detailTable;

    public GameStage(GameScreen rootScreen, SpriteBatch spriteBatch) {
        super(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), spriteBatch);
        this.rootScreen = rootScreen;
        construct();
    }

    private void construct() {
        this.navigationMap = new NavigationMap(this);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        this.navigationTable = new Table();
        TextButton navMapButton = new TextButton("Nav", ResourceManager.button_fullStyle);
        navMapButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                toggleNavMap();
            }
        });
        TextButton teamButton = new TextButton("Team", ResourceManager.button_fullStyle);
        teamButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Team button");
            }
        });
        TextButton shipButton = new TextButton("Ship", ResourceManager.button_fullStyle);
        shipButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Ship button");
            }
        });
        navigationTable.add(navMapButton).width(90).expand().fill();
        navigationTable.add(teamButton).width(90).expand().fill();
        navigationTable.add(shipButton).width(90).expand().fill();

        this.actionTable = new ActionTable(this);
        this.detailTable = new DetailTable(this);

        mainTable.add(navigationTable).width(Constants.EXACT_X / 3).height(40).center().padTop(4);
        mainTable.row();
        mainTable.add(actionTable).width(Constants.EXACT_X).height(340).center();
        mainTable.row();
        mainTable.add(detailTable).width(Constants.EXACT_X).height(110).center();

        this.addActor(mainTable);
    }

    public void toggleNavMap() {
        if (navigationMap.hasParent()) {
            navigationMap.remove();
        } else {
            this.addActor(navigationMap);
        }
    }
}
