package com.badlogic.drop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.drop.Drop;

public class DesktopLauncher {
   public static void main (String[] arg) {
      Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
      config.setWindowIcon();
      config.setTitle("Drop");
      config.setWindowedMode(1000, 640);
      config.useVsync(true);
      config.setForegroundFPS(60);
      new Lwjgl3Application(new Drop(), config);
   }
}

