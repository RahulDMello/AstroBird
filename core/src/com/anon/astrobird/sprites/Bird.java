package com.anon.astrobird.sprites;

import com.anon.astrobird.AstroBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Rahul on 06-02-2016.
 */
public class Bird {
    private static final int GRAVITY = -10;
    private static boolean ANTI_GRAVITY;
    private Texture bird;
    private BirdAnimation animate;
    private Vector3 velocity;
    private Vector3 position;
    private Sound sound;
    private static float MOVEMENT;

    private Rectangle birdBounds;

    public Bird(int x,int y){
        bird = new Texture("birdanimation.png");
        animate = new BirdAnimation(bird);
        velocity = new Vector3(0,0,0);
        ANTI_GRAVITY = false;
        position = new Vector3(x,y,0);
        sound = Gdx.audio.newSound(Gdx.files.internal("reversegravity.ogg"));
        MOVEMENT = 1;
        birdBounds = new Rectangle(x + (bird.getWidth()/6), y + (bird.getHeight()/2), 3, 3);
    }

    public void reverseGravity(){
        sound.play(0.6f);
        ANTI_GRAVITY = !ANTI_GRAVITY;
        //velocity.y = ANTI_GRAVITY ? -180:180;
    }

    public void update(float dt){
        animate.update(dt);
        if(position.y > 0 && !ANTI_GRAVITY)
            velocity.add(0, GRAVITY, 0);
        if(ANTI_GRAVITY && position.y < AstroBird.HEIGHT/2 - 34)
            velocity.add(0,-GRAVITY,0);
        velocity.scl(dt);
        position.add(MOVEMENT,velocity.y,0);
        if(position.y < 0 )
            position.y = 0;
        if(position.y > AstroBird.HEIGHT/2 - 20)
            position.y = AstroBird.HEIGHT/2 - 20;
        velocity.scl(1/dt);
        if(dt >= 30 && dt%10 == 0)
            MOVEMENT = MOVEMENT + (1/(3*dt));
        else if(dt < 20)
            MOVEMENT = MOVEMENT + (dt/30);
        birdBounds.setPosition(position.x + (bird.getWidth() / 6), position.y + (bird.getHeight() / 2));
    }

    public void dispose(){
        bird.dispose();
        sound.dispose();
    }

    public TextureRegion getBirdTexture() {
        return animate.getFrame();
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBirdBounds() {
        return birdBounds;
    }
}
