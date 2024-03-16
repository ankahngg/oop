package com.badlogic.drop.Screens;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Scenes.Hud;
import com.badlogic.drop.Sprites.Hero;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
	
	private Drop game;
	Texture texture;
	OrthographicCamera camera;
	Viewport gamePort;
	private Hud hud;
	
	// tilemap
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//Box2d
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private Hero player;
	
	
	public PlayScreen(Drop game) {
		this.game = game;
		camera = new OrthographicCamera();
		//gamePort = new ScreenViewport(camera);
		texture = new Texture("background.png");
		gamePort = new FitViewport(Drop.V_WIDTH/Drop.PPM, Drop.V_HEIGHT/Drop.PPM,camera);	
		//gamePort = new ScreenViewport(camera);	
		hud = new Hud(game.batch);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map2.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1/Drop.PPM);
		camera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
		
		
		world = new World(new Vector2(0,-10),true);
		b2dr = new Box2DDebugRenderer();
		
		new B2WorldCreator(world, map);
		
		player = new Hero(world);
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	private void handleInput(float dt) {
		// TODO Auto-generated method stub
		// press A
		if(Gdx.input.isKeyPressed(29)) {
			//camera.position.x -= 100*dt;
			player.b2body.applyLinearImpulse(new Vector2(-0.1f,0), player.b2body.getWorldCenter(), true);
		}
		//press D
		if(Gdx.input.isKeyPressed(32)) {
			//camera.position.x += 100*dt;
			player.b2body.applyLinearImpulse(new Vector2(0.1f,0), player.b2body.getWorldCenter(), true);
			
		}
		//press W
		if(Gdx.input.isKeyPressed(51)) {
			//camera.position.y += 100*dt;
			player.b2body.applyLinearImpulse(new Vector2(0,0.4f), player.b2body.getWorldCenter(), true);
		}
		//press S
		//if(Gdx.input.isKeyPressed(47)) camera.position.y -= 100*dt;
		
	}
	
	public void update(float dt) {
		
		handleInput(dt);
		world.step(1/60f, 6, 2);
		camera.position.x = player.b2body.getPosition().x;
		
		camera.update();
		renderer.setView(camera);
		
	}



	@Override
	public void render(float delta) {
		update(delta);
		
		ScreenUtils.clear(0, 0, 0.2f, 1);
        game.batch.setProjectionMatrix(gamePort.getCamera().combined);

		game.batch.begin();
		game.batch.draw(texture, gamePort.getCamera().position.x - gamePort.getWorldWidth() / 2,
				gamePort.getCamera().position.y - gamePort.getWorldHeight() / 2, gamePort.getWorldWidth(),
				gamePort.getWorldHeight());
		game.batch.end();
		//game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		//hud.stage.draw();
		
		renderer.render();
		
		b2dr.render(world, camera.combined);
		
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//System.out.print(width+" "+height+"      ");
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

}
