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
	
	public void update(float dt) {
//		tt += dt;
//		if(tt >= lifeTime) this.getTexture().dispose();
		stateTime += dt;
		if(stateTime > bullet.getAnimationDuration()*3) remove();
		if(!isDied) {
			region = bullet.getKeyFrame(stateTime,true);
			if(!region.isFlipX()) {
				if(direction == -1) region.flip(true, true);
			}
			setRegion(region);
			setBounds(b2body.getPosition().x-SpriteWidth/CuocChienSinhTon.PPM/2,
					b2body.getPosition().y-SpriteHeight/CuocChienSinhTon.PPM/2,
					getRegionWidth()/CuocChienSinhTon.PPM*scaleX,
					getRegionHeight()/CuocChienSinhTon.PPM*scaleY);
			Movement();
			screen.game.getBatch().begin();
			this.draw(screen.game.getBatch());
			screen.game.getBatch().end();									
		}
	}
}
