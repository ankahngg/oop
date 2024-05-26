package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class EnergyBall extends Bullet{
	public EnergyBall(World world, PlayScreen screen,float x,float y, int direction) {
		super(world, screen, x, y, direction);
		bulletDef.setUserData(this);
		Collision.setCategoryFilter(bulletDef, Collision.HEROBULLET_BITS, null);
	}
	public EnergyBall(World world, PlayScreen screen,float x,float y, int direction,float radious) {
		super();
		this.world = world;
		this.screen = screen;
		this.posX = x;
		this.posY = y;
		
		this.direction = direction;
		prepareAnimation();
		SpriteHeight = getRegionHeight();
		SpriteWidth = getRegionWidth();	
		defineBullet(x, y, radious);
		
		bulletDef.setUserData(this);
		Collision.setCategoryFilter(bulletDef, Collision.HEROBULLET_BITS, null);
	}
public EnergyBall(World world, PlayScreen screen, float x, float y, float dx,float dy) {
		
		super(world, screen, x, y);
	    SetSpeed(dx, dy);
		speed = 15;
		bulletDef.setUserData(this);
		Collision.setCategoryFilter(bulletDef, Collision.MONSTERBULLET_BITS,null);
	}

	@Override
	public void prepareAnimation() {
		atlasBullet = new TextureAtlas("Hero2/packs/energy-ball.pack");
		bullet = new Animation<TextureRegion>(0.15f, atlasBullet.getRegions());
		setRegion(atlasBullet.getRegions().get(0));
	}

	public void defineBullet(float x,float y,float radious) {
		CircleShape shape = new CircleShape();
		 bdef.position.set(x,y);
		 bdef.type = BodyDef.BodyType.DynamicBody;
		 bdef.gravityScale = 0;
		 b2body = world.createBody(bdef);
		 shape.setRadius(radious);
		 fdef.shape = shape;
		 fdef.isSensor = true;
		 bulletDef = b2body.createFixture(fdef);
	}
}
