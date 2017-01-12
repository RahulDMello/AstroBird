package com.anon.astrobird;

import com.anon.astrobird.states.GameStateManager;
import com.anon.astrobird.states.MenuState;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AstroBird extends ApplicationAdapter {
	public static final int HEIGHT = 800;
	public static final int WIDTH = 480;
	public GameStateManager gsm = new GameStateManager();
	public static final String TITLE = "ASTRO BIRD";

	private SpriteBatch batch;
	private static Preferences pref;

	
	@Override
	public void create () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		batch = new SpriteBatch();
		gsm.push(new MenuState(gsm));
		pref = Gdx.app.getPreferences("highscore");
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	public static void updateHighScore(int score){
		pref.putInteger("score",score);
		pref.flush();
	}

	public static int getHighScore(){
		return pref.getInteger("score", 0);
	}
}
