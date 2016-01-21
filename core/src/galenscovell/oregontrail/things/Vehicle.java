package galenscovell.oregontrail.things;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import galenscovell.oregontrail.util.ResourceManager;

public class Vehicle extends Actor {
    private Sprite currentSprite;
    private Sprite[] sprites;

    public Vehicle() {
        this.currentSprite = new Sprite(ResourceManager.uiAtlas.createSprite("vehicle"));
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentSprite, getX(), getY(), getWidth(), getHeight());
    }
}
