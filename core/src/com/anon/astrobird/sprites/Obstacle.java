package com.anon.astrobird.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Rahul on 06-02-2016.
 */
public class Obstacle {

    public static final int  OBSTACLE_WIDTH = 52;
    private static final int OBSTACLE_GAP = 120, LOWEST_OPENING = 120;
    private boolean frame;

    private float maxFrameTIme;
    private float currFrameTime;

    private Texture topTube;
    private Texture btmTube;

    private Texture greenTopTube;
    private Texture greenBtmTube;

    private Texture redTopTube;
    private Texture redBtmTube;

    private Rectangle topTubeBounds;
    private Rectangle btmTubeBounds;

    private Vector2 topTubePos, btmTubePos;

    private Random rand;

    public Obstacle(float x,boolean check){
        frame = check;
        maxFrameTIme = 1;
        currFrameTime = 0;
        greenTopTube = new Texture("greentoptube.png");
        greenBtmTube = new Texture("greenbottomtube.png");
        redTopTube = new Texture("redtoptube.png");
        redBtmTube = new Texture("redbottomtube.png");
        topTube = greenTopTube;
        btmTube = greenBtmTube;
        rand = new Random();
        topTubePos = new Vector2(x, rand.nextInt(130) + OBSTACLE_GAP + LOWEST_OPENING);
        btmTubePos = new Vector2(x, topTubePos.y - OBSTACLE_GAP - greenBtmTube.getHeight());
        topTubeBounds = new Rectangle(topTubePos.x,topTubePos.y,topTube.getWidth(),topTube.getHeight());
        btmTubeBounds = new Rectangle(btmTubePos.x, btmTubePos.y, btmTube.getWidth(), btmTube.getHeight());
    }

    public void reposition(float x){
        topTubePos.set(x,rand.nextInt(130) + OBSTACLE_GAP + LOWEST_OPENING);
        btmTubePos.set(x,topTubePos.y - OBSTACLE_GAP - btmTube.getHeight());

        topTubeBounds.setPosition(topTubePos);
        btmTubeBounds.setPosition(btmTubePos);
    }

    public Boolean collides(Rectangle player){
        return player.overlaps(topTubeBounds) || player.overlaps(btmTubeBounds);
        //return false;
    }

    public void update(float dt){
        currFrameTime +=dt;
        if(currFrameTime > maxFrameTIme){
            frame=!frame;
            currFrameTime = 0;
        }
        topTube = frame ? greenTopTube : redTopTube;
        btmTube = frame ? greenBtmTube : redBtmTube;
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBtmTube() {
        return btmTube;
    }

    public Vector2 getTopTubePos() {
        return topTubePos;
    }

    public Vector2 getBtmTubePos() {
        return btmTubePos;
    }

    public void dispose(){
        greenBtmTube.dispose();
        greenTopTube.dispose();
        redBtmTube.dispose();
        redTopTube.dispose();
        topTube.dispose();
        btmTube.dispose();
    }
}
