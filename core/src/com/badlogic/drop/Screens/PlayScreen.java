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
	private Music backgroundMusic;
	private Sound heroHurtSound;

	public PlayScreen(CuocChienSinhTon game) {
		this.game = game;
		//set up collision
//		Collision.setup(this);
//		//set up bullet manage		
//		BulletManage.setup(world, this);
//		
//		StageCreator.setup(world, this);
		loadSound();
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
		// TODO Auto-generated method stub
		
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
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
	}
	public float getSpeed() {
		return speed;
	}
	public void playHeroHurtSound() {
		heroHurtSound.play();
	}
	public abstract void handleDie();

}
