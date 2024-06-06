	package com.badlogic.drop;
	import java.util.HashMap;


	import com.badlogic.drop.Screens.FirstMap;
	import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.Menu2;
import com.badlogic.gdx.Game;
	import com.badlogic.gdx.Screen;
	import com.badlogic.gdx.graphics.OrthographicCamera;
	import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Screens.ScreenManagement;
import com.badlogic.drop.Tools.AudioManagement;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	
	public class CuocChienSinhTon extends Game {
		
			
		public static final int V_WIDTH=560;
		public static final int V_HEIGHT=320;
		public static final float PPM = 16;
		public  enum MAP {MAP1, MAP2,MAP3};
		public  HashMap<MAP, Screen> screenMap = new HashMap<CuocChienSinhTon.MAP, Screen>();
		private MAP map;
		public FirstMap map1;
		public FlappyMap map2;
		OrthographicCamera camera;
		public SpriteBatch batch;
		
		public SpriteBatch getBatch() {
			return batch;
		}

		public void create() {
			AudioManagement.setUp();
			batch = new SpriteBatch();
			
			setScreen(new Menu2(this));
			
			
		}
		@Override
		public void render() {
			// TODO Auto-generated method stub
			try {
				super.render();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
//		public void setNewScreen(Screen screen) {
//			this.setScreen(screen);
//			if(screen instanceof FirstMap) map = MAP.MAP1;
//			else map = MAP.MAP1;
//		}
		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			batch.dispose();
			super.dispose();
			
		}
		
		public MAP getCurrentMap() {
			return map;
		}
		public void setMap(MAP map) {
			
			this.map = map;
			switch (map) {
				case MAP1:
					setScreen(map1);
					break;
	
				case MAP2:
					setScreen(map2);
				default:
					break;
				}
			}
			
		

	}
	
	
	