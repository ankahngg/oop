package com.badlogic.drop.Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Drop.MAP;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

public abstract class Hero extends Sprite{
	public enum State {FALLING,JUMPING,STANDING,RUNNING,ATTACKING1,ATTACKING2,ATTACKING3,HURT,DIE };
	public enum Input {LEFT,RIGHT,JUMP,STOP,CROUCH};
	public State currentState;
	public State previousState;
	public Input currentInput;
	public int currentRank;
	public World world;
	public Body body;
	public Body hitbox;
	public float speed = 10f;
	BodyDef bdef = new BodyDef();
	CircleShape shape = new CircleShape();
	FixtureDef fdef = new FixtureDef();
	Fixture normalDef,crouchDef;
	
	protected TextureAtlas atlasRunning;
	protected TextureAtlas atlasJumping;
	protected TextureAtlas atlasStanding;
	protected TextureAtlas atlasCrouch;
	protected TextureAtlas atlasAttack1;
	protected TextureAtlas atlasAttack2;
	protected TextureAtlas atlasAttack3;
	protected TextureAtlas atlasHurting;
	protected TextureAtlas atlasDieing;
	
	public Animation<TextureRegion> running;
	public Animation<TextureRegion> jumping;
	public Animation<TextureRegion> standing;
	public Animation<TextureRegion> crouch;
	public Animation<TextureRegion> attack1;
	public Animation<TextureRegion> attack2;
	public Animation<TextureRegion> attack3;
	public Animation<TextureRegion> die;
	public Animation<TextureRegion> hurt;
	
	
	protected double AttackCoolDown = 2000;
	protected double lastAttackTime = 0;
	protected int currentAttack = 0;
	
	protected float HeroHeight;
	protected float stateTime;
	protected boolean runningRight = true;
	protected boolean currentDirection = false;
	
	
	public boolean isHurting;
	public boolean isHurt;
	public boolean isAttacking;
	public boolean isDieing;
	public boolean isDie;
	public int hurtDirection;

	protected int Health;
	protected int HealthMax;

	protected Fixture attackFixture;
	protected PlayScreen screen;
	public Hero(World world, PlayScreen screen) {
		this.world = world;
		this.screen = screen;

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
		setRegion(getFrame(dt));
		setBounds(body.getPosition().x-getRegionWidth()/Drop.PPM/2,
				body.getPosition().y-HeroHeight/Drop.PPM/2,
				getRegionWidth()/Drop.PPM, 
				getRegionHeight()/Drop.PPM);
	}
	public TextureRegion getFrame(float dt) {
		if (screen.game.getCurrentMap()==MAP.MAP2) {
	        return standing.getKeyFrame(stateTime, true);
		}
		TextureRegion region;
		currentState = getFrameState();
		
		stateTime = (currentState == previousState ? stateTime + dt : 0);
	
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

		return region;
	}

	public void handleHurt(Fixture damageObject) {
		isHurt = true;
		Spine spine = ((Spine) damageObject.getUserData());
		if(spine.body.getPosition().x > body.getPosition().x) hurtDirection = 0;
		else hurtDirection = 1;
		Health --;
		if(Health == 0) handleDie();
	}

	private void handleDie() {
		isDie = true;
	}
	protected State getFrameState() {
		// TODO Auto-generated method stub
		if(isHurting) {
			if(!hurt.isAnimationFinished(stateTime)) {
				return State.HURT;
			}
			else isHurting = false;
		}
		
		if(isDieing) {
//			if(hurtDirection == 0) body.setLinearVelocity( new Vector2((float) (-1.5*speed),0));
//			else body.setLinearVelocity( new Vector2((float) (1.5*speed),0));
			if(!die.isAnimationFinished(stateTime)) {
				return State.DIE;
			}
			else isDieing = false;
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
			if(System.currentTimeMillis() - lastAttackTime >= 100) {
				Collision.heroAttack(screen);
				isAttacking = true;
				if(currentAttack == 3) currentAttack = 1;
				else currentAttack ++;
				if(currentAttack == 1) return State.ATTACKING1;
				if(currentAttack == 2) return State.ATTACKING2;
				if(currentAttack == 3) return State.ATTACKING3;
			}	
		}
		
		
		if(System.currentTimeMillis() - lastAttackTime >= 500 ) currentAttack = 0;
		if(body.getLinearVelocity().y != 0 ) return State.JUMPING;
		if(body.getLinearVelocity().x != 0 ) return State.RUNNING;
		return State.STANDING;
	}
	
	public float getStateTime() {
		return stateTime;
	}
	
}
