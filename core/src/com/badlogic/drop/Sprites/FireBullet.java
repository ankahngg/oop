package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class FireBullet extends Bullet {
	public FireBullet(World world, PlayScreen screen, float x, float y, int direction) {
		super(world, screen, x, y, direction);
		
		bulletDef.setUserData(this);
		Collision.setCategoryFilter(bulletDef, Collision.MONSTERBULLET_BITS,null);
	}


	@Override
	public void prepareAnimation() {
		atlasBullet = new TextureAtlas("Monster/HellBeast/FireBullet.pack");
		bullet = new Animation<TextureRegion>(0.15f, atlasBullet.getRegions());
		setRegion(atlasBullet.getRegions().get(0));
		speed = 20;
		Vector2 hero = screen.getPlayer().body.getPosition();
		
		angle = getAngle(posX, posY, hero.x, hero.y);
	}
}

