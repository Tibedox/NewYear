package ru.itschool.newyear;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public static final float SCR_WIDTH = 1600;
    public static final float SCR_HEIGHT = 900;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Vector3 touch;

    private Texture imgBackGround;
    private Texture imgFlake;
    private Music musicElochka1, musicElochka2;

    SnowFlake[] flakes = new SnowFlake[400];

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
        touch = new Vector3();

        imgBackGround = new Texture("winterimg.jpg");
        imgFlake = new Texture("snowflake.png");
        musicElochka1 = Gdx.audio.newMusic(Gdx.files.internal("v-lesu-rodilas-elochka1.mp3"));
        musicElochka2 = Gdx.audio.newMusic(Gdx.files.internal("v-lesu-rodilas-elochka2.mp3"));

        for (int i = 0; i < flakes.length; i++) {
            flakes[i] = new SnowFlake();
        }
    }

    @Override
    public void render() {
        // касания
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            for(SnowFlake f: flakes){
                if(f.hit(touch.x, touch.y)) f.respawn();
            }
            //System.out.println(touch.x+" "+ touch.y);

            if(302<touch.x && touch.x<715 && 261<touch.y && touch.y<603){
                musicElochka1.stop();
                musicElochka2.stop();
            }

            if(871<touch.x && touch.x<940 && 308<touch.y && touch.y<373){
                musicElochka2.stop();
                musicElochka1.play();
            }
            if(745<touch.x && touch.x<810 && 340<touch.y && touch.y<402){
                musicElochka1.stop();
                musicElochka2.play();
            }
        }
        // события
        for (SnowFlake f: flakes) f.fly();

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (SnowFlake f: flakes) {
            batch.draw(imgFlake, f.x, f.y, f.width/2, f.height/2, f.width, f.height, 1, 1, f.rotation, 0, 0, imgFlake.getWidth(), imgFlake.getHeight(), false, false);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgBackGround.dispose();
        imgFlake.dispose();
        musicElochka1.dispose();
        musicElochka2.dispose();
    }
}
