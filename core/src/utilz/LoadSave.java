package utilz;

import com.badlogic.drop.Screens.Menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class LoadSave implements Disposable {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String MENU_BUTTONS = "asset/button_atlas.png";
    public static final String MENU_BACKGROUND = "asset/menu_background.png";
    public static final String MENU_BUTTONS_HOVER="asset/";

    private static TextureAtlas textureAtlas;

    public static Texture GetSpriteAtlas(String fileName) {
        return new Texture(Gdx.files.internal(fileName));
    }

    public static int[][] GetLevelData() {
        int[][] lvlData = new int[Menu.TILES_IN_WIDTH][Menu.TILES_IN_HEIGHT];
        Pixmap pixmap = new Pixmap(Gdx.files.internal(LEVEL_ONE_DATA));

        for (int j = 0; j < pixmap.getHeight(); j++) {
            for (int i = 0; i < pixmap.getWidth(); i++) {
                int value = (pixmap.getPixel(i, j) >> 24) & 0xff; 
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        }
        pixmap.dispose();
        return lvlData;
    }

    @Override
    public void dispose() {
        if (textureAtlas != null) {
            textureAtlas.dispose();
        }
    }
}
