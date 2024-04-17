package com.badlogic.drop.Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Hero.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer.Random;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Boss extends Sprite{
	public enum State {FALLING,JUMPING,STANDING,RUNNING,ATTACKING1,ATTACKING2,ATTACKING3,DIE, HURT};
	private State currentState ,previousState;
	public World world;
	private Animation<TextureRegion> jumping;
	private TextureAtlas atlasAttack1;
	private TextureAtlas atlasAttack2;
	private TextureAtlas atlasAttack3;
	private TextureAtlas atlasStanding;
	private TextureAtlas atlasRunning;
	private TextureAtlas atlasDie;
	private TextureAtlas atlasHurt;
	private Animation<TextureRegion> attack1;
	private Animation<TextureRegion> attack2;
	private Animation<TextureRegion> attack3;
	private Animation<TextureRegion> running;
	private Animation<TextureRegion> standing;
	private Animation<TextureRegion> die;
	private Animation<TextureRegion> hurt;
	public float stateTime;
	java.util.Random rd = new java.util.Random();
	
	public double lastAttackTime = 0;
	public double lastTeleTime = 0;
	public double TeleCd = 50;
	public Body b2body;
	public Body hitbox;
	BodyDef bdef = new BodyDef();
	FixtureDef fdef = new FixtureDef();
	public Fixture bossDef;
	private Fixture hitboxDef;
	
	public boolean isHurt = false;
	private int BossHeight;
	private int BossWidth;
	
	public void prepareAnimation() {
		atlasAttack1 = new TextureAtlas("Boss/packs/BossAttack1.pack");
		atlasAttack2 = new TextureAtlas("Boss/packs/BossAttack2.pack");
		atlasAttack3 = new TextureAtlas("Boss/packs/BossAttack3.pack");
		atlasStanding = new TextureAtlas("Boss/packs/BossIdle.pack");
		atlasRunning = new TextureAtlas("Boss/packs/BossRun.pack");
		atlasDie = new TextureAtlas("Boss/packs/BossDie.pack");
		atlasHurt = new TextureAtlas("Boss/packs/BossHurt.pack");
		
		
		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
		attack3 = new Animation<TextureRegion>(0.1f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.1f, atlasRunning.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.1f, atlasHurt.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDie.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		BossHeight = getRegionHeight();
		BossWidth = getRegionWidth();
	}
	
	public Boss(World world, PlayScreen screen) {		
			
		this.world = world;
		prepareAnimation();
		defineBoss();
		setBounds(0, 0, getRegionWidth()/Drop.PPM, getRegionHeight()/Drop.PPM);
	}
	
	public void update(float dt) {
		setRegion(getFrame(dt));
		setBounds(b2body.getPosition().x-BossWidth/Drop.PPM/2,
				b2body.getPosition().y-BossHeight/Drop.PPM/2,
				getRegionWidth()/Drop.PPM,
				getRegionHeight()/Drop.PPM);
		
		//updateMovement();
	}
	
	private void updateMovement() {
		
		Vector2 po = b2body.getPosition();
		float pox;
		if(System.currentTimeMillis() - lastTeleTime >= TeleCd) {
			if(rd.nextInt(2) == 0) {
				pox = po.x - rd.nextInt(5);
			}
			else pox = po.x + rd.nextInt(5);
			
			b2body.setTransform(new Vector2(pox,po.y), 0);			
			lastTeleTime = System.currentTimeMillis();
		}
		
	}
	
	private TextureRegion getFrame(float dt) {
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
		
		
		// Update state time
		
		previousState = currentState;
		

		return region;
	}
	
	double t = 1000;
	boolean isAttacking = false;
	boolean isHurting = false;
	
	private State getFrameState(float dt) {
		
		double currentTime = System.currentTimeMillis();
		
		if(isAttacking) {
			isHurt = false;
			if(!attack1.isAnimationFinished(stateTime)) return State.ATTACKING1;
			else {
				t = (rd.nextInt(5)+1)*1000;
				lastAttackTime = System.currentTimeMillis();
				isAttacking = false;
			}
		}
		if(isHurting) {
			if(!hurt.isAnimationFinished(stateTime)) return State.HURT;
			else isHurting = false;
		}
		
		if(currentTime - lastAttackTime >= t) {
			isAttacking = true;
			return State.ATTACKING1;
		}
		if(isHurt && !isAttacking) {
			onHit();
			isHurting = true;
			isHurt = false;
			return State.HURT;
		}
		
		return State.STANDING;
	}
	
	void onHit() {
		System.out.println("-10 mau");
	}
	
	private void defineBoss() {
		CircleShape shape = new CircleShape();
		 bdef.position.set(35,2);
		 bdef.type = BodyDef.BodyType.DynamicBody;
		 b2body = world.createBody(bdef);
		 shape.setRadius(getRegionHeight()/Drop.PPM/2);
		 fdef.shape = shape;
		 bossDef = b2body.createFixture(fdef);
		 Collision.setCategoryFilter(bossDef, Collision.BOSS_BITS);
		  
	}
}
