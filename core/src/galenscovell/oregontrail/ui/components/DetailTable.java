package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import galenscovell.oregontrail.util.ResourceManager;

public class DetailTable extends Table {
    private GameStage gameStage;

    public DetailTable(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {
        this.setBackground(ResourceManager.buttonDown);
    }
}
