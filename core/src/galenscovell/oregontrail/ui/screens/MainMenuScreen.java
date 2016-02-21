package galenscovell.oregontrail.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.oregontrail.util.*;

public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen(OregonTrailMain root){
        super(root);
    }

    @Override
    protected void create() {
        this.stage = new Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), root.spriteBatch);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Table titleTable = new Table();
        Label titleLabel = new Label("Game Name", ResourceManager.label_titleStyle);
        titleLabel.setAlignment(Align.center, Align.center);
        titleTable.add(titleLabel).width(400).height(80);

        Table buttonTable = new Table();
        TextButton newGameButton = new TextButton("New", ResourceManager.button_menuStyle);
        newGameButton.getLabel().setAlignment(Align.bottom, Align.center);
        newGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                root.newGame();
                stage.getRoot().addAction(Actions.sequence(Actions.fadeOut(0.75f), toGameScreen));
            }
        });
        TextButton continueGameButton = new TextButton("Load", ResourceManager.button_menuStyle);
        continueGameButton.getLabel().setAlignment(Align.bottom, Align.center);
        continueGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // root.loadGame();
            }
        });
        TextButton settingButton = new TextButton("Settings", ResourceManager.button_menuStyle);
        settingButton.getLabel().setAlignment(Align.bottom, Align.center);
        settingButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                // openSettings();
            }
        });
        TextButton quitButton = new TextButton("Quit", ResourceManager.button_menuStyle);
        quitButton.getLabel().setAlignment(Align.bottom, Align.center);
        quitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.getRoot().addAction(Actions.sequence(Actions.fadeOut(0.5f), quitGame));
            }
        });
        buttonTable.add(newGameButton).width(180).height(380).padRight(20);
        buttonTable.add(continueGameButton).width(180).height(380).padRight(20);
        buttonTable.add(settingButton).width(180).height(380).padRight(20);
        buttonTable.add(quitButton).width(180).height(380);

        mainTable.add(titleTable).width(760).height(80).center().padBottom(8);
        mainTable.row();
        mainTable.add(buttonTable).width(760).height(400).center();

        stage.addActor(mainTable);

        mainTable.addAction(Actions.sequence(Actions.fadeOut(0), Actions.fadeIn(0.5f)));
    }

    Action toGameScreen = new Action() {
        public boolean act(float delta) {
            root.setScreen(root.gameScreen);
            return true;
        }
    };

    Action quitGame = new Action() {
        public boolean act(float delta) {
            Gdx.app.exit();
            return true;
        }
    };
}
