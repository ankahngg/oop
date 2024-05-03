package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
//		atlasDie = new TextureAtlas("Boss/packs/BossDie.pack");
//		atlasHurt = new TextureAtlas("Boss/packs/BossHurt.pack");
		
//		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
//		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
//		attack3 = new Animation<TextureRegion>(0.1f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.15f, atlasRunning.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
//		hurt = new Animation<TextureRegion>(0.1f, atlasHurt.getRegions());
//		die = new Animation<TextureRegion>(0.1f, atlasDie.getRegions());
		setRegion(atlasRunning.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}
	
	public FlyingEye(World world, FirstMap screen,int x, int y) {		
		super(world, screen,x,y,false);
		bullet = new EyeBullet(world, screen, x, y, 0);
		isIntialLeft = true;
		Collision.setCategoryFilter(monsterDef, Collision.FLYINGEYE_BITS);
	}
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		super.update(dt);
		
		if(running.isAnimationFinished(stateTime)) {
			bullet.launch(dt);
		}
		bullet.update(dt);
		
		
		//bullet.update(dt);
	}
	
	public void Movement() {
		Vector2 vel = b2body.getLinearVelocity();
		b2body.setLinearVelocity(new Vector2(0,vel.y));
		
	}

	
	public State getFrameState(float dt) {
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
		this.Health --;
	}
	
}
