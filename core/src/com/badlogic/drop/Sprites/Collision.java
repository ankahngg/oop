package com.badlogic.drop.Sprites;

import java.util.ArrayList;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Tools.B2WorldCreator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Collision {

	public static boolean bossInRangeAttack = true;
	public static boolean InstructionColi = false;
	public static Instruction CrInstruction;
	public static final short GROUND_BITS = 1024;
	public static final short HERO_BITS = 1;
	public static final short INSTRUCTION_BITS = 2;
	public static final short HEROATTACK_BITS = 4;
	public static final short BOSS_BITS = 8;
	public static final short SPINE_BITS = 16;
	public static final short MONSTER_BITS = 32;
	public static final short FLYINGEYE_BITS = 64;
	public static final short MONSTERBULLET_BITS = 128;
	public static final short STAGEBOUND_BITS = 256;
	public static final short HEROBULLET_BITS = 512;
	public static PlayScreen screen;
	
	public static ArrayList<Monster> monsters = new ArrayList<Monster>();
	
	public static void setup(PlayScreen x) {
		screen = x;
	}
	
	public static void setCategoryFilter(Fixture fixture, Short filterBit,Short maskBit){
		  Filter filter = new Filter();
		  filter.categoryBits = filterBit;
		  if(maskBit != null) filter.maskBits = maskBit;
		  fixture.setFilterData(filter);	
		}
	
	public static void heroAttack(PlayScreen screen) {
		for(Monster m : monsters) {
			if(!m.isHurting)
			m.onHit();
		}
	}
	
	public static void monsterBulletHurt(Contact contact) {
		Fixture x = getFix(Collision.HEROBULLET_BITS,contact);
		Fixture y = (contact.getFixtureA() == x ? contact.getFixtureB() : contact.getFixtureA());
		if (x.getUserData()==null || y.getUserData() ==null) return;
		((Monster) y.getUserData()).onHit();
		((Bullet) x.getUserData()).onHit();
	}
	
	
	public static void instructionCollide(Contact contact) {
		Fixture x = getFix(Collision.HERO_BITS,contact);
		Fixture y = (contact.getFixtureA() == x ? contact.getFixtureB() : contact.getFixtureA());
		if (x.getUserData()==null || y.getUserData() ==null) return;
		CrInstruction = (Instruction) y.getUserData();	
	}
	public static void update(float dt) {
		if(InstructionColi) CrInstruction.onHit();
		
		
	}
	public static void heroBulletHurt(Contact contact) {
		
		Fixture x = getFix(Collision.HERO_BITS,contact);
		Fixture y = (contact.getFixtureA() == x ? contact.getFixtureB() : contact.getFixtureA());
		if (x.getUserData()==null || y.getUserData() ==null) return;
		((AnKhangHero) x.getUserData()).handleHurt(y);
		System.out.println("dcum");
		((Bullet) y.getUserData()).onHit();
	}
	public static void heroHurt(Contact contact) {
		Fixture x = getFix(Collision.HERO_BITS,contact);
		Fixture y = (contact.getFixtureA() == x ? contact.getFixtureB() : contact.getFixtureA());
		if (x.getUserData()==null || y.getUserData() == null) return;
		((AnKhangHero) x.getUserData()).handleHurt(y);
	}
	
	public static void heroCollideBound(Contact contact) {
		Fixture x = getFix(Collision.HERO_BITS,contact);
		Fixture y = (contact.getFixtureA() == x ? contact.getFixtureB() : contact.getFixtureA());
		StageBound bound = ((StageBound) y.getUserData());
		AnKhangHero hero = ((AnKhangHero) x.getUserData());
		
	}
	
	public static void monsterInRangeAttackAdd(Contact contact) {
		Fixture x = getFix(Collision.HEROATTACK_BITS,contact);
		Fixture y = (contact.getFixtureA() == x ? contact.getFixtureB() : contact.getFixtureA());
		monsters.add((Monster) y.getUserData());
	}
	public static void monsterInRangeAttackRemove(Contact contact) {
		Fixture x = getFix(Collision.HEROATTACK_BITS,contact);
		Fixture y = (contact.getFixtureA() == x ? contact.getFixtureB() : contact.getFixtureA());
		monsters.remove((Monster) y.getUserData());
	}
	public static void resetJump(Contact contact) {
		Fixture x = getFix(Collision.HERO_BITS,contact);
		Fixture y = (contact.getFixtureA() == x ? contact.getFixtureB() : contact.getFixtureA());
		if (screen instanceof FirstMap)
		 ((FirstMap) screen).canJump = true;
	}

	private static Fixture getFix(short z, Contact contact) {
		// TODO Auto-generated method stub
		Fixture x = contact.getFixtureA();
		Fixture y = contact.getFixtureB();
		if(x.getFilterData().categoryBits == z) return x;
		else return y;
	}
	
}
