package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import galenscovell.oregontrail.map.*;
import galenscovell.oregontrail.util.*;

public class MapPanel extends Table {
    private final GameStage gameStage;
    private Label distanceLabel;

    public MapPanel(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {
        this.setFillParent(true);

        Group mapGroup = new Group();
        Table mapTable = createMapTable();
        Table infoTable = createInfoTable();

        mapGroup.addActor(mapTable);
        mapGroup.addActor(infoTable);

        mapTable.setPosition(0, 0);
        infoTable.setPosition(0, 0);

        this.add(mapGroup).width(Constants.EXACT_X).height(Constants.EXACT_Y - (Constants.TILESIZE * 2)).padTop(Constants.TILESIZE * 2);
    }

    private Table createMapTable() {
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

    private Table createInfoTable() {
        Table infoTable = new Table();
        infoTable.setSize(Constants.EXACT_X, 50);
        infoTable.align(Align.center);
        TextButton travelButton = new TextButton("Travel", ResourceManager.button_menuStyle);
        travelButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                travelToLocation();
            }
        });
        this.distanceLabel = new Label("Distance: 0.0 AU", ResourceManager.label_menuStyle);
        infoTable.add(distanceLabel).expand().fill().left().padLeft(20);
        infoTable.add(travelButton).width(150).height(50).expand().fill().right();
        return infoTable;
    }

    private void travelToLocation() {
        gameStage.togglePanel(0);
        gameStage.gameScreen.setTravel();
        gameStage.toggleNavButtons();
        Repository.travelToSelection();
    }

    public void updateDistanceLabel(String d) {
        distanceLabel.setText(d);
    }
}
