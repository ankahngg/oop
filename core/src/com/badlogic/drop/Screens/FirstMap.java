package com.badlogic.drop.Screens;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Scenes.HealthBar;
import com.badlogic.drop.Sprites.AnKhangHero;
import com.badlogic.drop.Sprites.Boss;
import com.badlogic.drop.Sprites.Bullet;
import com.badlogic.drop.Sprites.Collision;
import com.badlogic.drop.Sprites.EyeBullet;
import com.badlogic.drop.Sprites.FlyingEye;
import com.badlogic.drop.Sprites.Skeleton;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.drop.Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FirstMap extends PlayScreen {
	public boolean isBossSpawn = true;
	public final int speed = 10;
	private Skeleton skeleton1;
	private FlyingEye Eye1;
	private Bullet bullet;
	
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
		new B2WorldCreator(world, map, this);
		
		//create monster
		skeleton1 = new Skeleton(world, this,30,2);
		Eye1 = new FlyingEye(world, this, 30, 5);
		//create boss
		boss = new Boss(world, this,35,2);

		//create player
		player = new AnKhangHero(world,this);
		//setup collision 
		Collision.setup(this);
		
		//create healthBar
		healthbar = new HealthBar(this);
		
		boss.b2body.setActive(false);
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	public void handleDie() {
		System.out.println("wtf");
		//game.setScreen(new FlappyMap(game));
		
		
	}
	
	protected void handleInput(float dt) {
		Vector2 vel = player.body.getLinearVelocity();
		boolean stop = true;
		
		if(player.isHurting) {
			if(player.hurtDirection == 0) {
				player.body.setLinearVelocity( new Vector2((float) (-1.5*speed),0));
			}
			else player.body.setLinearVelocity( new Vector2((float) (1.5*speed),0));
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
		if(player.isAttacking) {
			if(player.isFlipX()) player.body.setLinearVelocity( new Vector2(-2,vel.y));
			else player.body.setLinearVelocity( new Vector2(2,vel.y));
			return;
		}
		if(Gdx.input.isKeyJustPressed(Keys.P) && Gdx.input.isKeyPressed(Keys.ALT_LEFT)) {
			// +1 vào health
			skeleton1.onWallCollision();
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			player.body.setLinearVelocity( new Vector2(-speed,vel.y));
			stop = false;
		}
		
		if(Gdx.input.isKeyPressed(Keys.D)) {
			stop = false;
			player.body.setLinearVelocity( new Vector2(speed,vel.y));
		}
		
		if(Gdx.input.isKeyPressed(Keys.W) && vel.y == 0) {
			player.body.applyLinearImpulse(new Vector2(0,25), player.getBody().getWorldCenter(),true);
			stop = false;
		}
		
		if(stop) player.body.setLinearVelocity( new Vector2(0,vel.y));
	}
	
	// method that be called every 1/60s
	public void update(float dt) {
		handleInput(dt);
		player.update(dt);
		boss.update(dt);
		skeleton1.update(dt);
		Eye1.update(dt);
		
		if(player.isDie) handleDie();
		Collision.update(dt);
		healthbar.update(dt);

		world.step(1/60f, 6, 2);
		//handle camera out of bound
		if(player.body.getPosition().x-gamePort.getWorldWidth()/2 < 0) 
			camera.position.x = gamePort.getWorldWidth()/2;
		else 
			camera.position.x = player.body.getPosition().x;
		
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
