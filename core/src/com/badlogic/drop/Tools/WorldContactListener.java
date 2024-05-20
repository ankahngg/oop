package com.badlogic.drop.Tools;

import javax.lang.model.type.NullType;

import com.badlogic.drop.Sprites.Collision;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
	public boolean isContact(short id1, short id2, Contact contact) {
		
		Fixture x = contact.getFixtureA();
		Fixture y = contact.getFixtureB();
		if (x==null||y==null) return false;
		short xx = x.getFilterData().categoryBits;
		short yy = y.getFilterData().categoryBits;	
		
		if(xx == id1 && yy == id2) return true;
		if(xx == id2 && yy == id1) return true;
		return false;
		
	}
    @Override
    public void beginContact(Contact contact) {
    	try {
    		// Add the contact to the set when it begins

			if(isContact(Collision.HERO_BITS,Collision.GROUND_BITS,contact)) Collision.resetJump(contact);
	        if(isContact(Collision.HERO_BITS,Collision.INSTRUCTION_BITS,contact)) Collision.startInstructionColi = true;
	        if(isContact(Collision.HERO_BITS,Collision.STAGEBOUND_BITS,contact)) Collision.heroCollideBound(contact);
	        if(isContact(Collision.HEROATTACK_BITS,Collision.BOSS_BITS,contact)) Collision.bossInRangeAttack = true;
	        if(isContact(Collision.MONSTERBULLET_BITS,Collision.HERO_BITS,contact)) Collision.heroBulletHurt(contact);
	        if(isContact(Collision.SPINE_BITS,Collision.HERO_BITS,contact)) Collision.heroHurt(contact);
	        if(isContact(Collision.MONSTERBULLET_BITS,Collision.HERO_BITS, contact)) Collision.heroHurt(contact);
	      
	        if(isContact(Collision.MONSTER_BITS,Collision.HERO_BITS, contact)) {
	        	Collision.heroHurt(contact);
	        }
	        if(isContact(Collision.MONSTER_BITS,Collision.HEROATTACK_BITS, contact)) Collision.monsterInRangeAttackAdd(contact);
	    
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

    @Override
    public void endContact(Contact contact) {
        // Remove the contact from the set when it ends
        //contacts.remove(contact);
    	try {
			if(isContact(Collision.HERO_BITS,Collision.INSTRUCTION_BITS,contact)) Collision.startInstructionColi = false;
    	 if(isContact(Collision.HEROATTACK_BITS,Collision.BOSS_BITS,contact)) Collision.bossInRangeAttack = false;
    	 if(isContact(Collision.MONSTER_BITS,Collision.HEROATTACK_BITS, contact)) Collision.monsterInRangeAttackRemove(contact);
    	
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Called before collision resolution
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Called after collision resolution
    }

    
 
}
