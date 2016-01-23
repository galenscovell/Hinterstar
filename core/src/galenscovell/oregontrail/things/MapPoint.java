package galenscovell.oregontrail.things;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import galenscovell.oregontrail.util.ResourceManager;

public class MapPoint extends Actor {
    private Sprite currentSprite;
    private Sprite[] sprites;
    private boolean selected;

    public MapPoint() {
        this.currentSprite = new Sprite(ResourceManager.uiAtlas.createSprite("map_point"));
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
        batch.draw(currentSprite, getX(), getY(), getWidth(), getHeight());
        batch.setColor(1, 1, 1, 1);
    }
}
