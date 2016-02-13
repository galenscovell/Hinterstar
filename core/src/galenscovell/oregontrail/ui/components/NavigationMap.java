package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

import galenscovell.oregontrail.map.*;
import galenscovell.oregontrail.util.Repository;
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

        Table mapTable = new Table();
        mapTable.setBackground(ResourceManager.np_test2);

        Table mainMapTable = createMapBox();
        Table navInfoTable = createInfoBox();

        mapTable.add(mainMapTable).width(Constants.MAPBORDERWIDTH).height(Constants.MAPBORDERHEIGHT).expand().fill();
        mapTable.row();
        mapTable.add(navInfoTable).width(Constants.MAPBORDERWIDTH).padTop(5).padBottom(15);

        this.add(mapTable).width(Constants.EXACT_X).height(Constants.EXACT_Y - 40).expand().fill().center().padTop(40);
    }

    private Table createInfoBox() {
        Table navInfoTable = new Table();

        TextButton travelButton = new TextButton("Travel", ResourceManager.button_fullStyle);
        travelButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (gameStage.rootScreen.isTraveling()) {
                    System.out.println("Already traveling");
                } else if (!Repository.selectionIsValid()) {
                    System.out.println("Selection invalid");
                } else {
                    travelToLocation();
                }
            }
        });

        this.distanceLabel = new Label("Distance: 0.0 AU", ResourceManager.label_menuStyle);

        navInfoTable.add(distanceLabel).expand().fill().left();
        navInfoTable.add(travelButton).width(150).height(50).expand().fill().right();

        return navInfoTable;
    }

    private Table createMapBox() {
        Table mainMapTable = new Table();

        Table innerTable = new Table();
        innerTable.setBackground(ResourceManager.np_test4);
        generateMap(innerTable);

        mainMapTable.add(innerTable).expand().fill();

        return mainMapTable;
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
        gameStage.rootScreen.setTravel();
        Repository.travelToSelection();
    }

    public void updateDistanceLabel(String d) {
        distanceLabel.setText(d);
    }
}
