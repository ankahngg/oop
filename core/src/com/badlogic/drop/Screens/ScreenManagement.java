package com.badlogic.drop.Screens;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.CuocChienSinhTon.MAP;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ScreenManagement implements Screen {
	public enum State {MAP1,MAP2,MENU,DIE,WIN,PAUSE };
	public State currentState;
	public CuocChienSinhTon game;
	private SpriteBatch Batch;
	private Menu2 menu;
	private FirstMap map1;
	private FlappyMap map2;
	private OrthographicCamera camera;
	private FitViewport gamePort;
	

	public ScreenManagement(CuocChienSinhTon game) {
		this.game = game;
		currentState = State.MAP1;
		camera = new OrthographicCamera();
		gamePort = new FitViewport(CuocChienSinhTon.V_WIDTH/CuocChienSinhTon.PPM, CuocChienSinhTon.V_HEIGHT/CuocChienSinhTon.PPM,camera);		
		menu = new Menu2(game);
//		map1 = new FirstMap(game,camera,gamePort);
		//map2 = new FlappyMap(game);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.O)) currentState = State.MAP1;
		if(Gdx.input.isKeyJustPressed(Keys.P)) currentState = State.MENU;
		switch (currentState) {
			case MENU:
				menu.render(delta);
				break;
			case MAP1:
				map1.render(delta);
				break;
			case MAP2:
				map2.render(delta);
				break;
	
			default:
				break;
		}
		
		
	}

	@Override
	public void resize(int width, int height) {
	
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
		
	}
}
