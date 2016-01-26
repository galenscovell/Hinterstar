package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.scenes.scene2d.utils.*;
import galenscovell.oregontrail.util.*;
import galenscovell.oregontrail.map.*;

public class NavigationMap extends Table {
    private final GameStage gameStage;
    private MapGenerator mapGenerator;
    private MapRepository repo;

    public NavigationMap(GameStage gameStage) {
        this.gameStage = gameStage;
        this.mapGenerator = new MapGenerator(16);
        this.repo = mapGenerator.getRepo();
        construct();
    }

    private void construct() {
        this.setFillParent(true);

        Table mapTable = new Table();
        mapTable.setBackground(ResourceManager.buttonUp);

        Table navInfoTable = createInfoBox();
        Table mainMapTable = createMapBox();

        mapTable.add(navInfoTable).width(160).height(400).expand().fill().left().padLeft(8);
        mapTable.add(mainMapTable).width(Constants.MAPBORDERWIDTH).height(Constants.MAPBORDERHEIGHT).expand().fill().right().padRight(8);

        this.add(mapTable).width(Constants.EXACT_X).height(Constants.EXACT_Y - 50).expand().fill().center().padTop(50);
    }

    private Table createInfoBox() {
        Table navInfoTable = new Table();
        Table navInfo = new Table();
        navInfo.setBackground(ResourceManager.buttonDown);

        TextButton travelButton = new TextButton("Travel", ResourceManager.button_fullStyle);
        travelButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                travelToDestination();
            }
        });

        navInfoTable.add(navInfo).width(160).height(320).expand().fill();
        navInfoTable.row();
        navInfoTable.add(travelButton).width(160).height(70).expand().fill().bottom();

        return navInfoTable;
    }

    private Table createMapBox() {
        Table mainMapTable = new Table();
        mainMapTable.setBackground(ResourceManager.mapback);

        Table innerTable = new Table();
        innerTable.setBackground(new TextureRegionDrawable(ResourceManager.uiAtlas.findRegion("orion-nebula")));
        generateMap(innerTable);

        mainMapTable.add(innerTable).expand().fill();

        return mainMapTable;
    }

    private void generateMap(Table container) {
        Tile[][] tiles = mapGenerator.getTiles();

        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                container.add(tile).width(Constants.TILESIZE).height(Constants.TILESIZE);
            }
            container.row();
        }
    }

    private void travelToDestination() {
        gameStage.toggleNavMap();
        gameStage.rootScreen.setTravel();
    }
}
