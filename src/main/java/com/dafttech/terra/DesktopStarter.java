package com.dafttech.terra;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopStarter {

    public static void main(String[] args) {
        new LwjglApplication(TerraInfinita.$, "TerraInfinita", 1440, 900/*, true*/);
    }

}
