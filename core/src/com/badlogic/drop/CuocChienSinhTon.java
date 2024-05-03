	package com.badlogic.drop;
	import java.util.HashMap;

import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	
	public class CuocChienSinhTon extends Game {
			
		public static final int V_WIDTH=530;
		public static final int V_HEIGHT=320;
		public static final float PPM = 16;
		public  enum MAP {MAP1, MAP2,MAP3};
		public  HashMap<MAP, Screen> screenMap = new HashMap<CuocChienSinhTon.MAP, Screen>();
		private MAP map;
		OrthographicCamera camera;
		public SpriteBatch batch;
		
		public SpriteBatch getBatch() {
			return batch;
		}

		public void create() {
			map = MAP.MAP1;
			batch = new SpriteBatch();
			this.setScreen(new FirstMap(this));
		}
		
		@Override
		public void render() {
			// TODO Auto-generated method stub
			super.render();
		}
		
		public void setNewScreen(Screen screen) {
			this.setScreen(screen);
		}
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
			
			if (this.map != map) {
				this.map = map;
				switch (map) {
					case MAP1:
						this.setScreen(new FirstMap(this));
						break;
		
					case MAP2:
						this.setScreen(new FlappyMap(this));
					default:
						break;
					}
			}
			
		}

	}
	
	
	