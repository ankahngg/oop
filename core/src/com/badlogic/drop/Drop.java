	package com.badlogic.drop;
	import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
	import com.badlogic.gdx.Screen;
	import com.badlogic.gdx.graphics.OrthographicCamera;
	import com.badlogic.gdx.graphics.Texture;
	import com.badlogic.gdx.graphics.g2d.Sprite;
	import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	import com.badlogic.gdx.maps.tiled.TiledMap;
	import com.badlogic.gdx.maps.tiled.TmxMapLoader;
	import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
	import com.badlogic.gdx.utils.ScreenUtils;
	
	public class Drop extends Game {
			
		public static final int V_WIDTH=500;
		public static final int V_HEIGHT=320;
		public static final float PPM = 100;
		OrthographicCamera camera;
		public SpriteBatch batch;
		
		public void create() {
			batch = new SpriteBatch();
			
			this.setScreen(new PlayScreen(this));
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
	}
	
	
	