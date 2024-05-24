package com.badlogic.drop.Sprites;

import java.security.PublicKey;

import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Monster.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class DragonBallMonster1 extends Monster{
	public DragonBallMonster1(World world, PlayScreen screen,float x, float y) {
		super(world, screen, x, y, false);
		posX = x;
		posY = y;
		this.Health = 4;
		isIntialLeft = true;
		
		monsterDef.setUserData(this);
		Collision.setCategoryFilter(monsterDef,Collision.MONSTER_BITS,null);

	}
	@Override
	public void prepareAnimation() {
		// TODO Auto-generated method stub
		atlasStanding =new TextureAtlas("Monster/DragonballMonster1/running/49.atlas");
		atlasRunning =new TextureAtlas("Monster/DragonballMonster1/running/49.atlas");
		atlasDie =new TextureAtlas("Monster/DragonballMonster1/hurt/49.atlas");
		atlasHurt =new TextureAtlas("Monster/DragonballMonster1/hurt/49.atlas");
		atlasAttack1 = new TextureAtlas("Monster/DragonballMonster1/attacking/49.atlas");
		
		attack1 = new Animation<TextureRegion>(0.1f,atlasAttack1.getRegions());
		running = new Animation<TextureRegion>(0.2f,atlasRunning.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.1f, atlasHurt.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDie.getRegions());
		setRegion(atlasRunning.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}
	
	@Override
	public void removeMonster() {
		// TODO Auto-generated method stub
		if(b2body != null)
			world.destroyBody(b2body);
			b2body = null;
		isDied = true;
	}

	@Override
	public void movement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State getFrameState(float dt) {
		// TODO Auto-generated method stub
		if(isHurting) {
			if(!hurt.isAnimationFinished(stateTime)) return State.HURT;
			else isHurting =false;
		}
		if(isDieing) {
			if(!die.isAnimationFinished(stateTime)) return State.DIE;
			else {
				isDieing = false;
				
			}
		}
		if(isDie) {removeMonster();
			isDieing = true;
			isDie = false;
			return State.HURT;
		}
		if(isHurt) {
			isHurting = true;
			isHurt = false;
			return State.HURT;
		}
		float distance=(float) Math.sqrt(Math.pow(this.getX()-screen.getPlayer().getX(),2)+Math.pow(this.getY()-screen.getPlayer().getY(),2));
		
		if(distance<=screen.getSpeed()*1.5/((FlappyMap)screen).SPEED&&distance>=0) {
			return State.ATTACKING1;
			
		}
		return State.STANDING;
	}
	void onHit() {
		this.Health --;
		if(this.Health == 0) {
			isDie = true;
		}
		else {
			isHurt = true;
		}
	}
}
