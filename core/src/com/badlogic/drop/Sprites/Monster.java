package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;

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

abstract public class Monster extends Sprite{
		public enum State {FALLING,JUMPING,STANDING,RUNNING,ATTACKING1,ATTACKING2,ATTACKING3,DIE, HURT};
		public State currentState ,previousState;
		public World world;
		public Animation<TextureRegion> jumping;
		public TextureAtlas atlasAttack1;
		public TextureAtlas atlasAttack2;
		public TextureAtlas atlasAttack3;
		public TextureAtlas atlasStanding;
		public TextureAtlas atlasRunning;
		public TextureAtlas atlasDie;
		public TextureAtlas atlasHurt;
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
		
		public boolean isHurt = false;
		public boolean isIntialLeft = false;
		public int MonsterHeight;
		public int MonsterWidth;
		public boolean runningRight = true;
		
		public int getHealthMax() {
			return HealthMax;
		}
		public int getHealth() {
			return Health;
		}
		
		abstract public void prepareAnimation() ;
		public Monster(World world, FirstMap screen, int x, int y) {		
			this.world = world;
			this.Health = 20;
			this.HealthMax = 20;
			prepareAnimation();
			defineMonster(x,y);
			setBounds(0, 0, getRegionWidth()/CuocChienSinhTon.PPM, getRegionHeight()/CuocChienSinhTon.PPM);
			
		}
		
		public void update(float dt) {
			setRegion(getFrame(dt));
			setBounds(b2body.getPosition().x-MonsterWidth/CuocChienSinhTon.PPM/2,
					b2body.getPosition().y-MonsterHeight/CuocChienSinhTon.PPM/2,
					getRegionWidth()/CuocChienSinhTon.PPM,
					getRegionHeight()/CuocChienSinhTon.PPM);
			
			Movement();
		}
		
		abstract public void Movement();	
		
		
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

			return region;
		}
		
		double t = 1000;
		boolean isAttacking = false;
		boolean isHurting = false;
		
		abstract public State getFrameState(float dt) ;
		
		void onHit() {
			this.Health --;
		}
		
		public void defineMonster(int x,int y) {
			if(x == 30 && y == 2) System.out.println("lol");
			CircleShape shape = new CircleShape();
			 bdef.position.set(x,y);
			 bdef.type = BodyDef.BodyType.DynamicBody;
			 b2body = world.createBody(bdef);
			 shape.setRadius(getRegionHeight()/CuocChienSinhTon.PPM/2);
			 fdef.shape = shape;
			 monsterDef = b2body.createFixture(fdef);
			
			  
		}

}