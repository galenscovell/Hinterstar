package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import galenscovell.oregontrail.util.*;

public class ShipPanel extends Table {
    private final GameStage gameStage;

    public ShipPanel(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {
        this.setFillParent(true);

        Table mainTable = new Table();
        mainTable.setBackground(ResourceManager.np_test2);

        this.add(mainTable).width(Constants.EXACT_X).height(Constants.EXACT_Y - (Constants.SECTORSIZE * 2)).padTop(Constants.SECTORSIZE * 2);
    }
}
