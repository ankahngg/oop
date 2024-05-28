package com.badlogic.drop.Scenes;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Boss;
import com.badlogic.drop.Sprites.Boss1;
import com.badlogic.drop.Sprites.Hero;
import com.badlogic.drop.Tools.StageCreator;
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
	private PlayScreen screen;
	private TextureRegion currentRank;
	SpriteBatch batch;
	private Stage stage;
	private FitViewport viewport;
	private Hero player;
	private Boss boss;
	private Label HeroHealthLabel;
	private Label BossHealthLabel;
	
	public HealthBar(PlayScreen x) {
		atlasRanks = new TextureAtlas("Ranks/packs/Ranks.pack");
		currentRank = atlasRanks.getRegions().get(1);
		HeroHealthBar = new Texture("HealthBar/bg.png");
		HeroHealth =  new Texture("HealthBar/red.png");
		
		//get batch and player
		this.screen = x;
		batch = x.game.getBatch();
		player = x.getPlayer();
		
		
		
		//set up hud
//		viewport = new FitViewport(CuocChienSinhTon.V_WIDTH, CuocChienSinhTon.V_HEIGHT, new OrthographicCamera());
//		stage = new Stage(viewport,x.game.getBatch());
//		HeroHealthLabel = new Label(String.format("%d/%d",player.getHealth(),player.getHealthMax()),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
//		HeroHealthLabel.setPosition(3*CuocChienSinhTon.PPM, CuocChienSinhTon.V_HEIGHT-1.5f*CuocChienSinhTon.PPM+3);
//		
//		BossHealthLabel = new Label(String.format("%d/%d",boss.getHealth(),boss.getHealthMax()),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
//		BossHealthLabel.setPosition(20*CuocChienSinhTon.PPM, CuocChienSinhTon.V_HEIGHT-1.5f*CuocChienSinhTon.PPM+3);
//		
//		stage.addActor(HeroHealthLabel);
//		stage.addActor(BossHealthLabel);
	}
	
	public void update(float dt) {
		// draw Hero HeroHeroHealthBar
		
		
		
		batch.begin();
		float x = screen.getCamera().position.x-screen.getGamePort().getWorldWidth()/2;
		float y = screen.getCamera().position.y+screen.getGamePort().getWorldHeight()/2;
	
		batch.draw(HeroHealthBar, x+3,y-2,HeroHealthBar.getWidth()/CuocChienSinhTon.PPM/2,HeroHealthBar.getHeight()/CuocChienSinhTon.PPM/2, 0,0,HeroHealthBar.getWidth(),HeroHealthBar.getHeight(),false,false);
		
		float ratio = 1.0f*player.getHealth()/player.getHealthMax();
		float HealthX = x+3+(HeroHealthBar.getWidth()-HeroHealth.getWidth())/2/CuocChienSinhTon.PPM;
		float HealthY = y-2+(HeroHealthBar.getHeight()-HeroHealth.getHeight())/2/CuocChienSinhTon.PPM;
		// draw at HeroHealthX and HeroHealthY with size (lengOfHeroHeroHealthBar * HeroHealth/MaxHeroHealth) x (Height) (!!!) and scale with PPM
		// and take portion of Texuture with size (lengOfHeroHeroHealthBar * HeroHealth/MaxHeroHealth) x (Height)
		
		batch.draw(HeroHealth, HealthX,HealthY,
			(HeroHealth.getWidth()/CuocChienSinhTon.PPM/2)*ratio,HeroHealth.getHeight()/CuocChienSinhTon.PPM/2, 
			0,0,(int)(HeroHealth.getWidth()*ratio),HeroHealth.getHeight(),
			false,false);
		batch.end();
		

	}
	
}
