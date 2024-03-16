package Screens;

import com.badlogic.drop.Drop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	
	
	public PlayScreen(Drop game) {
		this.game = game;
		camera = new OrthographicCamera();
		//gamePort = new ScreenViewport(camera);
		texture = new Texture("bucket.png");
		//camera.setToOrtho(false, 2000, 500);
		gamePort = new FitViewport(Drop.V_WIDTH, Drop.V_HEIGHT,camera);	
		//gamePort = new ScreenViewport(camera);	
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(texture,0,0);
		game.batch.end();
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//System.out.print(width+" "+height+"      ");
		gamePort.update(width, height);
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
