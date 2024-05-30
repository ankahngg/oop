package com.badlogic.drop.Sprites;

import java.util.Set;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.CuocChienSinhTon.MAP;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Hero.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Region;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Hero extends Sprite{
	public enum State {FALLING,JUMPING,STANDING,RUNNING,ATTACKING1,ATTACKING2,ATTACKING3,HURT,DIE,FIRING };
	public enum Input {LEFT,RIGHT,JUMP,STOP,CROUCH};
	public State currentState;
	public State previousState;
	public Input currentInput;
	public int currentRank;
	public int stageSkill = 0;
	public int damage = 1;
	public World world;
	public Body body;
	public Body hitbox;
	BodyDef bdef = new BodyDef();
	CircleShape shape = new CircleShape();
	public FixtureDef fdef = new FixtureDef();
	public Fixture normalDef,crouchDef;
	
	protected TextureAtlas atlasRunning;
	protected TextureAtlas atlasJumping;
	protected TextureAtlas atlasStanding;
	protected TextureAtlas atlasCrouch;
	protected TextureAtlas atlasAttack1;
	protected TextureAtlas atlasAttack2;
	protected TextureAtlas atlasAttack3;
	protected TextureAtlas atlasEnergyBall;
	protected TextureAtlas atlasHurting;
	protected TextureAtlas atlasDieing;
	
	public Animation<TextureRegion> running;
	public Animation<TextureRegion> jumping;
	public Animation<TextureRegion> standing;
	public Animation<TextureRegion> crouch;
	public Animation<TextureRegion> attack1;
	public Animation<TextureRegion> attack2;
	public Animation<TextureRegion> attack3;
	public Animation<TextureRegion> energyBall;
	public Animation<TextureRegion> die;
	public Animation<TextureRegion> hurt;
	
	
	protected double AttackCoolDown = 2000;
	protected double lastAttackTime = 0;
	protected int currentAttack = 0;
	
	protected double FireCoolDown = 1000;
	protected double lastFireTime = 0;
	protected int currentFire = 0;
	
	public double shieldBegin = -1;
	public float HeroHeight;
	public float HeroWidth;
	protected float stateTime;
	protected boolean runningRight = true;
	protected boolean currentDirection = false;
	
	
	public boolean isHurting;
	public boolean isHurt;
	public boolean isAttacking;
	public boolean isDieing;
	public boolean isDie;
	public boolean isFiring;
	public boolean isHurtWhenCollide = false;
	public int hurtDirection;
	
	public int Health;
	public int HealthMax;
	public String bulletType;
	protected Fixture attackFixture;
	protected PlayScreen screen;
	protected Bullet bullet;
	private TextureRegion region;
	private Shield shield;
	public Hero(World world, PlayScreen screen,float x, float y, int maxHealth) {
		this.world = world;
		this.screen = screen;
		isDie = false;
		this.Health = maxHealth;
		this.HealthMax = maxHealth;
		this.currentRank = 0;
		prepareAnimation();
		defineHero(x, y);

		setBounds(0, 0, getRegionWidth()/CuocChienSinhTon.PPM, getRegionHeight()/CuocChienSinhTon.PPM);
		currentState = State.STANDING;
		previousState = State.STANDING;
		Collision.setCategoryFilter(normalDef, Collision.HERO_BITS,null);
		normalDef.setUserData(this);
		
		shield = new Shield(world, screen);
		
	}
	public void setBullet(World world,PlayScreen screen,float x, float y,int direction) {
		BulletManage.addBullet("EnergyBall", x, y, direction);
	}
	public Body getBody() {
		return body;
	}
	public int getHealth() {
		return Health;
	}
	public void setHealth(int health) {
		if(Health < HealthMax) Health += health;
	}
	public int getHealthMax() {
		return HealthMax;
	}
	public void setHealthMax(int healthMax) {
		HealthMax = healthMax;
	}
	
	protected abstract void prepareAnimation();
	
	public void update(float dt) {
		if(shieldBegin != -1) {
			if(System.currentTimeMillis()-shieldBegin >= 5000) shieldBegin = -1;
			else shield.update(body.getPosition().x, body.getPosition().y, dt);
		}
		setRegion(getFrame(dt));
		setBounds(body.getPosition().x-getRegionWidth()/CuocChienSinhTon.PPM/2,
				body.getPosition().y-HeroHeight/CuocChienSinhTon.PPM/2,
				getRegionWidth()/CuocChienSinhTon.PPM, 
				getRegionHeight()/CuocChienSinhTon.PPM);
		
		if(screen instanceof FirstMap)
			attackFix();
	}
	
	private void attackFix() {
		PolygonShape hitbox = new PolygonShape();
		if(runningRight != currentDirection) {
			if(attackFixture != null) body.destroyFixture(attackFixture);
			if(runningRight) hitbox.setAsBox(2, 3,new Vector2(2,1),0);
			else hitbox.setAsBox(2, 3,new Vector2(-2,1),0);
			fdef.shape = hitbox;
			fdef.isSensor = true;
			attackFixture = body.createFixture(fdef);
			attackFixture.setUserData("DamageRange");
			currentDirection = runningRight;
			Collision.setCategoryFilter(attackFixture, Collision.HEROATTACK_BITS,null);

		}
	}
	public TextureRegion getFrame(float dt) {
		region=null;
		currentState = getFrameState();
		stateTime = (currentState == previousState ? stateTime + dt : 0);
		if (screen instanceof FirstMap) {
			switch (currentState) {
		    case JUMPING:
		        region = jumping.getKeyFrame(stateTime, false);
		        break;
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
		    case HURT:
		    	region = hurt.getKeyFrame(stateTime, false);
		    	break;

		    case DIE:
		    	region = die.getKeyFrame(stateTime, false);
		    	break;
		    case FIRING:
		    	region = attack3.getKeyFrame(stateTime,false);
		    	break;
		    
		    default:
		        region = standing.getKeyFrame(stateTime, true);
		        break;
			}
			
			float vel = body.getLinearVelocity().x;
			
			if((vel < 0 || !runningRight) && !region.isFlipX()) {
				region.flip(true,false);
				runningRight = false;
				
			}
			else if((vel > 0 || runningRight) && region.isFlipX()) {
				region.flip(true,false);
				runningRight = true;
			}
			
			previousState = currentState;
		}
		else if (screen instanceof FlappyMap) {
			
			stateTime = (currentState == previousState ? stateTime + dt : 0);
		
			switch (currentState) {
			    
			    case FIRING:
			    	
			    	region = standing.getKeyFrame(stateTime);
			    	break;
			    case HURT:
			    	region = hurt.getKeyFrame(stateTime, false);
			    	break;
	
			    case DIE:
			    	region = die.getKeyFrame(stateTime, false);
			    	break;
			    
			    default:
			        region = standing.getKeyFrame(stateTime, true);
			        break;
			}
			
			previousState = currentState;
	
			
		}
		return region;
	}

	public void handleHurt(Fixture damageObject) {
		if(shieldBegin != -1) return;
		screen.playHeroHurtSound();
		isHurt = true;
		isAttacking = false;
		
		
		Health --;
		
		if(Health <= 0) {
			handleDie();
		}
	}

	private void handleDie() {
		isDie = true;

		
	}
	protected State getFrameState() {
		if (screen instanceof FirstMap) {
			
			// TODO Auto-generated method stub
			if(body.getPosition().y < 0) handleDie(); 
			
			if(isHurting) {
				if(!hurt.isAnimationFinished(stateTime)) {
					return State.HURT;
				}
				else isHurting = false;
			}
			
			if(isDieing) {
				if(!die.isAnimationFinished(stateTime)) {
					return State.DIE;
				}
				else {
					isDieing = false;
					screen.handleDie();
				}
			}
			if(isHurt) {
				isHurting = true;
				isAttacking = false;
				isFiring = false;
				isHurt = false;
				return State.HURT;
			}
			
			if(isDie) {
				isDieing = true;
				isDie = false;
				return State.DIE;
			}
			
			if(isFiring) {
				if(!attack3.isAnimationFinished(stateTime)) return State.FIRING;
				else {
					isFiring = false;
					lastFireTime = System.currentTimeMillis();
				}
			}
			
			if(isAttacking) {
				if(currentAttack == 1) {
					if(!attack1.isAnimationFinished(stateTime)) return State.ATTACKING1;
					else {isAttacking = false; lastAttackTime = System.currentTimeMillis();}				
				}
				if(currentAttack == 2) {
					if(!attack2.isAnimationFinished(stateTime)) return State.ATTACKING2;
					else {isAttacking = false; lastAttackTime = System.currentTimeMillis();}				
				}
				if(currentAttack == 3) {
					if(!attack3.isAnimationFinished(stateTime)) return State.ATTACKING3;
					else {isAttacking = false; lastAttackTime = System.currentTimeMillis();}				
				}
			}
			
			
			if(Gdx.input.isKeyPressed(Keys.J)) {
				if(System.currentTimeMillis() - lastAttackTime >= 50) {
					Collision.heroAttack(screen);
					isAttacking = true;
					if(currentAttack == 3) currentAttack = 1;
					else currentAttack ++;
					if(currentAttack == 1) return State.ATTACKING1;
					if(currentAttack == 2) return State.ATTACKING2;
					if(currentAttack == 3) return State.ATTACKING3;
				}	
			}
			
			if(Gdx.input.isKeyPressed(Keys.K) && ((FirstMap) screen).stagePass >= stageSkill) {
				if(System.currentTimeMillis() - lastFireTime >= FireCoolDown) {
					isFiring = true;
					if(this.isFlipX()) BulletManage.addBullet(bulletType, body.getPosition().x, body.getPosition().y, -1);
					else BulletManage.addBullet(bulletType, body.getPosition().x, body.getPosition().y, 1);
//					if(this.isFlipX()) BulletManage.addBullet(bulletType, body.getPosition().x, body.getPosition().y, -1,-1,45,-1);
//					else BulletManage.addBullet(bulletType, body.getPosition().x, body.getPosition().y, 1,-1,45,-1);
					return State.FIRING;
				}
			}
			
			
			if(System.currentTimeMillis() - lastAttackTime >= 500 ) currentAttack = 0;
			if(body.getLinearVelocity().y != 0 ) return State.JUMPING;
			if(body.getLinearVelocity().x != 0 ) {
				return State.RUNNING;
			}
			
			return State.STANDING;
		}
			
		else if (screen instanceof FlappyMap) {
			if(isHurting) {
				if(!hurt.isAnimationFinished(stateTime)) {
					return State.HURT;
				}
				else isHurting = false;
			}
			
			if(isDieing) {

				if(!die.isAnimationFinished(stateTime)) {
					return State.DIE;
				}
				else {
					isDieing = false;
					
					
					if (screen instanceof FlappyMap) {
						((FlappyMap)screen).handleDie();
					}
				}
			}
			if(isHurt) {
				isHurting = true;
				isHurt = false;
				return State.HURT;
			}
			
			if(isDie) {
				isDieing = true;
				isDie = false;
				return State.DIE;
			}
			
			if(isAttacking) {
				
					if(!attack1.isAnimationFinished(stateTime)) return State.ATTACKING1;
					else {isAttacking = false; lastAttackTime = System.currentTimeMillis();}				
				
			}
			if(Gdx.input.isKeyPressed(Keys.J)) {
				if(System.currentTimeMillis() - lastAttackTime >= 50) {
					Collision.heroAttack(screen);
					
					BulletManage.addBullet(bulletType, this.getX(), this.getY(), 1,20,0,-1);
					isAttacking = true;
					return State.FIRING;
				}
			}
			return State.STANDING;
		}
		return State.STANDING;
		
	}
	
	public float getStateTime() {
		return stateTime;
	}
	protected void defineHero(float x,float y) {
		bdef.position.set(x,y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		 body = world.createBody(bdef);
		 PolygonShape shape = new PolygonShape();
		 shape.setAsBox(getRegionWidth()/CuocChienSinhTon.PPM/2, getRegionHeight()/CuocChienSinhTon.PPM/2,new Vector2(0,0),0);
//		 shape.setRadius(getRegionHeight()/CuocChienSinhTon.PPM/2);
		 fdef.shape = shape;
		 fdef.friction = 0f;
		 normalDef = body.createFixture(fdef);
		 normalDef.setUserData("herobody");
		 
	}
	
}
