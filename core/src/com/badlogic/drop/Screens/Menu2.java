package com.badlogic.drop.Screens;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.CuocChienSinhTon.MAP;
import com.badlogic.drop.MapUserData;
import com.badlogic.drop.Tools.AudioManagement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

public class Menu2 implements Screen {
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
	
	
	public Menu2(CuocChienSinhTon game) {
		this.game = game;
		camera = new OrthographicCamera();
		backgroundTexture = new Texture("menu_background.jpg");
		playButton = new Texture("Menu/Play.png");
		playButtonHover = new Texture("Menu/PlayHover.png");

		exitButton = new Texture("Menu/Exit.png");
		exitButtonHover = new Texture("Menu/ExitHover.png");
		
		optionButton = new Texture("Menu/Option.png");
		optionButtonHover = new Texture("Menu/OptionHover.png");
		
		
		gamePort = new FitViewport(CuocChienSinhTon.V_WIDTH, CuocChienSinhTon.V_HEIGHT,camera);	
		batch = game.getBatch();	
		stage = new Stage(gamePort);
		Table tabel = new Table();  
		tabel.center();
		tabel.setFillParent(true);
		
		//PlayButton
		 ImageButton.ImageButtonStyle playbuttonstyle = new ImageButton.ImageButtonStyle();
		 playbuttonstyle.up = new TextureRegionDrawable(playButton);
		 //style.down = new TextureRegionDrawable(playButtonHover);
		 playbuttonstyle.over = new TextureRegionDrawable(playButtonHover);
		 PlayButton = new ImageButton(playbuttonstyle);
		
		 PlayButton.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                // Handle button click
	            	dispose();
	            	game.setScreen(new FirstMap(game));
	            	
	            }
	        });
		 
		 //Option Button
		 ImageButton.ImageButtonStyle optionButtonStyle = new ImageButton.ImageButtonStyle();
		 optionButtonStyle.up = new TextureRegionDrawable(optionButton);
		 //style.down = new TextureRegionDrawable(playButtonHover);
		 optionButtonStyle.over = new TextureRegionDrawable(optionButtonHover);
		 OptionButton = new ImageButton(optionButtonStyle);
		
		 OptionButton.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                // Handle button click
	                System.out.println("Option Button clicked!");
	            }
	        });
		 
		 //Exit Button
		 ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
		 exitButtonStyle.up = new TextureRegionDrawable(exitButton);
		//style.down = new TextureRegionDrawable(playButtonHover);
		 exitButtonStyle.over = new TextureRegionDrawable(exitButtonHover);
		 ExitButton = new ImageButton(exitButtonStyle);
		
		 ExitButton.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                // Handle button click
	                System.exit(0);
	            }
	        });
		 
		 

		 tabel.add(PlayButton).padTop(10);
		 tabel.row();
		 tabel.add(OptionButton).padTop(10);
		 tabel.row();
		 tabel.add(ExitButton).padTop(10);
	    // Add the button to the stage
        stage.addActor(tabel);

        // Make the stage handle input
        Gdx.input.setInputProcessor(stage);
      AudioManagement.manager.get("Music/NhacMenu.mp3",Music.class).play();
      AudioManagement.manager.get("Music/NhacMenu.mp3",Music.class).isLooping();
      
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void render(float delta) {
		MapUserData.clearData();
		ScreenUtils.clear(0, 0, 0.2f, 1);
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
		AudioManagement.manager.get("Music/NhacMenu.mp3",Music.class).dispose();
	}
}
