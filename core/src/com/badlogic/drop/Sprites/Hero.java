package com.badlogic.drop.Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
	public final int speed = 10;
	public enum State {FALLING,JUMPING,STANDING,RUNNING,ATTACKING1,ATTACKING2,ATTACKING3 };
	public enum Input {LEFT,RIGHT,JUMP,STOP,CROUCH};
	public State currentState;
	public State previousState;
	public Input currentInput;
	public World world;
	public Body b2body;
	public Body hitbox;
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
	
	public Animation<TextureRegion> running;
	public Animation<TextureRegion> jumping;
	public Animation<TextureRegion> standing;
	public Animation<TextureRegion> crouch;
	public Animation<TextureRegion> attack1;
	public Animation<TextureRegion> attack2;
	public Animation<TextureRegion> attack3;
	
	protected double AttackCoolDown = 2000;
	protected double lastAttackTime = 0;
	protected int currentAttack = 0;
	
	protected float HeroHeight;
	protected float stateTime;
	protected boolean runningRight = true;
	protected boolean currentDirection = false;
	
	protected boolean isAttacking;
	protected Fixture attackFixture;
	protected PlayScreen screen;
	public Hero(World world, PlayScreen screen) {
		this.world = world;
		this.screen = screen;

	}
	
	protected abstract void prepareAnimation();
	protected void handleInput(float dt) {
		Vector2 vel = b2body.getLinearVelocity();
		boolean stop = true;
		
		if(isAttacking) {
			b2body.setLinearVelocity( new Vector2(0,vel.y));
			return;
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			b2body.setLinearVelocity( new Vector2(-speed,vel.y));
			stop = false;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			stop = false;
			b2body.setLinearVelocity( new Vector2(speed,vel.y));
		}
		
		if(Gdx.input.isKeyPressed(Keys.W) && vel.y == 0) {
			b2body.applyLinearImpulse(new Vector2(0,30), b2body.getWorldCenter(),true);
			stop = false;
		}
		
		if(stop) b2body.setLinearVelocity( new Vector2(0,vel.y));
	}
	public abstract void detectCollison();
	public abstract void instructionSensor();
	public void update(float dt) {
		handleInput(dt);
		setRegion(getFrame(dt));
		setBounds(b2body.getPosition().x-getRegionWidth()/Drop.PPM/2,
				b2body.getPosition().y-HeroHeight/Drop.PPM/2,
				getRegionWidth()/Drop.PPM, 
				getRegionHeight()/Drop.PPM);
	}
	protected TextureRegion getFrame(float dt) {
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
		    
		    default:
		        region = standing.getKeyFrame(stateTime, true);
		        break;
		}
		
		float vel = b2body.getLinearVelocity().x;
		
		if((vel < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true,false);
			runningRight = false;
			
		}
		else if((vel > 0 || runningRight) && region.isFlipX()) {
			region.flip(true,false);
			runningRight = true;
		}
		
		
		// Update state time
		
		
		previousState = currentState;

		return region;
	}
	protected State getFrameState() {
		// TODO Auto-generated method stub
		//System.out.println(currentAttack);
		
	
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
				detectCollison();
				isAttacking = true;
				if(currentAttack == 3) currentAttack = 1;
				else currentAttack ++;
				if(currentAttack == 1) return State.ATTACKING1;
				if(currentAttack == 2) return State.ATTACKING2;
				if(currentAttack == 3) return State.ATTACKING3;
			}	
		}
		
		if(System.currentTimeMillis() - lastAttackTime >= 500 ) currentAttack = 0;
		if(b2body.getLinearVelocity().y != 0 ) return State.JUMPING;
		if(b2body.getLinearVelocity().x != 0 ) return State.RUNNING;
		return State.STANDING;
	}
	
}
