package com.badlogic.drop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.drop.CuocChienSinhTon;

public class DesktopLauncher {
   public static void main (String[] arg) {
      Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
      //Texture icon = new Texture("")
      config.setWindowIcon("icon2.jpg");
      config.setTitle("Cuộc chiến sinh tồn");
      config.setWindowedMode(CuocChienSinhTon.V_WIDTH*2,CuocChienSinhTon.V_HEIGHT*2 );
      config.useVsync(true);
      config.setForegroundFPS(120);
      new Lwjgl3Application(new CuocChienSinhTon(), config);
   }
}

