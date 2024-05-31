package com.badlogic.drop.Screens;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PauseScreen implements Screen{
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
	private Texture resumeButton;
	private Texture resumeButtonHover;
	private Texture quitButton;
	private Texture quitButtonHover;
	private ImageButton ResumeButton;
	private ImageButton QuitButton;
	
	public PauseScreen(CuocChienSinhTon game) {
		this.game = game;
		camera = new OrthographicCamera();
		backgroundTexture = new Texture("menu_background.jpg");
		resumeButton = new Texture("Menu/Resume.png");
		resumeButtonHover = new Texture("Menu/ResumeHover.png");

		quitButton = new Texture("Menu/Quit.png");
		quitButtonHover = new Texture("Menu/QuitHover.png");
		
		optionButton = new Texture("Menu/Option.png");
		optionButtonHover = new Texture("Menu/OptionHover.png");
		
		
		gamePort = new FitViewport(CuocChienSinhTon.V_WIDTH, CuocChienSinhTon.V_HEIGHT,camera);	
		batch = game.getBatch();	
		stage = new Stage(gamePort);
		Table tabel = new Table();  
		tabel.center();
		tabel.setFillParent(true);
		
		//ResumeButton
		 ImageButton.ImageButtonStyle resumeButtonStyle = new ImageButton.ImageButtonStyle();
		 resumeButtonStyle.up = new TextureRegionDrawable(resumeButton);
		 //style.down = new TextureRegionDrawable(playButtonHover);
		 resumeButtonStyle.over = new TextureRegionDrawable(resumeButtonHover);
		 ResumeButton = new ImageButton(resumeButtonStyle);
		
		 ResumeButton.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                // Handle button click
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
		 
		 //Quit Button
		 ImageButton.ImageButtonStyle quitButtonStyle = new ImageButton.ImageButtonStyle();
		 quitButtonStyle.up = new TextureRegionDrawable(quitButton);
		//style.down = new TextureRegionDrawable(playButtonHover);
		 quitButtonStyle.over = new TextureRegionDrawable(quitButtonHover);
		 QuitButton = new ImageButton(quitButtonStyle);
		
		 QuitButton.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                // Handle button click
	                game.setScreen(new Menu2(game));
	            }
	        });
		 
		 

		 tabel.add(ResumeButton).padTop(10);
		 tabel.row();
		 tabel.add(OptionButton).padTop(10);
		 tabel.row();
		 tabel.add(QuitButton).padTop(10);
	    // Add the button to the stage
        stage.addActor(tabel);

        // Make the stage handle input
        Gdx.input.setInputProcessor(stage);
      
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void render(float delta) {
		
		game.batch.setProjectionMatrix(camera.combined);
	
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
		// TODO Auto-generated method stub
		
	}
}
