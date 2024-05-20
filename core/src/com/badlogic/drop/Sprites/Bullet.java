package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

abstract public class Bullet extends Sprite{
			
	public World world;
	public PlayScreen screen;
	public boolean isActive = true;
	BodyDef bdef = new BodyDef();
	FixtureDef fdef = new FixtureDef();
	public Body b2body;
	public float stateTime=0;
	
	public TextureAtlas atlasBullet;
	public Animation<TextureRegion> bullet;
	public Fixture bulletDef;
	public float SpriteHeight;
	public float SpriteWidth;
	public int direction;
	public double preTime;
	public double lifeTime = 1000;
	public double tt = 0;
	public float posX;
	public float posY;
	public boolean isLaunch=false;
	
	public Bullet(World world,PlayScreen screen,float x, float y,int direction) {
		this.world = world;
		this.screen = screen;
		this.posX = x;
		this.posY = y;
		
		this.direction = direction;
		prepareAnimation();
		SpriteHeight = getRegionHeight();
		SpriteWidth = getRegionWidth();
		defineBullet(x,y);
		
	}
	
	abstract public void prepareAnimation() ;
	
	public void update(float dt) {
//		tt += dt;
//		if(tt >= lifeTime) this.getTexture().dispose();
		stateTime += dt;
		if(bullet.isAnimationFinished(stateTime)) remove();
		else {
			setRegion(bullet.getKeyFrame(stateTime,false));
			setBounds(b2body.getPosition().x-SpriteWidth/CuocChienSinhTon.PPM/2,
					b2body.getPosition().y-SpriteHeight/CuocChienSinhTon.PPM/2,
					getRegionWidth()/CuocChienSinhTon.PPM,
					getRegionHeight()/CuocChienSinhTon.PPM);
			Movement();
			screen.game.getBatch().begin();
			this.draw(screen.game.getBatch());
			screen.game.getBatch().end();									
		}
	}
	
	
	
	public void remove() {
		BulletManage.markRemoved(this);;
	}
	
	public void Movement() {
		 b2body.setLinearVelocity(new Vector2(10*direction,0));
	}
	
	public void onHit() {
//		this.b2body.destroyFixture(bulletDef);
		remove();
	}

	public void defineBullet(float x,float y) {
		CircleShape shape = new CircleShape();
		 bdef.position.set(x,y);
		 bdef.type = BodyDef.BodyType.DynamicBody;
		 bdef.gravityScale = 0;
		 b2body = world.createBody(bdef);
		 shape.setRadius(getRegionHeight()/CuocChienSinhTon.PPM/2);
		 fdef.shape = shape;
		 fdef.isSensor = true;
		 bulletDef = b2body.createFixture(fdef);
	}

	
	
}
