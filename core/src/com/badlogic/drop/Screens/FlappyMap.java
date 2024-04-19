package com.badlogic.drop.Screens;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Drop.MAP;
import com.badlogic.drop.Sprites.AnKhangHero;
import com.badlogic.drop.Sprites.Hero.State;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.drop.Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FlappyMap extends PlayScreen{
	private final int SPEED = 10;
	private final int GRAVITY = -50;
	private float timeCount;
	private TextureAtlas flyEngineAtlas;
	private Animation<TextureRegion> flyEngineAnimation;
	public FlappyMap(Drop game) {
		game.setMap(MAP.MAP2);
		atlas = new TextureAtlas("Hero.pack");
		this.game = game;

		// create camera
		camera = new OrthographicCamera();
		
		// load background
		backgroundTexture = new Texture("background.png");
		
		// viewport => responsive 
		gamePort = new FitViewport(Drop.V_WIDTH/Drop.PPM, Drop.V_HEIGHT/Drop.PPM,camera);		
		
		// load tilemap into world
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map2.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1/Drop.PPM);
		
		// setup box2d
		world = new World(new Vector2(0,GRAVITY),true);
		b2dr = new Box2DDebugRenderer();
		new B2WorldCreator(world, map, this);
		
		// create hero
		region = atlas.findRegion("HeroIdle");
		prepareFlyEngineAnimation();
		player = new AnKhangHero(world,this);
		player.body.setLinearVelocity(SPEED,0);
		timeCount = 0;
	}
	public TextureAtlas getAtlas() {
		return atlas;
	}
	private void prepareFlyEngineAnimation() {
		flyEngineAtlas = new TextureAtlas("asset/map2/packs/can-dau-van.atlas");
		Array<AtlasRegion> flipXArray = new Array<AtlasRegion>();
		Array<AtlasRegion> originArray = flyEngineAtlas.getRegions();
		for (AtlasRegion region : originArray) {
			region.flip(true, false);
			flipXArray.add(region);
		}
		flyEngineAnimation = new Animation<TextureRegion>(0.1f,flipXArray);
		flyEngineAnimation.setPlayMode(PlayMode.LOOP);
		
	}
	// method that be called every 1/60s
	public void update(float dt) {
		//time count for speed up
		timeCount+=dt;
		
		player.currentState = State.STANDING;


		handleInput(dt);
		player.update(dt);
		world.step(1/60f, 6, 2);
		//handle camera out of bound
		if(player.body.getPosition().x-gamePort.getWorldWidth()/2 < 0) 
			camera.position.x = gamePort.getWorldWidth()/2;
		else 
			camera.position.x = player.body.getPosition().x;
		
		renderer.setView(camera);
		camera.update();
		
	}
	public Body getBody() {
		return player.body;
	}
	private void heroJump() {
		getBody().setLinearVelocity(SPEED*(1+ timeCount/10), 17);

	}
	protected void handleInput(float dt) {
		if (Gdx.input.isTouched()||Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			heroJump();
		}
		
	}

	@Override
	public void render(float delta) {
		
		
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		
        game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(backgroundTexture, camera.position.x - gamePort.getWorldWidth() / 2,
				camera.position.y - gamePort.getWorldHeight() / 2, gamePort.getWorldWidth(),
				gamePort.getWorldHeight());
		game.batch.end();
				
		renderer.render();
		
		b2dr.render(world, camera.combined);
		game.batch.begin();
		player.draw(game.batch);
		game.batch.draw(flyEngineAnimation.getKeyFrame(player.getStateTime()), getBody().getPosition().x-player.getRegionWidth()/1.2f/Drop.PPM, getBody().getPosition().y-player.getRegionHeight()/Drop.PPM,flyEngineAnimation.getKeyFrame(0).getRegionWidth()*0.75f/Drop.PPM,flyEngineAnimation.getKeyFrame(delta).getRegionHeight()*0.8f/Drop.PPM);
		game.batch.end();
		
		worldContactListener = new WorldContactListener();
		world.setContactListener(worldContactListener);

		update(delta);

		}

		

		

	}


