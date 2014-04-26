package com.siondream.ld29.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.siondream.ld29.LudumDare;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Beneath the surface";
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new LudumDare(), config);
	}
}
