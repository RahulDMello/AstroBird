package com.anon.astrobird.states;

import com.anon.astrobird.AstroBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Rahul on 06-02-2016.
 */
public class EndScreen extends State{
    private Texture gameOver;
    private Music music;
    private BitmapFont font;

    public EndScreen(GameStateManager gsm) {
        super(gsm);
        gameOver = new Texture("gameover.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        cam.setToOrtho(false, AstroBird.WIDTH, AstroBird.HEIGHT);
        music = Gdx.audio.newMusic(Gdx.files.internal("menuscreenmusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.6f);
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
        if(AstroBird.getHighScore() < PlayScreen.getScore())
            AstroBird.updateHighScore(PlayScreen.getScore());
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(gameOver, 0, 0, AstroBird.WIDTH, AstroBird.HEIGHT);
        font.draw(sb,"Y O U R   S C O R E : "+String.valueOf(PlayScreen.getScore()),AstroBird.WIDTH/2 - 75,AstroBird.HEIGHT/2 - 110);
        font.draw(sb,"H I G H  S C O R E : "+String.valueOf(AstroBird.getHighScore()),AstroBird.WIDTH/2 - 75, AstroBird.HEIGHT/2 - 160);

        sb.end();
    }

    @Override
    public void dispose() {
        gameOver.dispose();
    }
}
