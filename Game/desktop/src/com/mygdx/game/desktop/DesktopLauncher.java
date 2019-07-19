package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.AndroidGame;
import com.mygdx.game.common.GameData;
import com.mygdx.game.desktop.ads.DesktopAdController;
import com.mygdx.game.util.interfaces.AdController;

public class DesktopLauncher {

	private static final AdController AD_CONTROLLER = new DesktopAdController();

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) GameData.WIDTH;
		config.height = (int) GameData.HEIGHT;

		new LwjglApplication(new AndroidGame(AD_CONTROLLER), config);
	}
}
