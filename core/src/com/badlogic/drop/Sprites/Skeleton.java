package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Skeleton extends Monster{
	public int getHealthMax() {
		return this.HealthMax;
	}
	public int getHealth() {
		return Health;
	}
	
	public void prepareAnimation() {
//		atlasAttack1 = new TextureAtlas("Boss/packs/BossAttack1.pack");
//		atlasAttack2 = new TextureAtlas("Boss/packs/BossAttack2.pack");
//		atlasAttack3 = new TextureAtlas("Boss/packs/BossAttack3.pack");
//		atlasStanding = new TextureAtlas("Boss/packs/BossIdle.pack");
		atlasRunning = new TextureAtlas("Monster/Skeleton/Skeleton.pack");
		atlasDie = new TextureAtlas("Monster/Skeleton/Die.pack");
		atlasHurt = new TextureAtlas("Monster/Skeleton/Hurting.pack");
		
//		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
//		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
//		attack3 = new Animation<TextureRegion>(0.1f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.1f, atlasRunning.getRegions());
//		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.4f, atlasHurt.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDie.getRegions());
		setRegion(atlasRunning.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}
	
	public Skeleton(World world, PlayScreen screen,int x, int y) {		
		super(world, screen,x,y,true);
		this.Health = 2;
		isIntialLeft = true;
		monsterDef.setUserData(this);
	}
	
	public void movement() {
		Vector2 vel = b2body.getLinearVelocity();
		if(!isHurting) {
			if(screen.getPlayer().getBody().getPosition().x < b2body.getPosition().x)
				b2body.setLinearVelocity(new Vector2(-2,vel.y));
			else 
				b2body.setLinearVelocity(new Vector2(2,vel.y));			
		}
	}
	
	double t = 1000;
	
	@Override
	public void removeMonster() {
		world.destroyBody(this.b2body);
		b2body = null;
		((FirstMap) screen).StageCreator.skeMonsters.remove(this);
		isDied = true;
	}
	
	public State getFrameState(float dt) {
		if(isHurting) {
			
			if(stateTime < hurt.getAnimationDuration()/10) {
				if(screen.getPlayer().getBody().getPosition().x < b2body.getPosition().x)
					b2body.applyLinearImpulse(new Vector2(5,5), b2body.getWorldCenter(),true);
				else 
					b2body.applyLinearImpulse(new Vector2(-5,5), b2body.getWorldCenter(),true);				
			}
			if(!hurt.isAnimationFinished(stateTime)) return State.HURT;
			else isHurting = false;
		}
		if(isDieing) {
			if(!die.isAnimationFinished(stateTime)) return State.DIE;
			else {
				isDieing = false;
				removeMonster();
			}
		}
		if(isDie) {
			isDieing = true;
			isDie = false;
			return State.DIE;
		}
		if(isHurt) {
			isHurting = true;
			isHurt = false;
			return State.HURT;
		}
		return State.RUNNING;
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
