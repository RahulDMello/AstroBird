package com.anon.astrobird.sprites;

import com.anon.astrobird.AstroBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import javax.xml.soap.Text;

/**
 * Created by Rahul on 06-02-2016.
 */
public class Coin {
    private TextureRegion coin;
    private Vector3 position;
    private Rectangle coinBounds;
    private boolean coinFlip;
    private float currFrameTime;
    private float maxFrameTime;
    private boolean up;
    private boolean visibility;

    public Coin(float x,float y){
        Texture t = new Texture("coin.png");
        coin = new TextureRegion(t,t.getWidth(),t.getHeight());
        position = new Vector3(x, y,0);
        coinBounds = new Rectangle(position.x,position.y,coin.getRegionWidth(),coin.getRegionHeight());
        coinFlip = false;
        maxFrameTime = 1f;
        currFrameTime = 0;
        up=true;
        visibility = true;
    }

    public void reposition(float x,float y){
        position.x = x;
        position.y = y;
        coinBounds.x = x;
        coinBounds.y = y;
        visibility = true;
    }

    public Boolean collides(Rectangle player){
        return player.overlaps(coinBounds);
        //return false;
    }

    public void update(float dt){
        currFrameTime+=dt;
        if(currFrameTime > maxFrameTime){
            coin.flip(true, false);
            if(up){
                position.y += 10;
            }
            else {
                position.y -= 10;
            }
            up=!up;
            currFrameTime = 0;
        }
    }


    public TextureRegion getCoin() {
        return coin;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getCoinBounds() {
        return coinBounds;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

}
