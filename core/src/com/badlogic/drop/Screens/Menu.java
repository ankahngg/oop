package com.badlogic.drop.Screens;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import gamestates.Gamestate;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends InputAdapter implements Screen {
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.2f;
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
    private OrthographicCamera camera;
    private Viewport viewport;

    public Menu(CuocChienSinhTon game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        loadButtons();
        loadBackground();
        Gdx.input.setInputProcessor(this);
    }
    public void update() {
    	for(MenuButton mb: buttons)
    		mb.update();
    }
    
    private void loadBackground() {
        backgroundImg = new Texture(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * SCALE);
        menuX = GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) ( -20f*SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(GAME_WIDTH / 2, (int) (160 * SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(GAME_WIDTH / 2, (int) (95 * SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(GAME_WIDTH / 2, (int) (30 * SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        SpriteBatch batch = game.getBatch();
        batch.begin();
        if (!inGame) {
            batch.draw(backgroundImg, menuX,menuY, menuWidth, menuHeight);
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 touchPoint = viewport.unproject(new Vector2(screenX, screenY));
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
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void show() {
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

    @Override
    public void dispose() {
        backgroundImg.dispose();
        for (MenuButton mb : buttons) {
            mb.dispose();
        }
    }
}
