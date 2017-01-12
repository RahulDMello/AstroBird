package com.anon.astrobird.states;

import com.anon.astrobird.AstroBird;
import com.anon.astrobird.sprites.Bird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Rahul on 06-02-2016.
 */
public class MenuState extends State {

    private Texture bkground;
    private Texture playBtn;
    private Music music;
    private BitmapFont font;
    private Bird bird;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        music = Gdx.audio.newMusic(Gdx.files.internal("menuscreenmusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.4f);
        cam.setToOrtho(false, (AstroBird.WIDTH) + 160, (AstroBird.HEIGHT));
        bkground = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        bird = new Bird(AstroBird.WIDTH / 2 + 100,AstroBird.HEIGHT / 2 + 100);
        music.play();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()){
            music.pause();
            gsm.set(new PlayScreen(gsm));
        }
    }

    @Override
    public void update(float dt) {
        bird.update(dt);
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bkground, 0, 0, cam.viewportWidth, cam.viewportHeight);
        sb.draw(bird.getBirdTexture(),AstroBird.WIDTH/2 + 15, AstroBird.HEIGHT - 90,75,75);
        sb.draw(playBtn,AstroBird.WIDTH/2 - 20,AstroBird.HEIGHT/2 - playBtn.getHeight()/2);
        font.draw(sb,"H I G H  S C O R E : "+String.valueOf(AstroBird.getHighScore()),AstroBird.WIDTH/2 , AstroBird.HEIGHT/2 - playBtn.getHeight()/2 - 15);

        sb.end();
    }

    @Override
    public void dispose() {
        bkground.dispose();
        playBtn.dispose();
        font.dispose();
        bird.dispose();
        music.dispose();
    }
}
