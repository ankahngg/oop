package com.badlogic.drop.Screens;

import com.badlogic.drop.CuocChienSinhTon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import gamestates.Gamestate;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends InputAdapter implements Screen {
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 24;
    public final static int TILES_IN_HEIGHT = 10;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private MenuButton[] buttons = new MenuButton[3];
    private Texture backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;
    private CuocChienSinhTon game;
	private boolean inGame;

    public Menu(CuocChienSinhTon game) {
    	Gdx.input.setInputProcessor(this);
        this.game = game;
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundImg = new Texture(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * SCALE);
        menuX = GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(GAME_WIDTH / 2, (int) (220 * SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(GAME_WIDTH / 2, (int) (150 * SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(GAME_WIDTH / 2, (int) (75 * SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        SpriteBatch batch = game.getBatch();
        batch.begin();
        if (!inGame) { 
            batch.draw(backgroundImg, menuX, menuY, menuWidth, menuHeight);
            for (MenuButton mb : buttons) {
                mb.draw(batch);
            }
        }
        batch.end();
    }

    private boolean isIn(Vector2 touchPoint, MenuButton mb) {
        Rectangle bounds = mb.getBounds();
        return bounds.contains(touchPoint.x, touchPoint.y);
    }
    public void resetMenu() {
        inGame = false;
    }
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	  @Override
	    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	        Vector2 touchPoint = new Vector2(screenX, Gdx.graphics.getHeight() - screenY); // Invert y-coordinate
	        if (!inGame) {
	        	
	            for (MenuButton mb : buttons) {
	                if (isIn(touchPoint, mb)) {
	                    mb.onClick(); 
	                    
	                    if (mb.getState() == Gamestate.PLAYING) {
	                        game.setScreen(new FirstMap(game));
	                        inGame = true; 
	                    }
	                    
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
}
