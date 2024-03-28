package com.badlogic.drop.Screens;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Scenes.Hud;
import com.badlogic.drop.Sprites.Hero;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
	OrthographicCamera camera,camera2;
	Viewport gamePort;
	
	// tilemap
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//Box2d
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private Hero player;
	
	private TextureAtlas atlas;
	TextureRegion region;
	//private AtlasRegion atlasRegion;
	
	public PlayScreen(Drop game) {
		atlas = new TextureAtlas("Hero.pack");
		this.game = game;
		camera = new OrthographicCamera();
		
		texture = new Texture("background.png");
		gamePort = new FitViewport(Drop.V_WIDTH/Drop.PPM, Drop.V_HEIGHT/Drop.PPM,camera);		
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("map2.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1/Drop.PPM);
		
		world = new World(new Vector2(0,-50),true);
		b2dr = new Box2DDebugRenderer();
		
		new B2WorldCreator(world, map);
//		
		player = new Hero(world,this);
		region = atlas.findRegion("HeroIdle");
		
		
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
	
	public void update(float dt) {
		player.update(dt);
		world.step(1/60f, 6, 2);
		//handle camera out of bound
		if(player.b2body.getPosition().x-gamePort.getWorldWidth()/2 < 0) 
			camera.position.x = gamePort.getWorldWidth()/2;
		else camera.position.x = player.b2body.getPosition().x;
		
		//player.setRegionX((int)player.b2body.getPosition().x);
		
		
		renderer.setView(camera);
		camera.update();
		
	}


	@Override
	public void render(float delta) {
		update(delta);
		
		
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		
        game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(texture, camera.position.x - gamePort.getWorldWidth() / 2,
				camera.position.y - gamePort.getWorldHeight() / 2, gamePort.getWorldWidth(),
				gamePort.getWorldHeight());
		game.batch.end();
				
		renderer.render();
		
		b2dr.render(world, camera.combined);
		game.batch.begin();
//		game.batch.draw(player.heroStand,
//				player.b2body.getPosition().x-player.heroStand.getRegionWidth()/2/Drop.PPM,
//				player.b2body.getPosition().y-player.heroStand.getRegionHeight()/2/Drop.PPM,
//				player.heroStand.getRegionWidth()/Drop.PPM,
//				player.heroStand.getRegionHeight()/Drop.PPM);
		player.draw(game.batch);
		game.batch.end();

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
