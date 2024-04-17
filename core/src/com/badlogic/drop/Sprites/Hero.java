package com.badlogic.drop.Sprites;

import java.awt.Rectangle;
import java.awt.RenderingHints.Key;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Region;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class Hero extends Sprite{
	final int speed = 10;
	public enum State {FALLING,JUMPING,STANDING,RUNNING,ATTACKING1,ATTACKING2,ATTACKING3,HURT,DIE };
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
	private TextureAtlas atlasRunning;
	private TextureAtlas atlasJumping;
	private TextureAtlas atlasStanding;
	private TextureAtlas atlasCrouch;
	private TextureAtlas atlasAttack1;
	private TextureAtlas atlasAttack2;
	private TextureAtlas atlasAttack3;
	private TextureAtlas atlasHurt;
	private TextureAtlas atlasDie;
	
	public Animation<TextureRegion> running;
	public Animation<TextureRegion> jumping;
	public Animation<TextureRegion> standing;
	public Animation<TextureRegion> crouch;
	public Animation<TextureRegion> attack1;
	public Animation<TextureRegion> attack2;
	public Animation<TextureRegion> attack3;
	private Animation<TextureRegion> die;
	private Animation<TextureRegion> hurt;
	
	private double AttackCoolDown = 2000;
	private double lastAttackTime = 0;
	private int currentAttack = 0;
	
	private float HeroHeight;
	private float stateTime;
	private boolean runningRight = true;
	private boolean currentDirection = false;
	
	private boolean isAttacking;
	private boolean isHurting;
	private int hurtDirection;
	private Fixture attackFixture;
	private PlayScreen screen;
	
	public int HealthMax=30;
	public int Health=6;
	public boolean isHurt = false;
	private TextureRegion region;
	
	
	
	public Hero(World world, PlayScreen screen) {		
		this.world = world;
		this.screen = screen;
		prepareAnimation();
		defineHero();
		
		setBounds(0, 0, getRegionWidth()/Drop.PPM, getRegionHeight()/Drop.PPM);
		currentState = State.STANDING;
		previousState = State.STANDING;
	}
	
	public void prepareAnimation() {
		atlasAttack1 = new TextureAtlas("Hero2/packs/Attack1.pack");
		atlasAttack2 = new TextureAtlas("Hero2/packs/Attack2.pack");
		atlasAttack3 = new TextureAtlas("Hero2/packs/Attack3.pack");
		atlasStanding = new TextureAtlas("Hero2/packs/Idle.pack");
		atlasRunning = new TextureAtlas("Hero2/packs/Run.pack");
		atlasJumping = new TextureAtlas("Hero2/packs/Jump.pack");
		atlasHurt = new TextureAtlas("Hero2/packs/Hurt.pack");
		atlasDie = new TextureAtlas("Hero2/packs/Die.pack");
		
		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
		attack3 = new Animation<TextureRegion>(0.1f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.1f, atlasRunning.getRegions());
		jumping = new Animation<TextureRegion>(0.1f, atlasJumping.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		die = new Animation<TextureRegion>(0.5f, atlasDie.getRegions());
		hurt = new Animation<TextureRegion>(0.1f, atlasHurt.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		HeroHeight = getRegionHeight();
	}
	
	private void handleInput(float dt) {
		Vector2 vel = b2body.getLinearVelocity();
		boolean stop = true;
		
		
		if(isHurting) {
			if(hurtDirection == 0) {
				b2body.setLinearVelocity( new Vector2((float) (-1.5*speed),0));
			}
			else b2body.setLinearVelocity( new Vector2((float) (1.5*speed),0));
			return;
		}
		
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
	
	public void update(float dt) {
		
		handleInput(dt);
		setRegion(getFrame(dt));
		setBounds(b2body.getPosition().x-getRegionWidth()/Drop.PPM/2,
				b2body.getPosition().y-HeroHeight/Drop.PPM/2,
				getRegionWidth()/Drop.PPM, 
				getRegionHeight()/Drop.PPM);
		
		attackFix();

	}
	
	private TextureRegion getFrame(float dt) {
		
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
	
	public void handleHurt(Fixture damageObject) {
		isHurt = true;
		Spine spine = ((Spine) damageObject.getUserData());
		if(spine.body.getPosition().x > b2body.getPosition().x) hurtDirection = 0;
		else hurtDirection = 1;
		Health --;
		
	}

	private State getFrameState() {
		// TODO Auto-generated method stub
		if(isHurting) {
			if(!hurt.isAnimationFinished(stateTime)) {
				return State.HURT;
			}
			else isHurting = false;
		}
		if(isHurt) {
			isHurting = true;
			isHurt = false;
			return State.HURT;
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
		if(b2body.getLinearVelocity().y != 0 ) return State.JUMPING;
		if(b2body.getLinearVelocity().x != 0 ) return State.RUNNING;
		return State.STANDING;
	}
	
	private void attackFix() {
		PolygonShape hitbox = new PolygonShape();
		if(runningRight != currentDirection) {
			if(attackFixture != null) b2body.destroyFixture(attackFixture);
			if(runningRight) hitbox.setAsBox(2, 2,new Vector2(2,1),0);
			else hitbox.setAsBox(2, 2,new Vector2(-2,1),0);
			fdef.shape = hitbox;
			fdef.isSensor = true;
			attackFixture = b2body.createFixture(fdef);
			attackFixture.setUserData("DamageRange");
			Collision.setCategoryFilter(attackFixture, Collision.HEROATTACK_BITS);
			currentDirection = runningRight;
		}
	}

	private void defineHero() {
		// TODO Auto-generated method stub
		 bdef.position.set(30,10);
		 bdef.type = BodyDef.BodyType.DynamicBody;
		 b2body = world.createBody(bdef);
		 shape.setRadius(getRegionHeight()/Drop.PPM/2);
		 
		 fdef.shape = shape;
		 normalDef = b2body.createFixture(fdef);
		 normalDef.setUserData(this);
		
		 Collision.setCategoryFilter(normalDef, Collision.HERO_BITS);
	}
	

	
}	
