package galenscovell.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.util.Constants;

public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen(OregonTrailMain root){
        super(root);
    }

    @Override
    public void create() {
        this.stage = new Stage(new FitViewport(Constants.UI_X, Constants.UI_Y), root.spriteBatch);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
    }
}
