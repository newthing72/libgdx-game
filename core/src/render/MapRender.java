package render;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.google.inject.Inject;
import generation.noise.FastNoiseLite;

import java.util.concurrent.ThreadLocalRandom;

public class MapRender extends ApplicationAdapter {

    public int size = 1;
    public int width = size * 1280;
    public int height = size * 720;
    SpriteBatch batch;
    Texture image;
    float[] noiseData;
    OrthographicCamera camera;
    Sprite sprite;


    @Inject
    public MapRender() {


        // Gather noise data


//        for (int i = 0; i < noiseData.length; i++) {
//            System.out.println(Arrays.toString(Arrays.copyOfRange(noiseData, i * 128, i * 128 + 128)));
//        }
    }

    @Override
    public void create() {
        this.image = new Texture("badlogic.jpg");
        this.batch = new SpriteBatch();
        camera = new OrthographicCamera(1280, 720);

        sprite = new Sprite(this.image);
        sprite.setOrigin(0, 0);

        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        sprite.setSize(15, 55);

        noiseData = new float[width * height];
        int index = 0;
        FastNoiseLite noise = new FastNoiseLite(ThreadLocalRandom.current().nextInt());
        noise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                noiseData[index++] = ((noise.GetNoise(x, y) + 1) * 5) / 10.0f;
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        ShapeRenderer shapeRenderer = new ShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < noiseData.length; i++) {
            shapeRenderer.setColor(0.5f, noiseData[i], 0.5f, 1);
            shapeRenderer.rect((i % width) * size, ((i / width)) * size, size, size);
        }
        shapeRenderer.end();

//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        batch.setProjectionMatrix(camera.combined);


        batch.begin();

        sprite.draw(batch);

        batch.end();

        handleInput();

        System.out.println(camera.position.toString());

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 1);
        }

        camera.update();
    }


}
