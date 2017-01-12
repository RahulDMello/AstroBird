package com.anon.astrobird.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Rahul on 06-02-2016.
 */
public class BirdAnimation {
    private Array<TextureRegion> frames;
    private int frameCount;
    private int currFrame;
    private float currFrameTime;
    private float maxFrameTime;

    public BirdAnimation(Texture bird){
        maxFrameTime = 0.2f;
        currFrameTime = 0;
        currFrame = 0;
        frameCount = 3;
        int frameWidth = bird.getWidth()/frameCount;
        frames = new Array<TextureRegion>();
        for(int i=0;i<frameCount;i++){
            frames.add(new TextureRegion(bird,i*frameWidth,0,frameWidth,bird.getHeight()));
        }
    }

    public void update(float dt){
        currFrameTime +=dt;
        if(currFrameTime > maxFrameTime){
            currFrame++;
            currFrameTime = 0;
        }
        if(currFrame >= frameCount)
            currFrame =0;
    }


    public TextureRegion getFrame() {
        return frames.get(currFrame);
    }
}
