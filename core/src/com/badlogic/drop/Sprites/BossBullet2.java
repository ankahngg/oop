package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class BossBullet2 extends Bullet{
	public BossBullet2(World world, PlayScreen screen, float x, float y, int direction) {
		super(world, screen, x, y, direction);
		speed = 20;
		
		bulletDef.setUserData(this);
		Collision.setCategoryFilter(bulletDef, Collision.MONSTERBULLET_BITS,null);
	}

	@Override
	public void prepareAnimation() {
		atlasBullet = new TextureAtlas("Boss/Bullet/BossBullet2.pack");
		bullet = new Animation<TextureRegion>(0.1f, atlasBullet.getRegions());
		setRegion(atlasBullet.getRegions().get(0));
		scaleX = scaleY = 2;
	}
}
