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
        back.setSize(Constants.SCREEN_X, 80);
        back.setPosition(0, 0);

        Table middle = new Table();
        middle.setSize(Constants.SCREEN_X, 50);
        middle.setPosition(0, 10);
        Vehicle vehicle = new Vehicle();
        vehicle.setSize(45, 35);
        middle.add(vehicle).expand().fill().left().padLeft(15);

        Table front = new Table();
        front.setSize(Constants.SCREEN_X, 80);
        front.setPosition(0, 0);

        graphicsGroup.addActor(back);
        graphicsGroup.addActor(middle);
        graphicsGroup.addActor(front);

        this.add(graphicsGroup).expand().fill();
    }
}
