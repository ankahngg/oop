package com.badlogic.drop.Scenes;

import com.badlogic.drop.Drop;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Hero;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HealthBar {
	private TextureAtlas atlasRanks;
	private TextureAtlas atlasHealthBar;
	private Texture HealthBar,Health;
	private PlayScreen screen;
	private TextureRegion currentRank;
	SpriteBatch batch;
	private Stage stage;
	private FitViewport viewport;
	private Hero player;
	
	public HealthBar(PlayScreen x) {
		//atlasHealthBar = new TextureAtlas("HealthBar/health.pack");
		atlasHealthBar = new TextureAtlas("HealthBar/HealthBar.pack");
		atlasRanks = new TextureAtlas("Ranks/packs/Ranks.pack");
		currentRank = atlasRanks.getRegions().get(1);
		HealthBar = new Texture("HealthBar/12.png");
		Health =  new Texture("HealthBar/11.png");
		
			
		//H = convertTextureRegionToTexture(Health);
		this.screen = x;
		batch = x.game.getBatch();
		
		viewport = new FitViewport(Drop.V_WIDTH/Drop.PPM, Drop.V_HEIGHT/Drop.PPM, new OrthographicCamera());
		
		stage = new Stage(viewport,x.game.batch);
		player = x.getPlayer();
	}
	
	public void update(float dt) {
		batch.begin();
		float x = screen.getCamera().position.x-screen.getGamePort().getWorldWidth()/2;
		float y = screen.getCamera().position.y+screen.getGamePort().getWorldHeight()/2;
	
		batch.draw(HealthBar, x+3,y-2,HealthBar.getWidth()/Drop.PPM+2,HealthBar.getHeight()/Drop.PPM, 0,0,HealthBar.getWidth(),HealthBar.getHeight(),false,false);
		batch.draw(currentRank, x, y-2, currentRank.getRegionWidth()/Drop.PPM/2,currentRank.getRegionHeight()/Drop.PPM/2);	
		
		float ratio = player.getHealth()/player.getHealthMax();
		float HealthX = x+3+(HealthBar.getWidth()-Health.getWidth())/2/Drop.PPM;
		float HealthY = y-2+(HealthBar.getHeight()-Health.getHeight())/2/Drop.PPM;
		// draw at HealthX and HealthY with size (lengOfHealthBar * Health/MaxHealth) x (Height) (!!!) and scale with PPM
		// and take portion of Texuture with size (lengOfHealthBar * Health/MaxHealth) x (Height)
		
		batch.draw(Health, HealthX,HealthY,(Health.getWidth()/Drop.PPM+2)*ratio,Health.getHeight()/Drop.PPM, 0,0,(int)(Health.getWidth()*ratio),Health.getHeight(),false,false);
		
		batch.end();
	}
	
}
