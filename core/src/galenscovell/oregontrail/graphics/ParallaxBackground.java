package galenscovell.oregontrail.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ParallaxBackground {
    private ParallaxLayer[] layers;
    private Camera camera;
    private SpriteBatch batch;
    private Vector2 speed = new Vector2();

    public ParallaxBackground(SpriteBatch batch, ParallaxLayer[] layers, float width, float height, Vector2 speed) {
        this.layers = layers;
        this.speed.set(speed);
        this.camera = new OrthographicCamera(width, height);
        this.batch = batch;
    }

    public void render(float delta) {
        this.camera.position.add(speed.x * delta, speed.y * delta, 0);

        for (ParallaxLayer layer : layers) {
            batch.setProjectionMatrix(camera.projection);
            batch.begin();

            float currentX = -camera.position.x * layer.parallaxRatio.x % (layer.region.getRegionWidth() + layer.padding.x);
            float currentY;

            if (speed.x < 0) {
                currentX += -(layer.region.getRegionWidth() + layer.padding.x);
            }

            while (currentX < camera.viewportWidth) {
                currentY = -camera.position.y * layer.parallaxRatio.y % (layer.region.getRegionHeight() + layer.padding.y);

                if (speed.y < 0) {
                    currentY += -(layer.region.getRegionHeight() + layer.padding.y);
                }

                while (currentY < camera.viewportHeight) {
                    batch.draw(
                        layer.region,
                        -this.camera.viewportWidth / 2 + currentX + layer.startPosition.x,
                        -this.camera.viewportHeight / 2 + currentY + layer.startPosition.y
                    );
                    currentY += layer.region.getRegionHeight() + layer.padding.y;
                }
                currentX += layer.region.getRegionWidth() + layer.padding.x;
            }

            batch.end();
        }
    }
}
