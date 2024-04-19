	package com.badlogic.drop;
	import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
	import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	
	public class Drop extends Game {
			
		public static final int V_WIDTH=520;
		public static final int V_HEIGHT=320;
		public static final float PPM = 16;
		public static enum MAP {MAP1, MAP2,MAP3};
		private static MAP map;
		OrthographicCamera camera;
		public SpriteBatch batch;
		
		public SpriteBatch getBatch() {
			return batch;
		}

		public void create() {
			batch = new SpriteBatch();
			
			this.setScreen(new FirstMap(this));
		}
		
		@Override
		public void render() {
			// TODO Auto-generated method stub
			super.render();
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
			Drop.map = map;
		}

	}
	
	
	