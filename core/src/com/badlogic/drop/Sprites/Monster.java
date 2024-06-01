package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Monster.State;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

abstract public class Monster extends Sprite{
		public enum State {FALLING,JUMPING,STANDING,RUNNING,ATTACKING1,ATTACKING2,ATTACKING3,DIE, HURT,APPEARED,DISAPPEARED};
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
		
		public double attackCd = 1000;
		public double lastAttackTime = 0;
		public double lastTeleTime = 0;
		public double TeleCd = 500;
		public double lastCheckJump = 0;
		public Body b2body;
		public Body hitbox;
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		public Fixture monsterDef;
		public Fixture hitboxDef;
		public boolean isGravityScale=false;
		
		double t = 1000;
		public boolean isAttacking1 = false;
		public boolean isAttacking2 = false;
		public boolean isHurting = false;
		public boolean isDieing = false;
		
		public boolean isVisible = true;
		public boolean isRuningR;
		public boolean isDynamic=true;
		public boolean isSensor=false;
		public boolean isHurt = false;
		public boolean isDie = false;
		public boolean isDied = false;
		public boolean isDieFinish = false;
		public boolean isRemoved = false;
		public boolean isIntialLeft = false;
		public float MonsterScaleX=1;
		public float MonsterScaleY=1;
		public float MonsterHeight;
		public float MonsterWidth;
		public StageCreator stageCreator;
		public BulletManage bulletManage;
		public boolean runningRight = true;
		public PlayScreen screen;
		public SpriteBatch batch;
		public float posX;
		public float posY;
		public float speed;
		public float angle;
		public float lifeTime=-1;
		public int direction=0;
		public HealthBarMonster HealthBar;
		public float radius;
		
		public int getHealthMax() {
			return HealthMax;
		}
		public int getHealth() {
			return Health;
		}
		
		abstract public void prepareAnimation() ;
		public Monster(World world, PlayScreen screen, float x, float y,int maxHealth, boolean isDynamic, boolean isSensor) {		
			this.world = world;
			this.Health = maxHealth;
			this.HealthMax = maxHealth;
			this.screen = screen;
			this.posX = x;
			this.posY = y;
			this.isDynamic = isDynamic;
			this.isSensor = isSensor;
			this.batch = screen.game.getBatch();
			stageCreator = screen.stageCreator;
			bulletManage = screen.bulletManage;
			HealthBar = new HealthBarMonster(screen);
			prepareAnimation();
			defineMonster(x,y);
			setBounds(0, 0, getRegionWidth()/CuocChienSinhTon.PPM, getRegionHeight()/CuocChienSinhTon.PPM);
			
			Collision.setCategoryFilter(monsterDef,Collision.MONSTER_BITS,null);
		}
		
		
		
		public void update(float dt) {
			
			if(b2body != null) {
				posX = b2body.getPosition().x;
				posY = b2body.getPosition().y;		
			}
			if(!isRemoved) {
				if(isDied) removeMonster();
				else {
					if(lifeTime != -1) {
						if(stateTime > lifeTime) {
							removeMonster();
							isDieFinish = true;
							stageCreator.removeMonster(this);
							
						}
					}
					else {
						if((posX>screen.getCamera().position.x+screen.getCamera().viewportWidth/2)
								||(posX<screen.getCamera().position.x-screen.getCamera().viewportWidth/2)) {
							removeMonster();
							isDieFinish = true;
							stageCreator.removeMonster(this);
						}
					}					
				}
			}
			
			setRegion(getFrame(dt));
			if(isRemoved && isDieFinish) return;
			//set sprite bounds
			setBounds(posX-MonsterWidth/CuocChienSinhTon.PPM/2,posY-MonsterHeight/CuocChienSinhTon.PPM/2,getRegionWidth()/CuocChienSinhTon.PPM*MonsterScaleX,getRegionHeight()/CuocChienSinhTon.PPM*MonsterScaleY);
			
			//update healthbar
			HealthBar.update(Health, HealthMax, posX, posY+radius);
			
			//handle movement
			movement();
			
			//render
			batch.begin();
			this.draw(batch);
			batch.end();
			
		}
		
		public void removeMonster() {
			isRemoved = true;
			world.destroyBody(b2body);
			b2body = null;
			//stageCreator.removeMonster(this);
		}
		
		public void setUp(int directionn, Float speedd, float anglee, float lifeTimee) {
			if(speedd != -1) speed = speedd;
			direction = directionn;
			if(anglee != -1) angle = anglee;
			lifeTime = lifeTimee;
		}
		
			
		public void movement() {
			if(direction != 0 && b2body != null) {
				double sin = Math.sin(Math.toRadians(angle));
				double cos =  Math.cos(Math.toRadians(angle));
				float vecX = (float) (speed*cos*direction);
				float vecY = (float) (speed*sin); 
				b2body.setLinearVelocity(new Vector2(vecX,vecY));				
			}
		}
		
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
				
				if(vel == 0) {
					if(!isIntialLeft && !region.isFlipX()) region.flip(true, false);
					else if(isIntialLeft && region.isFlipX()) region.flip(true, false);
					previousState = currentState;
					return region;
				}
					
				
				
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
				
			}
			previousState = currentState;
			return region;
		}
		public void onWallCollision() {
			isRuningR = !isRuningR;
		}
		
		
		abstract public State getFrameState(float dt) ;
		
		
		
		void onHit() {
			if(!isVisible) return;
			this.Health -= screen.getPlayer().damage;
			if(this.Health <= 0) {
//				if(screen instanceof FirstMap) {
//					int rnd1 = MathUtils.random(3);
//					if(rnd1 == 0) {
//						int rnd2 = MathUtils.random(3);
//						switch (rnd2) {
//						case 0:
//							StageCreator.addItems("Shield", b2body.getPosition().x, b2body.getPosition().y);
//							break;
//						case 1:
//							StageCreator.addItems("Strength", b2body.getPosition().x, b2body.getPosition().y);
//							break;
//							
//						default:
//							StageCreator.addItems("Heart", b2body.getPosition().x, b2body.getPosition().y);
//							
//							break;
//						}
//					}
//					
//				}
				isDie = true;
				
				isDied = true;
			}
			else {
				isHurt = true;
			}
		}
		
		public void defineMonster(float x,float y) {
			CircleShape shape = new CircleShape();
			 bdef.position.set(x,y);
			 if(isDynamic) bdef.type = BodyDef.BodyType.DynamicBody;
			 else  bdef.type = BodyDef.BodyType.StaticBody;
			 
			 if(!isGravityScale) bdef.gravityScale = 0;
			 b2body = world.createBody(bdef);
			 
			 shape.setRadius(getRegionHeight()/CuocChienSinhTon.PPM/2);
			 radius = getRegionHeight()/CuocChienSinhTon.PPM/2;
			 fdef.shape = shape;
			 fdef.isSensor = isSensor;
			
			 monsterDef = b2body.createFixture(fdef);
		}

}
