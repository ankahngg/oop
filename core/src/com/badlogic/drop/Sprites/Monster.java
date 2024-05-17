package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

abstract public class Monster extends Sprite{
		public enum State {FALLING,JUMPING,STANDING,RUNNING,ATTACKING1,ATTACKING2,ATTACKING3,DIE, HURT};
		public State currentState ,previousState;
		public World world;
		public TextureAtlas atlasAttack1;
		public TextureAtlas atlasAttack2;
		public TextureAtlas atlasAttack3;
		public TextureAtlas atlasStanding;
		public TextureAtlas atlasRunning;
		public TextureAtlas atlasDie;
		public TextureAtlas atlasHurt;
		public Animation<TextureRegion> jumping;
		public Animation<TextureRegion> attack1;
		public Animation<TextureRegion> attack2;
		public Animation<TextureRegion> attack3;
		public Animation<TextureRegion> running;
		public Animation<TextureRegion> standing;
		public Animation<TextureRegion> die;
		public Animation<TextureRegion> hurt;
		public float stateTime;
		java.util.Random rd = new java.util.Random();
		
		public int Health;
		public int HealthMax;
		
		
		public double lastAttackTime = 0;
		public double lastTeleTime = 0;
		public double TeleCd = 50;
		public Body b2body;
		public Body hitbox;
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		public Fixture monsterDef;
		public Fixture hitboxDef;
		
		double t = 1000;
		public boolean isAttacking = false;
		public boolean isHurting = false;
		public boolean isDieing = false;
		
		public boolean isRuningR;
		public boolean isDynamic;
		public boolean isHurt = false;
		public boolean isDie = false;
		public boolean isDied = false;
		public boolean isIntialLeft = false;
		public int MonsterHeight;
		public int MonsterWidth;
		public boolean runningRight = true;
		public PlayScreen screen;
		public SpriteBatch batch;
		public int posX;
		public int posY;
		
		public int getHealthMax() {
			return HealthMax;
		}
		public int getHealth() {
			return Health;
		}
		
		abstract public void prepareAnimation() ;
		public Monster(World world, PlayScreen screen, int x, int y, boolean isDynamic) {		
			this.world = world;
			this.Health = 20;
			this.HealthMax = 20;
			this.screen = screen;
			this.isDynamic = isDynamic;
			this.posX = x;
			this.posY = y;
			this.batch = screen.game.getBatch();
			prepareAnimation();
			defineMonster(x,y);
			setBounds(0, 0, getRegionWidth()/CuocChienSinhTon.PPM, getRegionHeight()/CuocChienSinhTon.PPM);
			Collision.setCategoryFilter(monsterDef,Collision.MONSTER_BITS,null);
			
		}
		
		public void update(float dt) {
			setRegion(getFrame(dt));
			if(isDied) return;
			setBounds(b2body.getPosition().x-MonsterWidth/CuocChienSinhTon.PPM/2,
					b2body.getPosition().y-MonsterHeight/CuocChienSinhTon.PPM/2,
					getRegionWidth()/CuocChienSinhTon.PPM,
					getRegionHeight()/CuocChienSinhTon.PPM);
			movement();
			batch.begin();
			this.draw(batch);
			batch.end();
		}
		
		abstract public void removeMonster();
			
		
		
		abstract public void movement();	
		
		
		public TextureRegion getFrame(float dt) {
			TextureRegion region;
			currentState = getFrameState(dt);
			
			stateTime = (currentState == previousState ? stateTime + dt : 0);
			switch (currentState) {
			    case RUNNING:
			        region = running.getKeyFrame(stateTime, true);
			        break;
			    case ATTACKING1:
			    	
			    	region = attack1.getKeyFrame(stateTime, false);
			    	break;
			    case ATTACKING2:
			    	region = attack2.getKeyFrame(stateTime, false);
			    	break;
			    	
			    case ATTACKING3:
			    	region = attack3.getKeyFrame(stateTime, false);
			    	break;
			    case DIE:
			    	region = die.getKeyFrame(stateTime, false);
			    	break;
			    case HURT:
			    	region = hurt.getKeyFrame(stateTime, false);
			    	break;
			    default:
			        region = standing.getKeyFrame(stateTime, true);
			        break;
			}
			
			if(b2body != null) {
				
				float vel = b2body.getLinearVelocity().x;
				if(!isIntialLeft) {
					if((vel < 0 || !runningRight) && !region.isFlipX()) {
						region.flip(true,false);
						runningRight = false;
						
					}
					else if((vel > 0 || runningRight) && region.isFlipX()) {
						region.flip(true,false);
						runningRight = true;
					}				
				}
				else {
					if((vel < 0 || !runningRight) && region.isFlipX()) {
						region.flip(true,false);
						runningRight = false;
					}
					else if((vel > 0 || runningRight) && !region.isFlipX()) {
						region.flip(true,false);
						runningRight = true;
					}				
				}
				
				previousState = currentState;
			}

			return region;
		}
		public void onWallCollision() {
			isRuningR = !isRuningR;
		}
		
		
		abstract public State getFrameState(float dt) ;
		
		void onHit() {
			this.Health --;
		}
		
		public void defineMonster(int x,int y) {
			
			CircleShape shape = new CircleShape();
			 bdef.position.set(x,y);
			 if(isDynamic) bdef.type = BodyDef.BodyType.DynamicBody;
			 else  bdef.type = BodyDef.BodyType.StaticBody;
			 b2body = world.createBody(bdef);
			 shape.setRadius(getRegionHeight()/CuocChienSinhTon.PPM/2);
			 fdef.shape = shape;
			 monsterDef = b2body.createFixture(fdef);
		}

}
