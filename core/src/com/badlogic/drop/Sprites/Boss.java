package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Boss extends Monster {
	public boolean isHurting;
	public boolean isAttacking;
	public boolean isDieing;
	public boolean isDie;
	public boolean isFiring;
	public Boss(World world, PlayScreen screen, float x, float y, int maxHealth, boolean isDynamic,boolean isSensor) {
		super(world, screen, x, y, maxHealth, isDynamic,isSensor);
		// TODO Auto-generated constructor stub
		Collision.setCategoryFilter(monsterDef, Collision.MONSTER_BITS,null);

	}
//	public abstract void removeMonster();
//	public abstract void movement();
//	public abstract State getFrameState(float dt) ;
	
}
