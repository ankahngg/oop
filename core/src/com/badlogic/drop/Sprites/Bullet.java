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
	public int scaleX;
	public int scaleY;
	public int direction;
	public double preTime;
	public int speed=10;
	public double lifeTime = 1000;
	public double tt = 0;
	public float posX;
	public float posY;
	public boolean isLaunch=false;
	public boolean isInfinited=false;
	
	public boolean isDied = false;
	public TextureRegion region;
	public boolean tracing = false;
	
	public Bullet() {
			
		}
	public Bullet(World world,PlayScreen screen,float x,float y) {
		this.world = world;
		this.screen = screen;
		this.posX = x;
		this.posY = y;
		
		prepareAnimation();
		SpriteHeight = getRegionHeight();
		SpriteWidth = getRegionWidth();
		if(this instanceof HeroBullet2) defineBullet(x, y, 1);
		else defineBullet(x,y);
		
		if(bulletDef != null) {
			bulletDef.setUserData(this);
			Collision.setCategoryFilter(bulletDef, Collision.HEROBULLET_BITS,null);
		}
	}
	public Bullet(World world,PlayScreen screen,float x, float y,int direction) {
		this.world = world;
		this.screen = screen;
		this.posX = x;
		this.posY = y;
		
		this.direction = direction;
		prepareAnimation();
		SpriteHeight = getRegionHeight();
		SpriteWidth = getRegionWidth();
		if(this instanceof HeroBullet2) defineBullet(x, y, -1);
		else defineBullet(x,y);
		
		if(bulletDef != null) {
			bulletDef.setUserData(this);
			Collision.setCategoryFilter(bulletDef, Collision.HEROBULLET_BITS,null);
		}
		
		
	}
	
	public void SetSpeed(int x) {
		
		speed = x;
	}
	public void SetSpeed(float dx, float dy) {
		Movement(dx,dy,false);
	}
	public void Movement(float dx, float dy,boolean nothing) {
	    b2body.setLinearVelocity(dx, dy);
	}

	
	abstract public void prepareAnimation() ;
	
	public void update(float dt) {
//		tt += dt;
//		if(tt >= lifeTime) this.getTexture().dispose();
		stateTime += dt;
		if(!isInfinited) {
			if(bullet.isAnimationFinished(stateTime)) remove();
			
		}
		if((this.getX()>screen.getGamePort().getScreenX()+screen.getGamePort().getScreenWidth()/2)
				||(this.getX()<screen.getGamePort().getScreenX()-screen.getGamePort().getScreenWidth()/2)) {
			remove();
		}
		if(!isDied) {
			if(!isInfinited)
			region = bullet.getKeyFrame(stateTime,false);
			
			if(!region.isFlipX()) {
				if(direction == -1) region.flip(true, false);
			}
			setRegion(region);
			setBounds(b2body.getPosition().x-SpriteWidth/CuocChienSinhTon.PPM/2,
					b2body.getPosition().y-SpriteHeight/CuocChienSinhTon.PPM/2,
					getRegionWidth()/CuocChienSinhTon.PPM,
					getRegionHeight()/CuocChienSinhTon.PPM);
			
			screen.game.getBatch().begin();
			this.draw(screen.game.getBatch());
			screen.game.getBatch().end();
			
			Movement();									
		}
	}
	public void update(float dt,float speed) {
		
		stateTime += dt;
		if(!isInfinited) {
			region = bullet.getKeyFrame(stateTime,false);
			if(bullet.isAnimationFinished(stateTime)) remove();
			else setRegion(region);
		}
		else {
			if (stateTime>bullet.getAnimationDuration())
				region = bullet.getKeyFrame(bullet.getAnimationDuration()-bullet.getFrameDuration());
			else region = bullet.getKeyFrame(stateTime, isInfinited);
			setRegion(region);
		}
		
				
				setBounds(b2body.getPosition().x-SpriteWidth/CuocChienSinhTon.PPM/2,
						b2body.getPosition().y-SpriteHeight/CuocChienSinhTon.PPM/2,
						getRegionWidth()/CuocChienSinhTon.PPM,
						getRegionHeight()/CuocChienSinhTon.PPM);
				
				screen.game.getBatch().begin();
				this.draw(screen.game.getBatch());
				screen.game.getBatch().end();
				
				if(this instanceof HeroBullet1) Movement(speed,1);	
								
			
		
		
	}
	
	
	
	public void remove() {
		isDied = true;
		BulletManage.markRemoved(this);
	}
	
	public void Movement() {
		
		
		 b2body.setLinearVelocity(new Vector2(speed*direction,0));
	}


	public void Movement(float speed,float direction1) {
		b2body.setLinearVelocity(SPEED*this.direction+speed*direction1,0);
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
		 shape.setRadius(getRegionHeight()/CuocChienSinhTon.PPM/2);
		 fdef.shape = shape;
		 fdef.isSensor = true;
		 bulletDef = b2body.createFixture(fdef);
	}
	public void defineBullet(float x,float y,float scale) {
		CircleShape shape = new CircleShape();
		 bdef.position.set(x,y);
		 bdef.type = BodyDef.BodyType.DynamicBody;
		 bdef.gravityScale = 0;
		 b2body = world.createBody(bdef);
		 shape.setRadius(getRegionHeight()/CuocChienSinhTon.PPM);
		 fdef.shape = shape;
		 fdef.isSensor = true;
		 bulletDef = b2body.createFixture(fdef);
	}
	public void setInfinited(boolean x) {
		this.isInfinited=x;
		bullet.setPlayMode(PlayMode.LOOP);
	}

	
	
}