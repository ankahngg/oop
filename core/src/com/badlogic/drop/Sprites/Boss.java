package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Boss extends Monster {
	public Texture BossHealthBar;
	public Texture BossHealth;

	public Boss(World world, PlayScreen screen, float x, float y, int maxHealth, boolean isDynamic,boolean isSensor) {
		super(world, screen, x, y, maxHealth, isDynamic,isSensor);
		// TODO Auto-generated constructor stub
		Collision.setCategoryFilter(monsterDef, Collision.MONSTER_BITS,null);

	}
//	public abstract void removeMonster();
//	public abstract void movement();
//	public abstract State getFrameState(float dt) ;
	
}
