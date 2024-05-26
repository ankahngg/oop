package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BossBullet1 extends Bullet{
	private float playerY;
	private float playerH;
	private float bulletY;
	private float bulletH;
	private boolean isPlayerHurt = false;

	public BossBullet1(World world, PlayScreen screen, float x, float y, int direction) {
		super(world, screen, x, y, direction);
		speed = 15;
		
	}

	@Override
	public void prepareAnimation() {
		atlasBullet = new TextureAtlas("Boss/Bullet/BossBullet1_1.pack");
		bullet = new Animation<TextureRegion>(0.3f, atlasBullet.getRegions());
		setRegion(atlasBullet.getRegions().get(0));
		scaleX = 12;
		scaleY=8;
	}
	public void Movement() {
		
	}
	public void update(float dt) {
//		tt += dt;
//		if(tt >= lifeTime) this.getTexture().dispose();
		stateTime += dt;
		 playerY = screen.getPlayer().body.getPosition().y;
		 playerH = screen.getPlayer().getRegionHeight()/CuocChienSinhTon.PPM;
		 bulletY = b2body.getPosition().y;
		 bulletH = atlasBullet.getRegions().get(2).getRegionHeight()/CuocChienSinhTon.PPM*scaleY;
		if(bullet.getKeyFrameIndex(stateTime) == 2 && 
		(playerY+playerH/2>bulletY-bulletH/2 && playerY-playerH/2<bulletY+bulletH/2) && !isPlayerHurt) {
			
			screen.getPlayer().handleHurt(null);
			isPlayerHurt  = true;
		}
//		if(bullet.getKeyFrameIndex(stateTime) == 2 && bulletDef == null) {
//			
//			addFixture();
//		}
		if(stateTime > bullet.getAnimationDuration()) remove();
		if(!isDied) {
			region = bullet.getKeyFrame(stateTime,true);
			if(!region.isFlipX()) {
				if(direction == -1) region.flip(true, true);
			}
			setRegion(region);
			setBounds(b2body.getPosition().x-getRegionWidth()/CuocChienSinhTon.PPM*scaleX/2,
					b2body.getPosition().y -getRegionHeight()/CuocChienSinhTon.PPM*scaleY/2,
					getRegionWidth()/CuocChienSinhTon.PPM*scaleX,
					getRegionHeight()/CuocChienSinhTon.PPM*scaleY);
			
			Movement();
			screen.game.getBatch().begin();
			this.draw(screen.game.getBatch());
			screen.game.getBatch().end();									
		}
	}
	
	public void addFixture() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(getRegionWidth()/CuocChienSinhTon.PPM/2*scaleX, getRegionHeight()/CuocChienSinhTon.PPM/2*scaleY,new Vector2(0,0),0);
		fdef.shape = shape;
		fdef.isSensor = true;
		bulletDef = b2body.createFixture(fdef);
		bulletDef.setUserData(this);
	}
	
	public void defineBullet(float x,float y) {
		 bdef.position.set(x-getRegionWidth()/CuocChienSinhTon.PPM*scaleX/2,
				 y-getRegionHeight()/CuocChienSinhTon.PPM*scaleY/2);
		
		 bdef.type = BodyDef.BodyType.DynamicBody;
		 bdef.gravityScale = 0;
		 b2body = world.createBody(bdef);
		 
	}
	
	
}
