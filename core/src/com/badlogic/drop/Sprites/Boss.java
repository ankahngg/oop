package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Boss extends Monster {

	public Boss(World world, PlayScreen screen, float x, float y, int maxHealth, boolean isDynamic) {
		super(world, screen, x, y, maxHealth, isDynamic);
		// TODO Auto-generated constructor stub
		Collision.setCategoryFilter(monsterDef, Collision.MONSTER_BITS,null);
	}
	public abstract void removeMonster();
	public abstract void movement();
	public abstract State getFrameState(float dt) ;
	
}
