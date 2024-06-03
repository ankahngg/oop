package com.badlogic.drop.Screens;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.CuocChienSinhTon.MAP;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

public class DieScreen implements Screen {
	public CuocChienSinhTon game;
	private OrthographicCamera camera;
	private Texture backgroundTexture;
	private FitViewport gamePort;
	private SpriteBatch batch;
	
	private Texture playButton;
	private Texture playButtonHover;
	
	private BitmapFont font;
	private Skin skin;
	private Stage stage;
	private ImageButton PlayButton;
	private Texture exitButton;
	private Texture exitButtonHover;
	private Texture optionButton;
	private Texture optionButtonHover;
	private ImageButton OptionButton;
	private ImageButton ExitButton;
	private Texture againButton;
	private Texture againButtonHover;
	private ImageButton AgainButton;
	
	
	public DieScreen(CuocChienSinhTon game, PlayScreen screen) {
		this.game = game;
		camera = new OrthographicCamera();
		backgroundTexture = new Texture("Menu/DieScreen.png");
		exitButton = new Texture("Menu/Exit.png");
		exitButtonHover = new Texture("Menu/ExitHover.png");
		againButton = new Texture("Menu/Again.png");
		againButtonHover = new Texture("Menu/AgainHover.png");
		
		gamePort = new FitViewport(CuocChienSinhTon.V_WIDTH, CuocChienSinhTon.V_HEIGHT,camera);	
		batch = game.getBatch();	
		stage = new Stage(gamePort);
		
		Table tabel = new Table();  
	
		tabel.center();
		tabel.setFillParent(true);
		
		 ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
		 exitButtonStyle.up = new TextureRegionDrawable(exitButton);
		//style.down = new TextureRegionDrawable(playButtonHover);
		 exitButtonStyle.over = new TextureRegionDrawable(exitButtonHover);
		 ExitButton = new ImageButton(exitButtonStyle);
		
		 ExitButton.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                // Handle button click
	            	dispose();
	                game.setScreen(new Menu2(game));
	              
	            }
	        });
		 
		 ImageButton.ImageButtonStyle againButtonStyle = new ImageButton.ImageButtonStyle();
		 againButtonStyle.up = new TextureRegionDrawable(againButton);
		//style.down = new TextureRegionDrawable(playButtonHover);
		 againButtonStyle.over = new TextureRegionDrawable(againButtonHover);
		 AgainButton = new ImageButton(againButtonStyle);
		
		 AgainButton.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	if(screen instanceof FirstMap) game.setScreen(new FirstMap(game));
	            	else game.setScreen(new FlappyMap(game));
	            }
	        });
		 tabel.add(AgainButton).padTop(10);
		 tabel.row();
		 tabel.add(ExitButton).padTop(10);
		 
		 stage.addActor(tabel);
		 
		 
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void render(float delta) {
		Gdx.input.setInputProcessor(stage);
		game.batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		game.batch.draw(backgroundTexture, 0,0, gamePort.getWorldWidth(),gamePort.getWorldHeight());
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height,true);
		
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
	
		stage.dispose();
	}
}
