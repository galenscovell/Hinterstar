package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import galenscovell.oregontrail.util.ResourceManager;

public class NavigationTable extends Table {
    private GameStage gameStage;

    public NavigationTable(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {

    }
}
