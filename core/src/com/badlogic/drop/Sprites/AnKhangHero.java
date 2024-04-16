package com.badlogic.drop.Sprites;

import java.awt.Rectangle;
import java.awt.RenderingHints.Key;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

public class AnKhangHero extends Hero{
	
	
	
	public AnKhangHero(World world, FirstMap screen) {	
		super(world, screen);

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
		
		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
		attack3 = new Animation<TextureRegion>(0.1f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(1f, atlasRunning.getRegions());
		jumping = new Animation<TextureRegion>(0.1f, atlasJumping.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		HeroHeight = getRegionHeight();
	}
	
	

	public void detectCollison() {
		Array<Contact> contacts = world.getContactList();
		for (Contact contact : contacts) {
			if (contact.isTouching() && 
				((contact.getFixtureA() == screen.getBoss().bossDef && contact.getFixtureB() == attackFixture) ||
				(contact.getFixtureA() == attackFixture && contact.getFixtureB() == screen.getBoss().bossDef))) {
				screen.getBoss().isHurt = true;
			}
		}
	}
	
	public void instructionSensor() {
		Array<Contact> contacts = world.getContactList();
		for (Contact contact : contacts) {
			if (contact.isTouching() && 
				((contact.getFixtureA() == normalDef && contact.getFixtureB() == Middle.instruction.fixture) ||
				(contact.getFixtureA() == Middle.instruction.fixture && contact.getFixtureB() == normalDef))) {
				Middle.instruction.onHit();
			}
		}
	}
	
	public void update(float dt) {
		//System.out.println(world.getContactList());
		
		super.update(dt);
		attackFix();

		
		instructionSensor();
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
		 normalDef.setUserData("herobody");
	}
	

	
}	
