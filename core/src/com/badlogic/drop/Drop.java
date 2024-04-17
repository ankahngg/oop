	package com.badlogic.drop;
	import com.badlogic.drop.Screens.LabScreen;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
	import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
	import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	import com.badlogic.gdx.maps.tiled.TiledMap;
	import com.badlogic.gdx.maps.tiled.TmxMapLoader;
	import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
	
	public class Drop extends Game {
			
		public static final int V_WIDTH=500;
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
			
			this.setScreen(new FlappyMap(this));
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
			this.map = map;
		}

	}
	
	
	