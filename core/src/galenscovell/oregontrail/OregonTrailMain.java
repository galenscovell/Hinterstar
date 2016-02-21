package galenscovell.oregontrail;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import galenscovell.oregontrail.ui.screens.*;
import galenscovell.oregontrail.util.ResourceManager;

public class OregonTrailMain extends Game {
    public SpriteBatch spriteBatch;
    public AbstractScreen loadingScreen, mainMenuScreen, gameScreen;
    
    @Override
    public void create () {
        this.spriteBatch = new SpriteBatch();
        this.loadingScreen = new LoadScreen(this);
        this.mainMenuScreen = new MainMenuScreen(this);
        setScreen(loadingScreen);
    }

    @Override
    public void dispose() {
        mainMenuScreen.dispose();
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        ResourceManager.dispose();
    }

    public void newGame() {
        this.gameScreen = new GameScreen(this);
    }

    public void loadGame() {

    }
}
