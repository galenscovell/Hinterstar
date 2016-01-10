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
        // Initialize spriteBatch used throughout game
        this.spriteBatch = new SpriteBatch();
        // Construct game screens
        this.loadingScreen = new LoadScreen(this);
        this.mainMenuScreen = new MainMenuScreen(this);
        // Move to loading screen
        setScreen(loadingScreen);
    }

    public void newGame() {
        this.gameScreen = new GameScreen(this);
    }

    public void loadGame() {

    }

    @Override
    public void dispose() {
        mainMenuScreen.dispose();
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        ResourceManager.dispose();
    }
}
