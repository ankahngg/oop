package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Region;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

abstract public class Bullet extends Sprite{
	public final float SPEED = 10;
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
	public float scaleX=1;
	public float scaleY=1;
	public int direction;
	public double preTime;
	public float speed=10;
	public double lifeTime = 1;
	public double tt = 0;
	public float posX;
	public float posY;
	public float angle = 0f;
	
	public TextureRegion region;
	public boolean tracing = false;
	public boolean isRemoved = false;
	
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
		
		if(bulletDef != null) {
			bulletDef.setUserData(this);
			Collision.setCategoryFilter(bulletDef, Collision.HEROBULLET_BITS,null);
		}
	}
	
	abstract public void prepareAnimation() ;
	
	public void update(float dt) {

		stateTime += dt;
		
		if(lifeTime != -1) {
			if(stateTime > lifeTime) remove();
		}
		else {
			if((b2body.getPosition().x>screen.getCamera().position.x+screen.getCamera().viewportWidth/2)
					||(b2body.getPosition().x<screen.getCamera().position.x-screen.getCamera().viewportWidth/2)) {
				remove();
			}			
		}
		
		if(!isRemoved) {
			
			if(stateTime > bullet.getAnimationDuration()) 
				region = bullet.getKeyFrame( bullet.getAnimationDuration());
			else 
				region = bullet.getKeyFrame(stateTime,true);
			if(!region.isFlipX()) {
				if(direction == -1) region.flip(true, false);
			}
			setRegion(region);
			setBounds(b2body.getPosition().x-SpriteWidth/CuocChienSinhTon.PPM/2*scaleX,
					b2body.getPosition().y-SpriteHeight/CuocChienSinhTon.PPM/2*scaleY,
					getRegionWidth()/CuocChienSinhTon.PPM,
					getRegionHeight()/CuocChienSinhTon.PPM);
			
			screen.game.getBatch().begin();
			this.draw(screen.game.getBatch());
			screen.game.getBatch().end();
			
			if(bulletExternalCollison()) {
				screen.getPlayer().handleHurt(null);
			} 
			Movement();									
		}
	}
	
	public boolean bulletExternalCollison() {
		return false;
	}
	
	public float getAngle(float x1,float y1, float x2, float y2) {
		float yWidth = Math.abs(y1-y2);
		float xWidth = Math.abs(x1-x2);
		return (float) Math.asin(yWidth/xWidth);
	}
	
	public void setUp(float speedd, int directionn, float anglee, float lifeTimee) {
		speed = speedd;
		direction = directionn;
		angle = anglee;
		lifeTime = lifeTimee;
		
	}
	
	public void remove() {
		isRemoved = true;
		BulletManage.removeBullet(this);
	}
	
	public void Movement() {
		double sin = Math.sin(Math.toRadians(angle));
		double cos =  Math.cos(Math.toRadians(angle));
		float vecX = (float) (speed*cos*direction);
		float vecY = (float) (speed*sin*direction); 
		b2body.setLinearVelocity(new Vector2(vecX,vecY));
	}
	
	public void onHit() {
		remove();
	}

	public void defineBullet(float x,float y) {
		CircleShape shape = new CircleShape();
		 bdef.position.set(x,y);
		 bdef.type = BodyDef.BodyType.DynamicBody;
		 bdef.gravityScale = 0;
		 b2body = world.createBody(bdef);
		 shape.setRadius(getRegionHeight()/CuocChienSinhTon.PPM/2*scaleX);
		 fdef.shape = shape;
		 fdef.isSensor = true;
		 bulletDef = b2body.createFixture(fdef);
	}
	
	
}