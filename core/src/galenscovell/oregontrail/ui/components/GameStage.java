package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
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
        this.setDebugAll(true);
        Table mainTable = new Table();
        mainTable.setBackground(ResourceManager.background);
        mainTable.setFillParent(true);


        // Graphics area
        Table topTable = new Table();
        Group graphicsGroup = new Group();

        Table back = new Table();
        Table middle = new Table();
        Table front = new Table();

        Vehicle vehicle = new Vehicle();
        vehicle.setSize(40, 25);
        middle.add(vehicle).expand().fill().right().padRight(15);

        back.setSize(Constants.SCREEN_X, 80);
        middle.setSize(Constants.SCREEN_X, 50);
        front.setSize(Constants.SCREEN_X, 80);

        graphicsGroup.addActor(back);
        graphicsGroup.addActor(middle);
        graphicsGroup.addActor(front);
        back.setPosition(0, 0);
        middle.setPosition(0, 10);
        front.setPosition(0, 0);

        topTable.add(graphicsGroup).expand().fill();


        // Information area
        Table bottomTable = new Table();
        bottomTable.setBackground(ResourceManager.buttonDown);


        mainTable.add(topTable).width(Constants.SCREEN_X).height(80).center().bottom().padBottom(4);
        mainTable.row();
        mainTable.add(bottomTable).width(Constants.SCREEN_X).height(36).center().bottom();

        this.addActor(mainTable);
    }
}
