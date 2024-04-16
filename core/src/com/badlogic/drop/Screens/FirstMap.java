package com.badlogic.drop.Screens;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Scenes.Hud;
import com.badlogic.drop.Sprites.Boss;
import com.badlogic.drop.Sprites.Hero;
import com.badlogic.drop.Sprites.AnKhangHero;
import com.badlogic.drop.Sprites.Middle;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.drop.Tools.WorldContactListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FirstMap extends PlayScreen {
	public final int speed = 10;

	
	public Boss getBoss() {
		return boss;
	}
	
	public void setBoss(Boss boss) {
		this.boss = boss;
	}
	
	
	public FirstMap(Drop game) {
		// load map
		atlas = new TextureAtlas("Hero.pack");
		this.game =  game;
		
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
		world = new World(new Vector2(0,-50),true);
		b2dr = new Box2DDebugRenderer();
		new B2WorldCreator(world, map, this);
		
		// create hero
		region = atlas.findRegion("HeroIdle");
		
		//create boss
		boss = new Boss(world, this);
		player = new AnKhangHero(world,this);
		
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	// method that be called every 1/60s
	public void update(float dt) {
		handleInput(dt);
		player.update(dt);
		boss.update(dt);
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
	
	protected void handleInput(float dt) {
		Vector2 vel = player.body.getLinearVelocity();
		boolean stop = true;
		
		if(player.isAttacking) {
			player.body.setLinearVelocity( new Vector2(0,vel.y));
			return;
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
			player.body.applyLinearImpulse(new Vector2(0,30), getBody().getWorldCenter(),true);
			stop = false;
		}
		
		if(stop) player.body.setLinearVelocity( new Vector2(0,vel.y));
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
		boss.draw(game.batch);
		game.batch.end();
		
		worldContactListener = new WorldContactListener();
		world.setContactListener(worldContactListener);

		update(delta);
		// TODO Auto-generated method stub

	}

	

	

}
