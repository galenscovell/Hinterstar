package galenscovell.oregontrail.ui.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.oregontrail.util.*;

public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen(OregonTrailMain root){
        super(root);
    }

    @Override
    public void create() {
        this.stage = new Stage(new FitViewport(Constants.UI_X, Constants.UI_Y), root.spriteBatch);
        stage.setDebugAll(true);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Table buttonTable = new Table();
        TextButton sampleButton = new TextButton("Sample", ResourceManager.button_fullStyle);
        buttonTable.add(sampleButton).width(120).height(60).center();
        mainTable.add(buttonTable).width(200).height(200).center();

        stage.addActor(mainTable);
    }
}
