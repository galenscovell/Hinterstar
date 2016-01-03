package galenscovell.oregontrail;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import galenscovell.ui.screens.*;
import galenscovell.util.ResourceManager;

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

    @Override
    public void dispose() {
        mainMenuScreen.dispose();
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        ResourceManager.dispose();
    }
}
