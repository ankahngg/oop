package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class EnergyBall extends Bullet{
	public EnergyBall(World world, PlayScreen screen,float x,float y, int direction) {
		super(world, screen, x, y, direction);
		bulletDef.setUserData(this);
		Collision.setCategoryFilter(bulletDef, Collision.HEROBULLET_BITS, null);
	}

	@Override
	public void prepareAnimation() {
		atlasBullet = new TextureAtlas("Hero2/packs/energy-ball.atlas");
		bullet = new Animation<TextureRegion>(0.15f, atlasBullet.getRegions());
		setRegion(atlasBullet.getRegions().get(0));
	}
}
