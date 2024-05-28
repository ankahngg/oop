package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BossBullet1 extends Bullet{
	private float playerY;
	private float playerH;
	private float bulletY;
	private float bulletH;
	private boolean isPlayerHurt = false;

	public BossBullet1(World world, PlayScreen screen, float x, float y, int direction) {
		super(world, screen, x, y, direction);
		speed = 15;
		
	}

	@Override
	public void prepareAnimation() {
		atlasBullet = new TextureAtlas("Boss/Bullet/BossBullet1_1.pack");
		bullet = new Animation<TextureRegion>(0.3f, atlasBullet.getRegions());
		setRegion(atlasBullet.getRegions().get(0));
		scaleX = 12;
		scaleY=8;
	}
	
	public boolean bulletExternalCollison() {
		 playerY = screen.getPlayer().body.getPosition().y;
		 playerH = screen.getPlayer().getRegionHeight()/CuocChienSinhTon.PPM;
		 bulletY = b2body.getPosition().y;
		 bulletH = atlasBullet.getRegions().get(2).getRegionHeight()/CuocChienSinhTon.PPM*scaleY;
		if(bullet.getKeyFrameIndex(stateTime) == 2 && 
		(playerY+playerH/2>bulletY-bulletH/2 && playerY-playerH/2<bulletY+bulletH/2) && !isPlayerHurt) {			
			isPlayerHurt = true;
			return true;
		}
		return false; 
	}
}
