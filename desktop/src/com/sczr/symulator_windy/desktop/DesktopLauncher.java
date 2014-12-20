package com.sczr.symulator_windy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sczr.symulator_windy.SCZRApplication;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		int windowWidth = 700;
		int windowHeight = 600;
		config.width = windowWidth;
		config.height = windowHeight;
		config.resizable = false;
		new LwjglApplication(new SCZRApplication(windowWidth, windowHeight), config);
	}
}
