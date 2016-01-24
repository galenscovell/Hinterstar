package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import galenscovell.oregontrail.things.MapPoint;
import galenscovell.oregontrail.util.ResourceManager;

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

        Table innerTable = new Table();
        innerTable.setBackground(ResourceManager.mapback);
        generateMap(innerTable);

        mapTable.add(innerTable).width(580).height(360).expand().fill().left().padLeft(20);

        this.add(mapTable).width(760).height(400).expand().fill().center().padTop(26);
    }

    private void generateMap(Table container) {
        MapPoint p1 = new MapPoint();
        MapPoint p2 = new MapPoint();

        container.add(p1).width(24).height(24).expand().fill();
        container.add(p2).width(24).height(24).expand().fill();
    }
}
