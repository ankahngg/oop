package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class HealthBarMonster extends Sprite{
	Texture HealBar;
	Texture Heal;
	private SpriteBatch batch;
	public HealthBarMonster(PlayScreen x) {
		HealBar = new Texture("HealthBar/bg.png");
		Heal = new Texture("HealthBar/red.png");
		batch = x.game.getBatch();
	}
	// y = ceil cordinaton
	public void update(float health,float healthMax,float x, float y) {
		float HealthBarWidth = HealBar.getWidth()/CuocChienSinhTon.PPM/4;
		float HealthBarHeight = HealBar.getHeight()/CuocChienSinhTon.PPM/4;
		
		float HealthWidth = Heal.getWidth()/CuocChienSinhTon.PPM/4;
		float HealthHeight = Heal.getHeight()/CuocChienSinhTon.PPM/4;
		
		float HealthBarX = x-HealthWidth/2;
		float HealthBarY = y+1;
		float HealthX = HealthBarX+(HealthBarWidth-HealthWidth);
		float HealthY = HealthBarY + (HealthBarHeight-HealthHeight);
		float ratio = 1.0f*health/healthMax;
		
	
		batch.begin();
		batch.draw(HealBar, HealthBarX,HealthBarY,HealthBarWidth,HealthBarHeight);
		batch.end();
		
		
		batch.begin();
		batch.draw(Heal, HealthX,HealthY,HealthWidth*ratio,HealthHeight, 0,0,(int)(Heal.getWidth()*ratio),(int)Heal.getHeight(),false,false);
		
		batch.end();
	}
}
