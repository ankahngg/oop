package com.badlogic.drop.Scenes;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Boss;
import com.badlogic.drop.Sprites.Hero;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HealthBar {
	private TextureAtlas atlasRanks;
	private Texture HeroHealthBar,HeroHealth;
	private Texture BossHealthBar,BossHealth;
	private FirstMap screen;
	private TextureRegion currentRank;
	SpriteBatch batch;
	private Stage stage;
	private FitViewport viewport;
	private Hero player;
	private Boss boss;
	private Label HeroHealthLabel;
	private Label BossHealthLabel;
	
	public HealthBar(FirstMap x) {
		atlasRanks = new TextureAtlas("Ranks/packs/Ranks.pack");
		currentRank = atlasRanks.getRegions().get(1);
		HeroHealthBar = new Texture("HealthBar/HeroHealthBar1.png");
		HeroHealth =  new Texture("HealthBar/HeroHealthBar2.png");
		
		BossHealthBar = new Texture("HealthBar/BossHealthBar1.png");
		BossHealth =  new Texture("HealthBar/BossHealthBar2.png");
		
		
		//get batch and player
		this.screen = x;
		batch = x.game.getBatch();
		player = x.getPlayer();
		boss = x.getBoss();
		
		//set up hud
		viewport = new FitViewport(Drop.V_WIDTH, Drop.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport,x.game.getBatch());
		HeroHealthLabel = new Label(String.format("%d/%d",player.getHealth(),player.getHealthMax()),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		HeroHealthLabel.setPosition(3*Drop.PPM, Drop.V_HEIGHT-1.5f*Drop.PPM+3);
		
		BossHealthLabel = new Label(String.format("%d/%d",boss.getHealth(),boss.getHealthMax()),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		BossHealthLabel.setPosition(20*Drop.PPM, Drop.V_HEIGHT-1.5f*Drop.PPM+3);
		
		stage.addActor(HeroHealthLabel);
		stage.addActor(BossHealthLabel);
	}
	
	public void update(float dt) {
		// draw Hero HeroHeroHealthBar
		batch.begin();
		float x = screen.getCamera().position.x-screen.getGamePort().getWorldWidth()/2;
		float y = screen.getCamera().position.y+screen.getGamePort().getWorldHeight()/2;
	
		batch.draw(HeroHealthBar, x+3,y-2,HeroHealthBar.getWidth()/Drop.PPM,HeroHealthBar.getHeight()/Drop.PPM, 0,0,HeroHealthBar.getWidth(),HeroHealthBar.getHeight(),false,false);
		
		float ratio = 1.0f*player.getHealth()/player.getHealthMax();
		float HealthX = x+3+(HeroHealthBar.getWidth()-HeroHealth.getWidth())/2/Drop.PPM;
		float HealthY = y-2+(HeroHealthBar.getHeight()-HeroHealth.getHeight())/2/Drop.PPM;
		// draw at HeroHealthX and HeroHealthY with size (lengOfHeroHeroHealthBar * HeroHealth/MaxHeroHealth) x (Height) (!!!) and scale with PPM
		// and take portion of Texuture with size (lengOfHeroHeroHealthBar * HeroHealth/MaxHeroHealth) x (Height)
		
		batch.draw(HeroHealth, HealthX,HealthY,
			(HeroHealth.getWidth()/Drop.PPM)*ratio,HeroHealth.getHeight()/Drop.PPM, 
			0,0,(int)(HeroHealth.getWidth()*ratio),HeroHealth.getHeight(),
			false,false);
		batch.end();
		
		// draw Boss HeroHeroHealthBar
		if(screen.isBossSpawn) {
			batch.begin();
			x = screen.getCamera().position.x-screen.getGamePort().getWorldWidth()/2;
			y = screen.getCamera().position.y+screen.getGamePort().getWorldHeight()/2;
			batch.draw(BossHealthBar, x+20,y-2,BossHealthBar.getWidth()/Drop.PPM+5,BossHealthBar.getHeight()/Drop.PPM, 0,0,BossHealthBar.getWidth(),BossHealthBar.getHeight(),false,false);
			
			ratio = 1.0f*boss.getHealth()/boss.getHealthMax();
			HealthX = x+20+(BossHealthBar.getWidth()-BossHealth.getWidth())/2/Drop.PPM;
			HealthY = y-2+(BossHealthBar.getHeight()-BossHealth.getHeight())/2/Drop.PPM;
			
			batch.draw(BossHealth, HealthX,HealthY,
					(BossHealth.getWidth()/Drop.PPM+5)*ratio,BossHealth.getHeight()/Drop.PPM, 
					0,0,(int)(BossHealth.getWidth()*ratio),BossHealth.getHeight(),
					false,false);
			batch.end();
		}
		if(player.currentRank > 0) {
			batch.begin();
			batch.draw(currentRank, x, y-2, currentRank.getRegionWidth()/Drop.PPM/2,currentRank.getRegionHeight()/Drop.PPM/2);	
			batch.end();
		}
		// Draw hud
		HeroHealthLabel.setText(String.format("%d/%d",player.getHealth(),player.getHealthMax()));
		BossHealthLabel.setText(String.format("%d/%d",boss.getHealth(),boss.getHealthMax()));
		screen.game.getBatch().setProjectionMatrix(stage.getCamera().combined);
		stage.draw();
	}
	
}
