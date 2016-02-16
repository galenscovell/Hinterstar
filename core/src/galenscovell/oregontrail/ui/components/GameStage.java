package galenscovell.oregontrail.ui.components;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.ui.screens.GameScreen;
import galenscovell.oregontrail.ui.tween.ActorAccessor;
import galenscovell.oregontrail.util.*;

public class GameStage extends Stage {
    public final GameScreen gameScreen;
    private final TweenManager tweenManager;
    private Table buttonTable;
    private NavigationMap navigationMap;
    private ActionTable actionTable;
    private DetailTable detailTable;

    public GameStage(GameScreen gameScreen, SpriteBatch spriteBatch, TweenManager tweenManager) {
        super(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), spriteBatch);
        this.gameScreen = gameScreen;
        this.tweenManager = tweenManager;
        construct();
    }

    private void construct() {
        this.navigationMap = new NavigationMap(this);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        this.buttonTable = new Table();
        TextButton navMapButton = new TextButton("Nav", ResourceManager.button_mapStyle);
        navMapButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                toggleNavMap(false);
            }
        });
        TextButton teamButton = new TextButton("Team", ResourceManager.button_mapStyle);
        teamButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Team button");
            }
        });
        TextButton shipButton = new TextButton("Ship", ResourceManager.button_mapStyle);
        shipButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Ship button");
            }
        });
        buttonTable.add(navMapButton).width(90).expand().fill();
        buttonTable.add(teamButton).width(90).expand().fill();
        buttonTable.add(shipButton).width(90).expand().fill();

        this.actionTable = new ActionTable(this);
        this.detailTable = new DetailTable(this);

        mainTable.add(buttonTable).width(Constants.EXACT_X / 3).height(40).center().padTop(4);
        mainTable.row();
        mainTable.add(actionTable).width(Constants.EXACT_X).height(340).center();
        mainTable.row();
        mainTable.add(detailTable).width(Constants.EXACT_X).height(110).center();

        this.addActor(mainTable);
    }

    public void toggleNavMap(boolean beganTravel) {
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        gameScreen.toggleMap();
        if (navigationMap.hasParent()) {
            Tween.to(navigationMap, ActorAccessor.ALPHA, 0.25f)
                    .target(0)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int i, BaseTween<?> baseTween) {
                            navigationMap.remove();
                            navigationMap.setColor(1, 1, 1, 1);
                        }
                    })
                    .start(tweenManager);
            if (!beganTravel) {
                Repository.clearSelection();
            }
        } else {
            this.addActor(navigationMap);
            Tween.from(navigationMap, ActorAccessor.ALPHA, 0.25f).target(0).start(tweenManager);
        }
        tweenManager.update(Gdx.graphics.getDeltaTime());
    }

    public void updateDistanceLabel(String d) {
        navigationMap.updateDistanceLabel(d);
    }
}
