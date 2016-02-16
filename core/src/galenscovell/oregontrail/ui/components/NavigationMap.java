package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.utils.Align;
import galenscovell.oregontrail.map.*;
import galenscovell.oregontrail.util.*;

public class NavigationMap extends Table {
    private final GameStage gameStage;
    private Label distanceLabel;

    public NavigationMap(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {
        this.setFillParent(true);

        Table mainTable = new Table();
        mainTable.setBackground(ResourceManager.np_test2);

        Group mapGroup = new Group();
        Table mapTable = createMap();
        Table infoTable = createInfo();

        mapGroup.addActor(mapTable);
        mapGroup.addActor(infoTable);

        mapTable.setPosition(0, 0);
        infoTable.setPosition(0, 0);

        mainTable.add(mapGroup).expand().fill();

        this.add(mainTable).width(Constants.EXACT_X).height(Constants.EXACT_Y).center();
    }

    private Table createInfo() {
        Table infoTable = new Table();
        infoTable.setSize(Constants.EXACT_X, 50);
        infoTable.align(Align.center);
        TextButton travelButton = new TextButton("Travel", ResourceManager.button_fullStyle);
        travelButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (gameStage.gameScreen.isTraveling()) {
                    System.out.println("Already traveling");
                } else if (!Repository.selectionIsValid()) {
                    System.out.println("Selection invalid");
                } else {
                    travelToLocation();
                }
            }
        });

        this.distanceLabel = new Label("Distance: 0.0 AU", ResourceManager.label_menuStyle);
        infoTable.add(distanceLabel).expand().fill().left().padLeft(20);
        infoTable.add(travelButton).width(150).height(50).expand().fill().right();
        return infoTable;
    }

    private Table createMap() {
        Table mapTable = new Table();
        mapTable.setBackground(ResourceManager.np_test4);
        generateMap(mapTable);
        mapTable.setFillParent(true);
        return mapTable;
    }

    private void generateMap(Table container) {
        MapGenerator mapGenerator = new MapGenerator();
        Tile[][] tiles = mapGenerator.getTiles();
        Repository.setTiles(tiles);
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                container.add(tile).width(Constants.TILESIZE).height(Constants.TILESIZE);
            }
            container.row();
        }
    }

    private void travelToLocation() {
        gameStage.toggleNavMap(true);
        gameStage.gameScreen.setTravel();
        Repository.travelToSelection();
    }

    public void updateDistanceLabel(String d) {
        distanceLabel.setText(d);
    }
}
