package com.badlogic.drop.Sprites;

import java.util.IllegalFormatFlagsException;
import java.util.Iterator;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Monster.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class FlyingEye extends Monster{
	public double cd = 2000;
	public double preTime;
	public EyeBullet bullet;
	public boolean isRunning = false;
	
	public void prepareAnimation() {
//		atlasAttack1 = new TextureAtlas("Boss/packs/BossAttack1.pack");
//		atlasAttack2 = new TextureAtlas("Boss/packs/BossAttack2.pack");
//		atlasAttack3 = new TextureAtlas("Boss/packs/BossAttack3.pack");
		atlasStanding = new TextureAtlas("Monster/FlyingEye/flying-eye.pack");
		atlasRunning = new TextureAtlas("Monster/FlyingEye/flying-eye.pack");
		atlasDie = new TextureAtlas("Monster/FlyingEye/Die.pack");
		atlasHurt = new TextureAtlas("Monster/FlyingEye/Hurting.pack");
		
//		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
//		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
//		attack3 = new Animation<TextureRegion>(0.1f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.2f, atlasRunning.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.1f, atlasHurt.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDie.getRegions());
		setRegion(atlasRunning.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}
	
	public FlyingEye(World world, PlayScreen screen,float x, float y) {		
		super(world, screen,x,y,false);
		this.Health = 2;
		
		
		isIntialLeft = true;
		
		monsterDef.setUserData(this);
		
	}
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		super.update(dt);
		
		if(isDied) return;
		if(running.isAnimationFinished(stateTime)) {
			BulletManage.addBullet("FlyingEye", b2body.getPosition().x, b2body.getPosition().y, -1);
		}
		
		
	}
	
	public void removeMonster() {
		world.destroyBody(b2body);

		b2body = null;
		if (screen instanceof FirstMap)
		((FirstMap) screen).StageCreator.eyeMonsters.remove(this);
		isDied = true;
	}
	
	public void movement() {

	}

	
	public State getFrameState(float dt) {
		
		if(isHurting) {
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
		
		double crTime = System.currentTimeMillis();
		
		if(isRunning) {
			if(running.isAnimationFinished(stateTime)) isRunning = false;
			else return State.RUNNING;
		}
		
		if(crTime-preTime > cd) {
			isRunning = true;
			preTime = crTime;
			return State.RUNNING;
		}
		else {
			stateTime = 0;
			return State.STANDING;
		}
		
	}
	
	void onHit() {
		//System.out.println("lol");
		this.Health --;
		if(this.Health == 0) {
			isDie = true;
		}
		else {
			isHurt = true;
		}
	}
	
	public void defineMonster(int x,int y) {
		
		super.defineMonster(x,y);
	}
	
}
