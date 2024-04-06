package com.badlogic.drop.Tools;

import com.badlogic.drop.Sprites.InteractiveTileObject;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener{

	
	@Override
	public void beginContact(Contact contact) {
		System.out.println("begin");
		// TODO Auto-generated method stub
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
			
		if(fixA.getUserData() == "DamageRange" || fixB.getUserData() == "DamageRange") {
			Fixture head = fixA.getUserData() == "DamageRange" ? fixA : fixB;
			Fixture object = head == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				((InteractiveTileObject) object.getUserData()).onHit();
			}
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		//System.out.println("end");
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		//System.out.println("1");
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	
}
