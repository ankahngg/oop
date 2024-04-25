package com.badlogic.drop.Screens;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LabScreen implements Screen {
	
	private CuocChienSinhTon game;
	private OrthographicCamera camera;        
      
	//private float rotationSpeed;     
	private FitViewport gamePort;
	private Texture tex;
	private float rotationSpeed = 0.5f;
	private float PPM = 16f;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private TextureAtlas atlas,heroAtlas;
	private TextureRegion region;
	private TextureRegion frames[];
	public Animation<TextureRegion> runningAnimation;
	float stateTime;
	
	
	public LabScreen(CuocChienSinhTon game) {
		
		this.game = game;
		camera = new OrthographicCamera();
		//camera.position.set(new Vector3(100,100,0));
		gamePort = new FitViewport(500/PPM,320/PPM,camera);	
		
		map = new TmxMapLoader().load("map2.tmx");
		renderer = new OrthogonalTiledMapRenderer(map,1/PPM);
		tex = new Texture("background.png");
		
		heroAtlas = new TextureAtlas("PNG/Sprites/hero/ll");
		atlas = new TextureAtlas("Hero.pack");
		region = atlas.findRegion("HeroIdle");
		//region = heroAtlas.findRegion("hero-attack-1");
		frames = new TextureRegion[5];
		for (int i = 1; i <= 5; i++) {
            frames[i-1] = heroAtlas.findRegion(String.format("hero-attack-%d",i));
        }
		
		runningAnimation = new Animation<TextureRegion>(0.05f, frames);
		
		//atlas = new TextureAtlas("Hero.pack");
		//region = atlas.findRegion("HeroIdle");
		
		//splitter(region,1,3);
		
		// TODO Auto-generated constructor stub
	}
	@Override
	public void render(float delta) {
		handleInput();
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		camera.update();                                                         
		game.batch.setProjectionMatrix(camera.combined);   

		
		game.batch.begin();     
		game.batch.draw(tex,camera.position.x - gamePort.getWorldWidth() / 2,
				camera.position.y - gamePort.getWorldHeight() / 2, gamePort.getWorldWidth(),
				gamePort.getWorldHeight());
		game.batch.end();        
		
		renderer.setView(camera);
		renderer.render();
		
		stateTime += Gdx.graphics.getDeltaTime(); 

		TextureRegion currentFrame = runningAnimation.getKeyFrame(stateTime, true);
		
		game.batch.begin();  
		//game.batch.draw(frames[0],5,2,frames[0].getRegionWidth()/PPM,frames[0].getRegionHeight()/PPM);
		//game.batch.draw(region,10,2,region.getRegionWidth()/PPM,region.getRegionHeight()/PPM);
		game.batch.draw(currentFrame,3,2,currentFrame.getRegionWidth()/PPM,currentFrame.getRegionHeight()/PPM);
		game.batch.end();   
		
		
		
	}

	private void handleInput() {
		// TODO Auto-generated method stub
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.rotate(-rotationSpeed , 0, 0, 1);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			camera.rotate(rotationSpeed, 0, 0, 1);
		}
		
	}
	@Override
	public void show() {
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
		
	}
}
