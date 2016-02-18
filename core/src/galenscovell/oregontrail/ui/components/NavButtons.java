package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import galenscovell.oregontrail.util.ResourceManager;

public class NavButtons extends Table {
    private final GameStage gameStage;

    public NavButtons(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {
        this.align(Align.center);

        TextButton mapButton = new TextButton("Nav", ResourceManager.button_mapStyle);
        mapButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameStage.toggleMap();
            }
        });
        TextButton teamButton = new TextButton("Team", ResourceManager.button_mapStyle);
        teamButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Team button");
            }
        });
        TextButton shipButton = new TextButton("Ship", ResourceManager.button_mapStyle);
        shipButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Ship button");
            }
        });

        this.add(mapButton).width(120).expand().fill();
        this.add(teamButton).width(120).expand().fill();
        this.add(shipButton).width(120).expand().fill();
    }
}
