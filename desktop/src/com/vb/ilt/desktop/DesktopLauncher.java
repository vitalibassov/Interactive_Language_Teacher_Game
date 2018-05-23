package com.vb.ilt.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vb.ilt.InteractiveLangTeacherGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Graphics.DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.setFromDisplayMode(displayMode);
//		config.width = (int)GameConfig.WIDTH;
//		config.height = (int)GameConfig.HEIGHT;
//		config.fullscreen = false;
		new LwjglApplication(new InteractiveLangTeacherGame(), config);
	}
}
