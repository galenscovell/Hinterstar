package galenscovell.oregontrail.things.entity;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import galenscovell.oregontrail.ui.components.GameStage;
import galenscovell.oregontrail.ui.screens.GameScreen;
import galenscovell.oregontrail.util.ResourceManager;

public class Player extends Actor {
    private GameScreen gameScreen;
    private Sprite currentSprite;
    private Sprite[] sprites;
    private boolean selected, animUp;
    private float frame;

    public Player(GameStage gameStage) {
        this.gameScreen = gameStage.gameScreen;
        this.currentSprite = new Sprite(ResourceManager.uiAtlas.createSprite("placeholder_vehicle"));
        this.setSize(270, 90);

        this.addListener(new ActorGestureListener() {
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selected = !selected;
            }
        });
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (selected) {
            batch.setColor(0.5f, 0.5f, 1.0f, 1);
        }
        batch.draw(currentSprite, getX(), getY() + frame, getWidth(), getHeight());
        batch.setColor(1, 1, 1, 1);

//        if (frame == 0) {
//            animUp = true;
//        } else if (frame == 6) {
//            animUp = false;
//        }
//
//        if (animUp) {
//            frame += 0.125f;
//        } else {
//            frame -= 0.125f;
//        }
    }
}
