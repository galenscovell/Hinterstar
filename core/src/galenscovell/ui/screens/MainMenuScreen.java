package galenscovell.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.OregonTrailMain;

public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen(OregonTrailMain root){
        super(root);
        this.stage = new Stage(new FitViewport(480, 800), root.spriteBatch);
    }

    @Override
    public void create() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
    }
}
