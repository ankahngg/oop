package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class HeroBullet2 extends Bullet{
	public HeroBullet2(World world,PlayScreen screen, float x, float y, int direction) {
		super(world,screen,x,y,direction);
		SetSpeed(20);

	}
	@Override
	public void prepareAnimation() {
		atlasBullet = new TextureAtlas("hero_bullet/thunder/thunder.atlas");
		bullet = new Animation<TextureRegion>(0.1f, atlasBullet.getRegions());
		setRegion(atlasBullet.getRegions().get(0));
	}
}
