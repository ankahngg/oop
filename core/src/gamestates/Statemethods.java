package gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputProcessor;

public interface Statemethods extends InputProcessor {
    void update();
    void draw(SpriteBatch batch);
}
