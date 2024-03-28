package com.badlogic.drop.Sprites;

import java.awt.RenderingHints.Key;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Hero extends Sprite{
	final int speed = 10;
	public enum State {FALLING,JUMPING,STANDING,RUNNING,ATTACKING, CROUCHING};
	public enum Input {LEFT,RIGHT,JUMP,STOP,CROUCH};
	public State currentState;
	public State previousState;
	public Input currentInput;
	public World world;
	public Body b2body;
	BodyDef bdef = new BodyDef();
	CircleShape shape = new CircleShape();
	FixtureDef fdef = new FixtureDef();
	Fixture normalDef,crouchDef;
	private TextureAtlas atlasAttack;
	private TextureAtlas atlasRunning;
	private TextureAtlas atlasJumping;
	private TextureAtlas atlasStanding;
	private TextureAtlas atlasCrouch;
	
	public Animation<TextureRegion> running;
	public Animation<TextureRegion> jumping;
	public Animation<TextureRegion> standing;
	public Animation<TextureRegion> crouch;
	public Animation<TextureRegion> attack;
	
	private double CrouchCoolDown = 1000;
	private float AttackCoolDown = 2000;
	private double lastCrouchTime = 0;
	private float lastAttackTime = 0;
	
	private float stateTime;
	private boolean runningRight = true;
	private float normalRadius;
	private float crouchRadius;
	private boolean CanCrouch = true;
	
	
	public Hero(World world, PlayScreen screen) {		
		Gdx.input.setInputProcessor(new InputHandler(this));
		this.world = world;
		//setRegion(getTexture());
		
		prepareAnimation();
		defineHero();
		setBounds(0, 0, getRegionWidth()/Drop.PPM, getRegionHeight()/Drop.PPM);
		currentState = State.STANDING;
		previousState = State.STANDING;
		
	}
	
	public void prepareAnimation() {
		atlasAttack = new TextureAtlas("PNG/Sprites/hero/HeroAttack.pack");
		atlasRunning = new TextureAtlas("PNG/Sprites/hero/HeroRun.pack");
		atlasJumping = new TextureAtlas("PNG/Sprites/hero/HeroJump.pack");
		atlasStanding = new TextureAtlas("PNG/Sprites/hero/HeroIdle.pack");
		atlasCrouch = new TextureAtlas("PNG/Sprites/hero/HeroCrouch.pack");
		attack = new Animation<TextureRegion>(0.05f, atlasAttack.getRegions());
		running = new Animation<TextureRegion>(0.1f, atlasRunning.getRegions());
		jumping = new Animation<TextureRegion>(0.05f, atlasJumping.getRegions());
		crouch =  new Animation<TextureRegion>(0.05f, atlasCrouch.getRegions());
		standing = new Animation<TextureRegion>(0.5f, atlasStanding.getRegions());
		setRegion(atlasStanding.findRegion("hero-idle-1"));
		normalRadius = atlasAttack.findRegion("hero-attack-1").getRegionHeight()/Drop.PPM/2;
		crouchRadius = atlasCrouch.findRegion("hero-crouch").getRegionHeight()/Drop.PPM/2;
	}
	
	private void handleInput(float dt) {
		Vector2 vel = b2body.getLinearVelocity();
		boolean stop = true;
		
		double CrTime = System.currentTimeMillis();
		//System.out.println(CrTime+" "+lastCrouchTime+" "+(CrTime-lastCrouchTime));
		if(CrTime-lastCrouchTime >= CrouchCoolDown && vel.y == 0) {CanCrouch = true; }
		else CanCrouch = false;
     
		
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
		
		if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.D)) {
			b2body.setLinearVelocity( new Vector2(0,vel.y));
		}
		if(Gdx.input.isKeyPressed(Keys.S) && CanCrouch) {
			b2body.setLinearVelocity( new Vector2(0,vel.y)); 
		}
		
		if(stop) b2body.setLinearVelocity( new Vector2(0,vel.y));
		
		
	}
	public void update(float dt) {
		handleInput(dt);
		setBounds(b2body.getPosition().x-getRegionWidth()/Drop.PPM/2,
				b2body.getPosition().y-getRegionHeight()/Drop.PPM/2,
				getRegionWidth()/Drop.PPM, 
				getRegionHeight()/Drop.PPM);
		
		
		setRegion(getFrame(dt));
	}
	
	
	private TextureRegion getFrame(float dt) {
		
		currentState = getFrameState();
		TextureRegion region;
		switch (currentState) {
		    case JUMPING:
		        region = jumping.getKeyFrame(stateTime, false);
		        break;
		    case RUNNING:
		    	
		        region = running.getKeyFrame(stateTime, true);
		        break;
		    case ATTACKING:
		    	
		    	region = attack.getKeyFrame(stateTime, false);
		    	break;
		    case CROUCHING:
		    	
		    	region = crouch.getKeyFrame(stateTime, true);
		    	break;
		    	
		    default:
		        region = standing.getKeyFrame(stateTime, true);
		        break;
		}
		
		

		// Adjust character orientation based on velocity
		float velocityX = b2body.getLinearVelocity().x;
		if ((velocityX < 0 || !runningRight) && !region.isFlipX()) {
		    region.flip(true, false);
		    runningRight = false;
		} else if ((velocityX > 0 || runningRight) && region.isFlipX()) {
		    region.flip(true, false);
		    runningRight = true;
		} else if (velocityX == 0 && runningRight && region.isFlipX()) {
		    region.flip(true, false);
		}

		// Update state time
		stateTime = (currentState == previousState ? stateTime + dt : 0);
		previousState = currentState;

		return region;

	}

	private State getFrameState() {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyPressed(Keys.J)) return State.ATTACKING;
		if(b2body.getLinearVelocity().y != 0) return State.JUMPING;
		if(b2body.getLinearVelocity().x != 0) return State.RUNNING;
		if(Gdx.input.isKeyPressed(Keys.S) && CanCrouch) return State.CROUCHING;
		return State.STANDING;
		
	}

	private void defineHero() {
		// TODO Auto-generated method stub
		 bdef.position.set(0,10);
		 bdef.type = BodyDef.BodyType.DynamicBody;
		 b2body = world.createBody(bdef);
		 shape.setRadius(getRegionHeight()/Drop.PPM/2);
		 fdef.shape = shape;
		 normalDef = b2body.createFixture(fdef);
		 
	}
	
	 private  class InputHandler implements com.badlogic.gdx.InputProcessor {
		 
		 	private final Hero hero; 
		 	public InputHandler(Hero hero) {
				this.hero = hero;
			}

			@Override
	        public boolean keyDown(int keycode) {
				
				switch (keycode) {
                case Keys.S:
                	if(CanCrouch) {
                		b2body.setTransform(new Vector2(b2body.getPosition().x,b2body.getPosition().y-getRegionHeight()/Drop.PPM/2+crouchRadius), 0);
                		b2body.getFixtureList().first().getShape().setRadius(crouchRadius); 
                		break;
                	}
			}
			return false;
	                
	        }

			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
				switch (keycode) {
	                case Keys.S:
	                	lastCrouchTime = System.currentTimeMillis();
	                	System.out.println(b2body.getPosition().y);
	                	System.out.println(getRegionHeight()/Drop.PPM/2);
	                	System.out.println(b2body.getPosition().y-getRegionHeight()/Drop.PPM/2+normalRadius);
                		b2body.setTransform(new Vector2(b2body.getPosition().x,b2body.getPosition().y-getRegionHeight()/Drop.PPM/2+normalRadius), 0);
                		b2body.getFixtureList().first().getShape().setRadius(normalRadius); 
                		break;	
				}
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				// TODO Auto-generated method stub
				return false;
			}
	 }
	
}	
