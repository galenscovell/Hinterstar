package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import galenscovell.oregontrail.util.*;
import galenscovell.oregontrail.world.*;

public class NavigationMap extends Table {
    private final GameStage gameStage;

    public NavigationMap(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {
        this.setFillParent(true);

        Table mapTable = new Table();
        mapTable.setBackground(ResourceManager.buttonUp);

        Table navInfoTable = new Table();
        Table navInfo = new Table();
        navInfo.setBackground(ResourceManager.buttonDown);
        TextButton travelButton = new TextButton("Travel", ResourceManager.button_fullStyle);
        navInfoTable.add(navInfo).width(160).height(320).expand().fill();
        navInfoTable.row();
        navInfoTable.add(travelButton).width(160).height(70).expand().fill().bottom();

        Table innerTable = new Table();
        innerTable.setBackground(new TextureRegionDrawable(ResourceManager.uiAtlas.findRegion("orion-nebula")));
        generateMap(innerTable);

        mapTable.add(navInfoTable).width(160).height(400).expand().fill().left().padLeft(8);
        mapTable.add(innerTable).width(Constants.MAPBORDERWIDTH).height(Constants.MAPBORDERHEIGHT).expand().fill().right().padRight(8);

        this.add(mapTable).width(Constants.EXACT_X).height(Constants.EXACT_Y - 50).expand().fill().center().padTop(50);
    }

    private void generateMap(Table container) {
        MapGenerator mapGenerator = new MapGenerator(14);
        Tile[][] tiles = mapGenerator.getTiles();

        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                container.add(tile).width(Constants.TILESIZE).height(Constants.TILESIZE);
            }
            container.row();
        }
    }
}
