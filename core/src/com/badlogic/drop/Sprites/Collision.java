package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Collision {

	public static boolean bossInRangeAttack = true;
	public static boolean startInstructionColi = false;
	public static final short HERO_BITS = 1;
	public static final short INSTRUCTION_BITS = 2;
	public static final short HEROATTACK_BITS = 3;
	public static final short BOSS_BITS = 4;
	public static final short SPINE_BITS = 5;
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
	public static void render() {
		if(startInstructionColi) B2WorldCreator.startInstruc.onHit();
	}
	public static void heroHurt(Contact contact) {
		
		Fixture x = getFix(Collision.SPINE_BITS,contact);
		screen.getPlayer().handleHurt(x);
	}

	private static Fixture getFix(short z, Contact contact) {
		// TODO Auto-generated method stub
		Fixture x = contact.getFixtureA();
		Fixture y = contact.getFixtureB();
		if(x.getFilterData().categoryBits == z) return x;
		else return y;
	}
	
}
