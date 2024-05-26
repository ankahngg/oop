package com.badlogic.drop.Screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.drop.CuocChienSinhTon;
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
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.drop.Tools.Heart;
import com.badlogic.drop.Tools.Item;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.drop.Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
	public final int SPEED = 10;

	public B2WorldCreator WorldCreator;
	public double teleCd=1000;
	public double lastTele=0;
	public boolean canJump = false;
	public boolean Hitting;
	public int stageNum = 8;
	public int stagePass = -1;
	public int stageCr;
	public int crCheckpoint = 0;
	public StageCreator StageCreator;
	public Rectangle tmp;
	public ArrayList<Boolean> firstEntry = new ArrayList<Boolean>();
	public ArrayList<Boolean> stageComplete = new ArrayList<Boolean>();
	
	private boolean isOnStage = false;
	
	public FirstMap(CuocChienSinhTon game) {
		// load map
		atlas = new TextureAtlas("Hero.pack");
		this.game =  game;
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
		StageCreator = new StageCreator(world, map, this);
		//create player
		player = new AnKhangHero(world,this);
		player.isHurtWhenCollide = true;
		//setup collision 
		Collision.setup(this);
		BulletManage.setup(world,this);
		b2dr.setDrawBodies(false);
		
		//create healthBar
		healthbar = new HealthBar(this);
		
		for(int i=0;i<=stageNum;i++) firstEntry.add(true);
		firstEntry.set(0, false);
		nextStage();
		
		speed =SPEED;
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	public void handleDie() {
		Vector2 pos = WorldCreator.checkpoints.get(0);
		for(Vector2 p : WorldCreator.checkpoints) {
			if((int) (p.x/35/CuocChienSinhTon.PPM) <= stagePass) pos = p;
		}
		
		StageCreator.clearMonster();
		
		stagePass = (int) (pos.x/CuocChienSinhTon.PPM/35)-1;
		nextStage();
		
		
		for(int i = stagePass+1;i<=stageNum;i++) firstEntry.set(i,true);
	
		player.setHealth(player.getHealthMax());
		player.body.setTransform(new Vector2(pos.x/CuocChienSinhTon.PPM,pos.y/CuocChienSinhTon.PPM), 0);
		
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
		
//		if(Gdx.input.isKeyJustPressed(Keys.J)) {
//			System.out.println("lol");
//			player.body.applyLinearImpulse(new Vector2(30,0), player.getBody().getWorldCenter(),true);
//		}
//		if(player.isAttacking) {
//			if(player.isFlipX()) player.body.setLinearVelocity( new Vector2(-2,vel.y));
//			else player.body.setLinearVelocity( new Vector2(2,vel.y));
//			return;
//		}
		if(Gdx.input.isKeyJustPressed(Keys.P) && Gdx.input.isKeyPressed(Keys.ALT_LEFT)) {
			// +1 vào health
			stageCr = 8;
			stagePass = 7;
			player.body.setTransform(new Vector2(35*stageCr+2,3) , 0);
			
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
	
	public void monsterUpdate(float dt) {

		for(Boss x : StageCreator.bosses) {
			if(x!=null) x.update(dt);
		}
		
		for(Boss x : StageCreator.bossesRemove) {
			StageCreator.bosses.remove(x);
		}
		StageCreator.bossesRemove.clear();
		
		for(Item x : StageCreator.items) {
			if(x!=null)
			x.update(dt);
		}
		
		if(!StageCreator.itemsRemove.isEmpty()) {
			for(Item x : StageCreator.itemsRemove) {
				if(x!=null)
				world.destroyBody(x.b2body);
				StageCreator.items.remove(x);
			}
			StageCreator.itemsRemove.clear();
		}
		
		for(Monster x : StageCreator.monsters) {
			if(x!=null) x.update(dt);
		}
		for(Monster x : StageCreator.monstersRemove) {
			StageCreator.monsters.remove(x);
		}
		StageCreator.monstersRemove.clear();
		
	}
	
	// method that be called every 1/60s
	public void update(float dt) {
		
		BulletManage.update(dt);
		
		handleInput(dt);
		player.update(dt);
		
		monsterUpdate(dt);
		
		Collision.update(dt);
		healthbar.update(dt);

		world.step(1/60f, 6, 2);
		//handle camera out of bound
		
		stageCr = (int) ((player.body.getPosition().x-(player.getRegionWidth()/2-10)/CuocChienSinhTon.PPM)/stageLength);
		
		camera.position.x = stageCr * stageLength + (float) stageLength/2;
		
		//camera.position.x = player.body.getPosition().x;
		if(firstEntry.get(stageCr)) {
			firstEntry.set(stageCr, false);
			StageCreator.Creator(stageCr);
			closeStage();
		}
		
		if(StageCreator.isStageClear()) nextStage();
		renderer.setView(camera);
		camera.update();
		
	}

	@Override
	public void render(float delta) {
		
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
		
		
		

	}

	

	

}
