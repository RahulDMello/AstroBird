package com.anon.astrobird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.anon.astrobird.AstroBird;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=AstroBird.WIDTH;
		config.height=AstroBird.HEIGHT;
		config.title=AstroBird.TITLE;
		new LwjglApplication(new AstroBird(), config);
	}
}
