package com.badlogic.drop.Tools;

import com.badlogic.gdx.physics.box2d.*;
import java.util.HashSet;
import java.util.Set;

public class WorldContactListener implements ContactListener {
    private Set<Contact> contacts;

    public WorldContactListener() {
        contacts = new HashSet<>();
    }

    @Override
    public void beginContact(Contact contact) {
        // Add the contact to the set when it begins
        contacts.add(contact);
       
    }

    @Override
    public void endContact(Contact contact) {
        // Remove the contact from the set when it ends
        //contacts.remove(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Called before collision resolution
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Called after collision resolution
    }

    // Method to check if two fixtures are currently in contact
    public boolean areFixturesInContact(String A, String B) {
    	
        for (Contact contact : contacts) {
            if ((contact.getFixtureA().getUserData() == A && contact.getFixtureB().getUserData() == B) ||
                (contact.getFixtureA().getUserData() == B && contact.getFixtureB().getUserData() == A)) {
                return true;
            }
        }
        return false;
    }
}
