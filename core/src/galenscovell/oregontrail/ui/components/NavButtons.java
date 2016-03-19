package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import galenscovell.oregontrail.util.ResourceManager;

public class NavButtons extends Table {
    private final GameStage gameStage;
    private TextButton mapButton, teamButton, shipButton;

    public NavButtons(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {
        this.align(Align.center);

        this.mapButton = new TextButton("Map", ResourceManager.button_mapStyle0);
        this.teamButton = new TextButton("Team", ResourceManager.button_mapStyle1);
        this.shipButton = new TextButton("Ship", ResourceManager.button_mapStyle2);

        mapButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameStage.togglePanel(0);
                teamButton.setChecked(false);
                shipButton.setChecked(false);
            }
        });
        teamButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameStage.togglePanel(1);
                mapButton.setChecked(false);
                shipButton.setChecked(false);
            }
        });
        shipButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameStage.togglePanel(2);
                mapButton.setChecked(false);
                teamButton.setChecked(false);
            }
        });

        this.add(mapButton).width(130).expand().fill();
        this.add(teamButton).width(130).expand().fill();
        this.add(shipButton).width(130).expand().fill();
    }

    public TextButton getMapButton() {
        return mapButton;
    }
}
