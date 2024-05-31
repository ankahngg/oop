package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Monster.State;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class HellBeast extends Monster{
	
	public double BulletCd = 2000;
	public double lastFire = 0;
	public EyeBullet bullet;
	public int getHealthMax() {
		return this.HealthMax;
	}
	public int getHealth() {
		return Health;
	}
	
	public void prepareAnimation() {
		atlasAttack1 = new TextureAtlas("Monster/HellBeast/Breath.pack");
		atlasAttack2 = new TextureAtlas("Monster/HellBeast/Burn.pack");
//		atlasAttack3 = new TextureAtlas("Boss/packs/BossAttack3.pack");
//		atlasStanding = new TextureAtlas("Boss/packs/BossIdle.pack");
		atlasRunning = new TextureAtlas("Monster/HellBeast/Idle.pack");
		atlasDie = new TextureAtlas("Monster/Skeleton/Die.pack");
		atlasHurt = new TextureAtlas("Monster/HellBeast/Hurt.pack");
		
		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
//		attack3 = new Animation<TextureRegion>(0.1f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.1f, atlasRunning.getRegions());
//		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.4f, atlasHurt.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDie.getRegions());
		setRegion(atlasRunning.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}
	
	public HellBeast(World world, PlayScreen screen,float x, float y,int maxHealth,boolean isDynamic, boolean isSensor) {		
		super(world, screen, x, y,maxHealth, isDynamic,isSensor);
		
		
		isIntialLeft = true;
		
		monsterDef.setUserData(this);
	}
	
	public void movement() {
		
	}
	
	double t = 1000;
	
	@Override
	
	public State getFrameState(float dt) {
		if(isAttacking1) {
			if(!attack1.isAnimationFinished(stateTime)) return State.ATTACKING1;
			else {
				isAttacking1 = false;
				float angle = bulletManage.getAngle(posX, posY, screen.getPlayer().body.getPosition().x, screen.getPlayer().body.getPosition().y);
				System.out.println(angle);
				bulletManage.addBullet("FireBullet", posX, posY, -1,20,angle,-1);
				lastFire = System.currentTimeMillis();
			}
		}
		if(isHurting) {
			if(!hurt.isAnimationFinished(stateTime)) return State.HURT;
			else isHurting = false;
		}
		if(isDieing) {
			if(!die.isAnimationFinished(stateTime)) return State.DIE;
			else {
				isDieing = false;
				isDieFinish = true;
				stageCreator.removeMonster(this);
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
		
		if( System.currentTimeMillis() - lastFire > BulletCd) {
			isAttacking1 = true;
			return State.ATTACKING1;
		}
		
		return State.RUNNING;
	}
	
}
