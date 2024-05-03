package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Collision {

	public static boolean bossInRangeAttack = true;
	public static boolean startInstructionColi = false;
	public static final short GROUND_BITS = 9;
	public static final short HERO_BITS = 1;
	public static final short INSTRUCTION_BITS = 2;
	public static final short HEROATTACK_BITS = 3;
	public static final short BOSS_BITS = 4;
	public static final short SPINE_BITS = 5;
	public static final short SKELETON_BITS = 6;
	public static final short FLYINGEYE_BITS = 7;
	public static final short EYEBULLET_BITS = 8;
	public static final short MONSTERBOUND_BITS = 8;
	public static PlayScreen screen;
	
	public static void setup(PlayScreen x) {
		screen = x;
	}
	
	public static void setCategoryFilter(Fixture fixture, Short filterBit){
		  Filter filter = new Filter();
		  filter.categoryBits = filterBit;
		  fixture.setFilterData(filter);	
		}
	
	public static void heroAttack(PlayScreen screen) {
		if(bossInRangeAttack) screen.getBoss().isHurt = true;
	}
	public static void update(float dt) {
		if(startInstructionColi) B2WorldCreator.startInstruc.onHit();
	}
	public static void heroHurt(Contact contact) {
		
		Fixture x = getFix(Collision.HERO_BITS,contact);
		Fixture y = (contact.getFixtureA() == x ? contact.getFixtureB() : contact.getFixtureA());
		((AnKhangHero) x.getUserData()).handleHurt(y);
	}
	public static void monsterBound(Contact contact) {
		Fixture x = getFix(Collision.SKELETON_BITS,contact);
		((Skeleton) x.getUserData()).onWallCollision();
	}

	private static Fixture getFix(short z, Contact contact) {
		// TODO Auto-generated method stub
		Fixture x = contact.getFixtureA();
		Fixture y = contact.getFixtureB();
		if(x.getFilterData().categoryBits == z) return x;
		else return y;
	}
	
}
