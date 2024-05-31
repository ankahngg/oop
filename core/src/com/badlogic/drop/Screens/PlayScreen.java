package com.badlogic.drop.Screens;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Scenes.HealthBar;
import com.badlogic.drop.Sprites.Boss;
import com.badlogic.drop.Sprites.Boss1;
import com.badlogic.drop.Sprites.BulletManage;
import com.badlogic.drop.Sprites.Collision;
import com.badlogic.drop.Sprites.Hero;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.drop.Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class PlayScreen implements Screen {
	public CuocChienSinhTon game;
	Texture backgroundTexture;

	// camera
	OrthographicCamera camera,camera2;
	Viewport gamePort;

	// tilemap
	protected TmxMapLoader mapLoader;
	protected TiledMap map;
	protected OrthogonalTiledMapRenderer renderer;
	
	//Box2d
	public World world;
	public Box2DDebugRenderer b2dr;
	// speed
	protected float speed;
	//Hero
	protected Hero player;
	
	//Boss
	protected Boss boss;

	//HealthBar
	protected HealthBar healthbar;
	
	//map
	protected TextureAtlas atlas;
	protected TextureRegion region;

	
	public WorldContactListener worldContactListener;

	// music
	public Music backgroundMusic;
	public Sound heroHurtSound;
	public Stage stage;
	public Texture homeButton;
	public Texture homeButtonHover;
	public ImageButton HomeButton;
	public boolean pause=false;
	public StageCreator stageCreator;
	public BulletManage bulletManage;
	private Texture resumeButton;
	private Texture resumeButtonHover;
	private Texture quitButton;
	private Texture quitButtonHover;
	private Texture optionButton;
	private Texture optionButtonHover;
	public Stage pauseStage;
	private ImageButton OptionButton;
	private ImageButton ExitButton;
	private Texture exitButton;
	private ImageButton QuitButton;
	private ImageButton ResumeButton;
	public DieScreen dieScreen;
	public boolean isPlayerDie = false;

	public PlayScreen(CuocChienSinhTon game) {
		this.game = game;
		camera = new OrthographicCamera();
		gamePort = new FitViewport(CuocChienSinhTon.V_WIDTH, CuocChienSinhTon.V_HEIGHT,camera);
		
		setUpPauseButton();
		setUpPauseStage();
		
		dieScreen = new DieScreen(game,this);
		//Gdx.input.setInputProcessor(stage);
		
		 
		loadSound();
	}
	

	public void setUpPauseButton() {
		stage = new Stage(gamePort);
		
		/// Home button
		homeButton = new Texture("Menu/Home.png");
		homeButtonHover = new Texture("Menu/HomeHover.png");
		
		ImageButton.ImageButtonStyle homebuttonstyle = new ImageButton.ImageButtonStyle();
		homebuttonstyle.up = new TextureRegionDrawable(homeButton);
		 //style.down = new TextureRegionDrawable(playButtonHover);
		homebuttonstyle.over = new TextureRegionDrawable(homeButtonHover);
		 HomeButton = new ImageButton(homebuttonstyle);
		
		 HomeButton.addListener(new ClickListener() {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	                pause = true;
	              
	            }
	        });
		 
		 HomeButton.setPosition(10, gamePort.getWorldHeight()-homeButton.getHeight()-10);
		 stage.addActor(HomeButton);
		 
	}
	
	public void setUpPauseStage() {
		pauseStage = new Stage(gamePort);
		resumeButton = new Texture("Menu/Resume.png");
		 resumeButtonHover = new Texture("Menu/ResumeHover.png");
		 
		 quitButton = new Texture("Menu/Quit.png");
		 quitButtonHover = new Texture("Menu/QuitHover.png");
		 
		 optionButton = new Texture("Menu/Option.png");
		 optionButtonHover = new Texture("Menu/OptionHover.png");
		 
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
	            	pause = false;
	            	
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
	                dispose();
	            }
	        });
		 Table table = new Table();
		 table.setFillParent(true);
		 table.center();
		 table.add(ResumeButton).padTop(10);
		 table.row();
		 table.add(OptionButton).padTop(10);
		 table.row();
		 table.add(QuitButton).padTop(10);
		 pauseStage.addActor(table);
//		 Gdx.input.setInputProcessor(pauseStage);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	public void loadSound() {
		heroHurtSound =  Gdx.audio.newSound(Gdx.files.internal("sound/hero/heroHurt.wav"));
	}
	public Viewport getGamePort() {
		return gamePort;
	}

	public Boss getBoss() {
		return this.boss;
	}

	public Hero getPlayer() {
		return player;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(float delta) {
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
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
	public float getSpeed() {
		return speed;
	}
	public void playHeroHurtSound() {
		heroHurtSound.play();
	}
	public abstract void handleDie();

}
