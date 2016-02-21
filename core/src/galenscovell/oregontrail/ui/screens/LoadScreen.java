package galenscovell.oregontrail.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.oregontrail.util.*;

public class LoadScreen extends AbstractScreen {
    private ProgressBar loadingBar;

    public LoadScreen(OregonTrailMain root) {
        super(root);
    }

    @Override
    protected void create() {
        this.stage = new Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), root.spriteBatch);
        Table loadingMain = new Table();
        loadingMain.setFillParent(true);

        Table barTable = new Table();
        this.loadingBar = createBar();
        barTable.add(loadingBar).width(400).expand().fill();
        loadingMain.add(barTable).expand().fill();

        stage.addActor(loadingMain);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if (ResourceManager.assetManager.update()) {
            ResourceManager.done();
            stage.getRoot().addAction(Actions.sequence(Actions.fadeOut(0.4f), toMainMenuScreen));
        }
        loadingBar.setValue(ResourceManager.assetManager.getLoadedAssets());
    }

    @Override
    public void show() {
        ResourceManager.create();
        create();
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.sequence(Actions.fadeIn(0.4f)));
    }

    private ProgressBar createBar() {
        TextureRegionDrawable fill = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/loadingFill.png"))));
        TextureRegionDrawable empty = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/loadingEmpty.png"))));
        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(empty, fill);
        ProgressBar bar = new ProgressBar(0, 6, 1, false, barStyle);
        barStyle.knobBefore = fill;
        bar.setValue(0);
        bar.setAnimateDuration(0.1f);
        return bar;
    }

    Action toMainMenuScreen = new Action() {
        public boolean act(float delta) {
            root.setScreen(root.mainMenuScreen);
            return true;
        }
    };
}
