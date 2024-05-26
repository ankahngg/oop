package ui;

import java.nio.file.spi.FileSystemProvider;

import com.badlogic.drop.Screens.Menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import gamestates.Gamestate;
import utilz.LoadSave;
import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
public class MenuButton {
    public static final int B_WIDTH_DEFAULT = 140;
    public static final int B_HEIGHT_DEFAULT = 56;
    public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Menu.SCALE);
    public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Menu.SCALE);
    private int xPos, yPos, rowIndex,index;
    private int xOffsetCenter = B_WIDTH / 2;
    private int yOffsetCenter = B_HEIGHT / 2;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    private Gamestate state;
    private TextureRegion[] imgs;
    private CuocChienSinhTon game;

    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    public void loadImgs() {
        imgs = new TextureRegion[3];
        Texture temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = new TextureRegion(temp, i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(SpriteBatch batch) {

        batch.draw(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
  
    }

    public void update() {
        index = 0;
        if (mouseOver) 
        	index = 1;
        if (mousePressed) 
        	index = 2;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }
    public Gamestate getState() {
        return state;
    }
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

	public void onClick() {
		 // Add logic to handle button click event based on its state
        switch (state) {
            case PLAYING:
                break;
            case OPTIONS:
            	 System.out.println("lmao");
                break;
            case QUIT:
                // Quit the game
                break;
            default:
                break;
        }
	}
	public void dispose() {
    }
}
