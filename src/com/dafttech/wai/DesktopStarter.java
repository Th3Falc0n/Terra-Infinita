package com.dafttech.wai;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopStarter {
	public static void main(String[] args) {
		new LwjglApplication(new TerraInfinita(), "TerraInfinita", 800, 600, false);
	}
}
