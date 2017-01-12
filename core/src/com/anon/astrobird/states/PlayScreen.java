package com.anon.astrobird.states;

import com.anon.astrobird.AstroBird;
import com.anon.astrobird.sprites.Bird;
import com.anon.astrobird.sprites.Coin;
import com.anon.astrobird.sprites.Obstacle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import javax.xml.soap.Text;

/**
 * Created by Rahul on 06-02-2016.
 */
public class PlayScreen extends State {

    private static final int OBSTACLE_COUNT = 4;
    private static final int OBSTACLE_SPACING = 170;
    private static final int GROUND_OFFSET = -50;
    private static final int HORRIZONTAL_OFFSET = 300;
    private Texture background;
    private Texture ground;
    private Texture base;
    private Music music;
    private int initBirdPos;
    private static int score;
    private Vector2 groundPos1;
    private Vector2 groundPos2;
    private Bird bird;
    private Array<Obstacle> obstacles;
    private BitmapFont font;
    private Array<Coin> coins;
    private static int coinCount;
    private Music coinMusic;
    private boolean start;


    public PlayScreen(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, AstroBird.WIDTH / 2 + 160, AstroBird.HEIGHT / 2);
        initBirdPos = 50;
        bird= new Bird(initBirdPos,AstroBird.HEIGHT/4);
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth,GROUND_OFFSET);
        groundPos2 = new Vector2(groundPos1.x+ground.getWidth(),GROUND_OFFSET);
        base = new Texture("base.png");
        background = new Texture("background.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("backgroundmusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        start = false;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        coinCount = 0;
        obstacles = new Array<Obstacle>();
        coins = new Array<Coin>();
        for(int i=0 ; i<OBSTACLE_COUNT ;i++){
            obstacles.add(new Obstacle(i * (OBSTACLE_SPACING + Obstacle.OBSTACLE_WIDTH) + HORRIZONTAL_OFFSET ,i%2==0));

            coins.add(new Coin(i * (OBSTACLE_SPACING + Obstacle.OBSTACLE_WIDTH) + HORRIZONTAL_OFFSET,obstacles.get(i).getTopTubePos().y - 70));
        }
        music.play();
        coinMusic = Gdx.audio.newMusic(Gdx.files.internal("coinmusic.mp3"));
        coinMusic.setVolume(0.8f);
        coinMusic.setLooping(false);
    }

    public static int getCoinCount() {
        return coinCount;
    }

    public void updateTubes(float dt){
        for(Obstacle o : obstacles){

            o.update(dt);
        }
        for(Coin c : coins){
            c.update(dt);
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            start = true;
            bird.reverseGravity();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(start) {
            bird.update(dt);
            updateTubes(dt);
            updateGround();
            cam.position.x = bird.getPosition().x + 80;
            score = (int) (bird.getBirdBounds().x) / (OBSTACLE_SPACING + Obstacle.OBSTACLE_WIDTH);
            for (int i = 0; i < obstacles.size; i++) {
                Obstacle o = obstacles.get(i);
                Coin c = coins.get(i);
                if (cam.position.x - (cam.viewportWidth / 2) > o.getTopTubePos().x + o.getTopTube().getWidth()) {
                    o.reposition(o.getTopTubePos().x + ((OBSTACLE_SPACING + Obstacle.OBSTACLE_WIDTH) * OBSTACLE_COUNT));
                    if (!c.isVisibility()) {
                        coinCount++;
                    }
                    c.reposition(c.getPosition().x + ((OBSTACLE_SPACING + Obstacle.OBSTACLE_WIDTH) * OBSTACLE_COUNT), o.getTopTubePos().y - 70);
                }
                if (o.collides(bird.getBirdBounds())) {
                    music.pause();
                    for (Coin coin : coins) {
                        if (!coin.isVisibility())
                            coinCount++;
                    }
                    gsm.set(new EndScreen(gsm));
                }
            }
            for (int i = 0; i < coins.size; i++) {
                Coin c = coins.get(i);
                if (c.collides(bird.getBirdBounds())) {
                    c.setVisibility(false);
                    coinMusic.play();
                }
            }

            if(bird.getBirdBounds().y < groundPos1.y + ground.getHeight() - 20 || bird.getBirdBounds().y < groundPos2.y + ground.getHeight() - 20) {

                music.pause();
                gsm.set(new EndScreen(gsm));
            }

            cam.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0, AstroBird.WIDTH, AstroBird.HEIGHT);
        for(Obstacle o : obstacles){
            sb.draw(o.getTopTube(), o.getTopTubePos().x, o.getTopTubePos().y);
            sb.draw(base,o.getTopTubePos().x + 5 , AstroBird.HEIGHT/2 - 10, 60, 150);
            sb.draw(o.getBtmTube(), o.getBtmTubePos().x, o.getBtmTubePos().y);
            sb.draw(base,  o.getBtmTubePos().x - 3, -100, 60, 150);
        }
        for(Coin c : coins){
            if(c.isVisibility())
                sb.draw(c.getCoin(),c.getPosition().x,c.getPosition().y);
        }
        font.draw(sb, String.valueOf(score), bird.getBirdBounds().x + (cam.viewportWidth / 2) - 10, cam.viewportHeight - 30);
        sb.draw(ground, groundPos1.x,groundPos1.y, cam.viewportWidth + 115, ground.getHeight() - 10);
        sb.draw(ground, groundPos2.x, groundPos2.y, cam.viewportWidth + 115, ground.getHeight() - 10);
        sb.draw(bird.getBirdTexture(), bird.getPosition().x, bird.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        ground.dispose();
        base.dispose();
        music.dispose();
        bird.dispose();
        for(Obstacle o : obstacles)
            o.dispose();
        font.dispose();
    }

    public void updateGround(){
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

    public static int getScore(){
        return score + coinCount;
    }
}
