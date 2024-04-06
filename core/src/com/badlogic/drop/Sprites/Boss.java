package com.badlogic.drop.Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Hero.State;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Boss extends Sprite{
	public World world;
	public Body b2body;
	public Body hitbox;
	
	public Boss(World world, PlayScreen screen) {		
			
		this.world = world;
		//setBounds(0, 0, getRegionWidth()/Drop.PPM, getRegionHeight()/Drop.PPM);
		
			
			
	}
}
