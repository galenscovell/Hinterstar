package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import galenscovell.oregontrail.map.*;
import galenscovell.oregontrail.util.*;

public class NavigationMap extends Table {
    private final GameStage gameStage;
    private MapGenerator mapGenerator;
    private MapRepository repo;

    public NavigationMap(GameStage gameStage) {
        this.gameStage = gameStage;
        this.mapGenerator = new MapGenerator(15);
        this.repo = mapGenerator.getRepo();
        construct();
    }

    private void construct() {
        this.setFillParent(true);

        Table mapTable = new Table();
        mapTable.setBackground(ResourceManager.glass);

        Table mainMapTable = createMapBox();
        Table navInfoTable = createInfoBox();

        mapTable.add(mainMapTable).width(Constants.MAPBORDERWIDTH).height(Constants.MAPBORDERHEIGHT).expand().fill();
        mapTable.row();
        mapTable.add(navInfoTable).width(Constants.MAPBORDERWIDTH).padTop(10);

        this.add(mapTable).width(Constants.EXACT_X).height(Constants.EXACT_Y - 40).expand().fill().center().padTop(40);
    }

    private Table createInfoBox() {
        Table navInfoTable = new Table();

        TextButton travelButton = new TextButton("Travel", ResourceManager.button_fullStyle);
        travelButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (gameStage.rootScreen.isTraveling()) {
                    System.out.println("Already traveling");
                } else if (repo.getCurrentSelection() == null || repo.getCurrentSelection() == repo.getCurrentSelection()) {
                    System.out.println("Selection invalid");
                } else {
                    travelToLocation();
                }
            }
        });

        navInfoTable.add(travelButton).width(120).height(50).expand().fill().right();

        return navInfoTable;
    }

    private Table createMapBox() {
        Table mainMapTable = new Table();
        mainMapTable.setBackground(ResourceManager.glass);

        Table innerTable = new Table();
        innerTable.setBackground(new TextureRegionDrawable(ResourceManager.uiAtlas.findRegion("galaxy")));
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

    private void travelToLocation() {
        gameStage.toggleNavMap();
        gameStage.rootScreen.setTravel();
        repo.travelToSelection();
    }
}
