package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class HeroBullet1 extends Bullet {
	public HeroBullet1(World world, PlayScreen screen, float x, float y, int direction) {
		super(world, screen, x, y, direction);
		SetSpeed(20);
		bulletDef.setUserData(this);
		Collision.setCategoryFilter(bulletDef, Collision.HEROBULLET_BITS,null);
		System.out.println("new");
	}

	@Override
	public void prepareAnimation() {
		atlasBullet = new TextureAtlas("hero_bullet/bullet1.pack");
		bullet = new Animation<TextureRegion>(0.15f, atlasBullet.getRegions());
		setRegion(atlasBullet.getRegions().get(0));
	}
}
