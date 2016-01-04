package galenscovell.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import galenscovell.oregontrail.OregonTrailMain;
import galenscovell.ui.components.GameStage;

public class GameScreen extends AbstractScreen {

    public GameScreen(OregonTrailMain root) {
        super(root);
    }

    @Override
    public void create() {
        this.stage = new GameStage(this, root.spriteBatch);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    public void toMainMenu() {
        root.setScreen(root.mainMenuScreen);
    }
}
