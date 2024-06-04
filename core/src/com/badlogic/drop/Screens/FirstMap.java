package com.badlogic.drop.Screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.MapUserData;
import com.badlogic.drop.Scenes.HealthBar;
import com.badlogic.drop.Sprites.AnKhangHero;
import com.badlogic.drop.Sprites.Boss;
import com.badlogic.drop.Sprites.Boss1;
import com.badlogic.drop.Sprites.Bullet;
import com.badlogic.drop.Sprites.BulletManage;
import com.badlogic.drop.Sprites.Collision;
import com.badlogic.drop.Sprites.EyeBullet;
import com.badlogic.drop.Sprites.FlyingEye;
import com.badlogic.drop.Sprites.HellBeast;
import com.badlogic.drop.Sprites.HungKing;
import com.badlogic.drop.Sprites.Monster;
import com.badlogic.drop.Sprites.Skeleton;
import com.badlogic.drop.Sprites.StageBound;
import com.badlogic.drop.Tools.AudioManagement;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.drop.Tools.Heart;
import com.badlogic.drop.Tools.Item;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.drop.Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FirstMap extends PlayScreen {
	public int stageLength = 35;
	public boolean isBossSpawn = true;
	public final int speed = 10;

	public B2WorldCreator WorldCreator;
	public double teleCd=1000;
	public double lastTele=0;
	public boolean canJump = true;
	public boolean Hitting;
	public int stageNum = 8;
	public int stagePass = -1;
	public int stageCr = 0;
	public int crCheckpoint = 0;
	public Rectangle tmp;
	public ArrayList<Boolean> firstEntry = new ArrayList<Boolean>();
	public ArrayList<Boolean> stageComplete = new ArrayList<Boolean>();
	
	private boolean isOnStage = false;
	
	public Music map1Music;
	
	public FirstMap(CuocChienSinhTon game) {
		super(game);
		// load map
		atlas = new TextureAtlas("Hero.pack");
		// create camera
		camera = new OrthographicCamera();
		
		// load background
		backgroundTexture = new Texture("bg1.png");
		
		// viewport => responsive 
		gamePort = new FitViewport(CuocChienSinhTon.V_WIDTH/CuocChienSinhTon.PPM, CuocChienSinhTon.V_HEIGHT/CuocChienSinhTon.PPM,camera);		
		
		// load tilemap into world
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map2.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1/CuocChienSinhTon.PPM);
		
		// setup box2d
		world = new World(new Vector2(0,-50),true);
		b2dr = new Box2DDebugRenderer();
		WorldCreator = new B2WorldCreator(world, map, this);
		
		Collision.setup(this);
		//set up bullet manage		
		bulletManage = new BulletManage(world,this);
		
		stageCreator = new StageCreator(world,this);
		//setup die screen
		dieScreen = new DieScreen(game,this);
		//create player
		player = new AnKhangHero(world,this);
		player.isHurtWhenCollide = true;
		
		b2dr.setDrawBodies(false);
		
		//create healthBar
		healthbar = new HealthBar(this);
		//
		for(int i=0;i<=8;i++) firstEntry.add(true);
		setUpProgress();
		map1Music = AudioManagement.manager.get(AudioManagement.map1Music,Music.class);
		map1Music.setLooping(true);
		map1Music.setVolume(0.1f);
		map1Music.play();
		AudioManagement.setLastMusic(map1Music);
	}
	
	public void setUpProgress() {
		stageCr = MapUserData.CrStage;
		stagePass = MapUserData.CrStagePass;
		Vector2 pos = WorldCreator.checkpoints.get(0);
		for(Vector2 p : WorldCreator.checkpoints) {
			if((int) (p.x/35/CuocChienSinhTon.PPM) <= stagePass) pos = p;
		}
		stagePass = (int) (pos.x/CuocChienSinhTon.PPM/35)-1;
		nextStage();
		
		for(int i = 0;i<=stagePass;i++) firstEntry.set(i,false);
		for(int i = stagePass+1;i<=stageNum;i++) firstEntry.set(i,true);
		player.body.setTransform(new Vector2(pos.x/CuocChienSinhTon.PPM,pos.y/CuocChienSinhTon.PPM), 0);
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	public void setDieScreen() {
		dispose();
		game.setScreen(new DieScreen(game, this));

	}
	public void handleDie() {
//		Vector2 pos = WorldCreator.checkpoints.get(0);
//		for(Vector2 p : WorldCreator.checkpoints) {
//			if((int) (p.x/35/CuocChienSinhTon.PPM) <= stagePass) pos = p;
//		}
//		
//		stageCreator.clearMonster();
//		
//		
//		stagePass = (int) (pos.x/CuocChienSinhTon.PPM/35)-1;
//		nextStage();
//		
//		
//		for(int i = stagePass+1;i<=stageNum;i++) firstEntry.set(i,true);
//	
//		player.setHealth(player.getHealthMax());
//		player.body.setTransform(new Vector2(pos.x/CuocChienSinhTon.PPM,pos.y/CuocChienSinhTon.PPM), 0);
	}
	public void closeStage() {
		isOnStage = true;
		WorldCreator.stageBounds.get(stagePass).body.setActive(true);
	}
	
	public void nextStage() {
		stagePass++;
		WorldCreator.stageBounds.get(stagePass).body.setActive(false);
	}
	
	protected void handleInput(float dt) {
		Vector2 vel = player.body.getLinearVelocity();
		boolean stop = true;
		
		if(player.isHurting) {
			if(player.hurtDirection == 0) {
				player.body.setLinearVelocity( new Vector2((float) (0*speed),0));
			}
			else player.body.setLinearVelocity( new Vector2((float) (0*speed),0));
			return;
		}
		if(player.isDieing) {
			player.body.setLinearVelocity( new Vector2(0,vel.y));
			return ;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.P) && Gdx.input.isKeyPressed(Keys.ALT_LEFT)) {
			// +1 vào health
			stageCr = 8;
			stagePass = 7;
			player.body.setTransform(new Vector2(35*stageCr+2,3) , 0);
			//player.damage = 10;
			
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			
			player.body.setLinearVelocity( new Vector2(-speed,vel.y));
			stop = false;
		}
		
		
		if(Gdx.input.isKeyPressed(Keys.D)) {
		
			stop = false;
			player.body.setLinearVelocity( new Vector2(speed,vel.y));
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.L) && System.currentTimeMillis()-lastTele > teleCd) {
			if(!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) {
				if(!player.isFlipX()) player.body.setTransform(new Vector2((int) (player.body.getPosition().x+5),(int)player.body.getPosition().y), 0);
				else player.body.setTransform(new Vector2((int) (player.body.getPosition().x-5),(int)player.body.getPosition().y), 0);
			}
			else if(Gdx.input.isKeyPressed(Keys.D)) player.body.setTransform(new Vector2((int) (player.body.getPosition().x+5),(int)player.body.getPosition().y), 0);
			else if(Gdx.input.isKeyPressed(Keys.A)) player.body.setTransform(new Vector2((int) (player.body.getPosition().x-5),(int)player.body.getPosition().y), 0);
			lastTele = System.currentTimeMillis();
		}
		
		
		if(Gdx.input.isKeyJustPressed(Keys.W) && canJump && vel.y == 0) {
			canJump = false;
			player.body.applyLinearImpulse(new Vector2(0,23), player.getBody().getWorldCenter(),true);
			stop = false;
		}
		
		if(stop) player.body.setLinearVelocity( new Vector2(0,vel.y));
	}
	
	// method that be called every 1/60s
	public void update(float dt) {
		
		world.step(1/60f, 6, 2);
		
		bulletManage.update(dt);
		
		handleInput(dt);
		player.update(dt);
		
		stageCreator.update(dt);
		Collision.update(dt);
		//handle camera out of bound
		
		stageCr = (int) ((player.body.getPosition().x-(player.HeroWidth/2-10)/CuocChienSinhTon.PPM)/stageLength);
		camera.position.x = stageCr * stageLength + (float) stageLength/2;
		
		//camera.position.x = player.body.getPosition().x;
		if(firstEntry.get(stageCr)) {
			firstEntry.set(stageCr, false);
			stageCreator.Creator(map,stageCr);
			closeStage();
		}
		
		if(stageCreator.isStageClear()) nextStage();
		renderer.setView(camera);
		camera.update();
		
		healthbar.update(dt);
		MapUserData.saveData(stageCr, stagePass);
	}

	@Override
	public void render(float delta) {
		if(pause) {
			map1Music.pause();
			pauseStage.draw();
			Gdx.input.setInputProcessor(pauseStage);
			 return;
		}
		
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
        game.batch.setProjectionMatrix(camera.combined);

		//Draw background
		game.batch.begin();
		game.batch.draw(backgroundTexture, camera.position.x - gamePort.getWorldWidth() / 2,
				camera.position.y - gamePort.getWorldHeight() / 2, gamePort.getWorldWidth(),
				gamePort.getWorldHeight());
		game.batch.end();
				
		// render tilemap
		renderer.render();
		
		// render box2d
		b2dr.render(world, camera.combined);

		// draw player and draw boss
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
		
		update(delta);
		
		// setup worldContactListener
		worldContactListener = new WorldContactListener();
		world.setContactListener(worldContactListener);
		
		//pauseStage.draw();
		Gdx.input.setInputProcessor(stage);
		stage.draw();
		

	}
	public void dispose() {
			// TODO Auto-generated method stub
			map1Music.dispose();
		}

	

	

}
