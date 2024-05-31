package com.badlogic.drop.Screens;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import javax.swing.text.View;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.CuocChienSinhTon.MAP;
import com.badlogic.drop.Scenes.HealthBar;
import com.badlogic.drop.Sprites.AnKhangHero;
import com.badlogic.drop.Sprites.Aura;
import com.badlogic.drop.Sprites.Boss;
import com.badlogic.drop.Sprites.Boss1;
import com.badlogic.drop.Sprites.Boss2;
import com.badlogic.drop.Sprites.Bullet;
import com.badlogic.drop.Sprites.BulletManage;
import com.badlogic.drop.Sprites.Collision;
import com.badlogic.drop.Sprites.DragonBallMonster1;
import com.badlogic.drop.Sprites.DragonBallMonster2;
import com.badlogic.drop.Sprites.EnergyBall;
import com.badlogic.drop.Sprites.EyeBullet;
import com.badlogic.drop.Sprites.FlyingEye;
import com.badlogic.drop.Sprites.Hero;
import com.badlogic.drop.Sprites.HungKing;
import com.badlogic.drop.Sprites.Monster;
import com.badlogic.drop.Sprites.Skeleton;
import com.badlogic.drop.Sprites.Hero.State;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.drop.Tools.Heart;
import com.badlogic.drop.Tools.Item;
import com.badlogic.drop.Tools.Shield;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.drop.Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FlappyMap extends PlayScreen{
	public static final int SPEED = 5;
	private final int GRAVITY = -30;
	private final float DISTANCE = 15;
	private final int MAP_LENGTH = 200;
	private final int BOSS_BEGIN_POSITION = 40;
	private double timeCount;
	private double timeBegin;
	private double stageTime=10000;
	private double stageCr;
	
	
	private float lastBoundPosX;
	private boolean isBossAppeared = false;
	private TextureAtlas flyEngineAtlas;
	private Animation<TextureRegion> flyEngineAnimation;
	private Random random;
	
	private boolean isGameOver;
	public double monsterCd = 1000f; 
	public double itemCd = 3000f; 
	public double lastTimeSpawnMonster = 0;
	public double lastTimeSpawnItem = 0;
	public float[] posSpawns = {1,3,5,7,9,11,13,15,17,19};
	
	public FlappyMap (CuocChienSinhTon game) {
		super(game);
		//utils
		random = new Random();
		isGameOver = false;
		//

		atlas = new TextureAtlas("Hero.pack");

		// create camera
		camera = new OrthographicCamera();
		
		// load background
		backgroundTexture = new Texture("background/bg1.jpg");
		
		// viewport => responsive 
		gamePort = new FitViewport(CuocChienSinhTon.V_WIDTH/CuocChienSinhTon.PPM, CuocChienSinhTon.V_HEIGHT/CuocChienSinhTon.PPM,camera);		
		
		// load tilemap into world
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1/CuocChienSinhTon.PPM);
		
		// setup box2d
		world = new World(new Vector2(0,GRAVITY),true);
		b2dr = new Box2DDebugRenderer();
		new B2WorldCreator(world, map, this);
		
		// create hero
		region = atlas.findRegion("HeroIdle");
		prepareFlyEngineAnimation();

		player = new AnKhangHero(world,this);
		timeBegin = System.currentTimeMillis();
		
		//create heath bar
		healthbar = new HealthBar(this);

		// Create bounds
		createBounds();
		
		isBossAppeared=false;
		//b2dr.setDrawBodies(false);
		
		Collision.setup(this);
		//set up bullet manage		
		bulletManage = new BulletManage(world, this);
		
		stageCreator = new StageCreator(world,this);
		
	}

	private void loadMusic() {
		
	}
	private void spawnItems() {
		int type = MathUtils.random(2);
		float posY = MathUtils.random(17)+1;
		if(System.currentTimeMillis() - lastTimeSpawnItem > itemCd) {
			switch (type) {
			case 0:
				stageCreator.addItems("Shield", 30, posY,-1,10,0,-1);
				break;
			case 1:
				stageCreator.addItems("Strength", 30, posY,-1,10,0,-1);
				break;
			default:
				stageCreator.addItems("Heart", 30, posY,-1,10,0,-1);
				break;
			}
			lastTimeSpawnItem = System.currentTimeMillis();
			
		}
	}
	
	public void suffle() {
		for (int i = 0; i < posSpawns.length; i++) {
			int randomIndexToSwap = MathUtils.random(posSpawns.length-1);
			float temp = posSpawns[randomIndexToSwap];
			posSpawns[randomIndexToSwap] = posSpawns[i];
			posSpawns[i] = temp;
		}
	}
	
	public void spawnMonsters() {
		
		speed = (float) (10 + 2 * Math.min(stageCr, 7));
		monsterCd =  ((double)(DISTANCE/speed)*1000);
		float cnt = MathUtils.random(2)+1;
	
		if(System.currentTimeMillis() - lastTimeSpawnMonster >= monsterCd) {
			suffle();
			for(int i=0;i<cnt;i++) {
				int type = MathUtils.random(2);
				switch (type) {
				case 0:
					stageCreator.addMonster("FlyingEye", 35, posSpawns[i], 4, true, true, -1, speed, 0, -1);
					break;
				case 1:
					stageCreator.addMonster("DragonBallMonster1", 35, posSpawns[i], 4, true, true, -1, speed, 0, -1);
					break;
				case 2:
					stageCreator.addMonster("DragonBallMonster2", 35, posSpawns[i], 4, true, true, -1, speed, 0, -1);
					break;
				
				}
			}
			
			lastTimeSpawnMonster = System.currentTimeMillis();
		}
		
	}
	
	
	private void createBounds() {

		// Create bounds
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		lastBoundPosX = player.getX() + gamePort.getWorldWidth()*300;
		// Define top and bottom bounds
		Body body = world.createBody(bodyDef);

		EdgeShape topEdge = new EdgeShape();
		topEdge.set(new Vector2(player.getX(), gamePort.getWorldHeight()-1f), new Vector2(gamePort.getWorldWidth()*300, gamePort.getWorldHeight()));
		EdgeShape bottomEdge = new EdgeShape();
		bottomEdge.set(new Vector2(player.getX(), 1), new Vector2(gamePort.getWorldWidth()*300, 0));
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = topEdge;
		body.createFixture(fixtureDef);
		
		fixtureDef.shape = bottomEdge;
		fixtureDef.friction=0;
		Fixture botFixture =  body.createFixture(fixtureDef);

		botFixture.setSensor(false);
	}
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	// create fly engine
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
		
		if(pause) {
			pauseStage.draw();
			 Gdx.input.setInputProcessor(pauseStage);
			 return;
		}
		else {
			Gdx.input.setInputProcessor(stage);
		}
		
		timeCount = System.currentTimeMillis()-timeBegin;
		stageCr = (int) (timeCount/stageTime);
	
		camera.position.x = 17.5f;
		
		stageCreator.update(dt);
		
		if(stageCr < 7) spawnMonsters();
		else {
			if(!isBossAppeared) {
				stageCreator.addMonster("Boss2", 33, 10, 50, true, true);
				isBossAppeared = true;
			}
		}
		
		spawnItems();

		bulletManage.update(dt);
		
		//update healthbar
		healthbar.update(dt);
		
		player.currentState = State.STANDING;
	
		handleInput(dt);
		world.step(1/60f, 6, 2);
		
		renderer.setView(camera);
		camera.update();
		
		player.update(dt);
		
		//stage.draw();
		
	}
	public Body getBody() {
		return player.body;
	}
	private void heroJump() {
		getBody().setLinearVelocity(0, 17);

	}
	protected void handleInput(float dt) {
		if (Gdx.input.isTouched()||Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			heroJump();
		}
		
		
	}
	@Override
	public void render(float delta) {
		if(isPlayerDie) {
			
			dieScreen.render(delta);
			return;
		}
		
		if(pause) {
			Gdx.input.setInputProcessor(pauseStage);
			pauseStage.draw();
			 return;
		}
		
		
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
        game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(backgroundTexture, camera.position.x - gamePort.getWorldWidth() / 2,
				camera.position.y - gamePort.getWorldHeight() / 2, gamePort.getWorldWidth(),
				gamePort.getWorldHeight());
		game.batch.end();
		
		b2dr.render(world, camera.combined);
		game.batch.begin();
		player.draw(game.batch);
		game.batch.draw(flyEngineAnimation.getKeyFrame(player.getStateTime()), getBody().getPosition().x-player.getRegionWidth()/1.2f/CuocChienSinhTon.PPM, getBody().getPosition().y-player.getRegionHeight()/CuocChienSinhTon.PPM,flyEngineAnimation.getKeyFrame(0).getRegionWidth()*0.75f/CuocChienSinhTon.PPM,flyEngineAnimation.getKeyFrame(delta).getRegionHeight()*0.8f/CuocChienSinhTon.PPM);
	
		game.batch.end();
		
		worldContactListener = new WorldContactListener();
		world.setContactListener(worldContactListener);

		Gdx.input.setInputProcessor(stage);
		update(delta);
		stage.draw();
	}
	

	public float getSpeed() {
		return speed;
	}

	@Override
	public void handleDie() {
		isPlayerDie = true;
		timeBegin = System.currentTimeMillis();
		isBossAppeared = false;
		stageCreator.clearMonster();
		bulletManage.clearBullet();
		player.Health = player.HealthMax;
		player.shieldBegin = player.strengthBegin = -1;
		player.damage = 1;
		
	}
	public void dispose() {
		
		// TODO Auto-generated method stub
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		
		
	}
	}


