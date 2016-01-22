package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import galenscovell.oregontrail.things.Vehicle;
import galenscovell.oregontrail.util.*;

public class ActionTable extends Table {
    private GameStage gameStage;

    public ActionTable(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {
        Group graphicsGroup = new Group();

        Table back = new Table();
        back.setSize(Constants.EXACT_X, 32);
        back.setPosition(0, 0);

        Table middle = new Table();
        middle.setSize(Constants.EXACT_X, 200);
        middle.setPosition(0, 40);
        Vehicle vehicle = new Vehicle();
        vehicle.setSize(180, 90);
        middle.add(vehicle).expand().fill().left().padLeft(30);

        Table front = new Table();
        front.setSize(Constants.EXACT_X, 320);
        front.setPosition(0, 0);

        graphicsGroup.addActor(back);
        graphicsGroup.addActor(middle);
        graphicsGroup.addActor(front);

        this.add(graphicsGroup).expand().fill();
    }
}
