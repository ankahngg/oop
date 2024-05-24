package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Boss extends Monster{
	
	public int getHealthMax() {
		return this.HealthMax;
	}
	public int getHealth() {
		return Health;
	}
	
	public void prepareAnimation() {
		atlasAttack1 = new TextureAtlas("Boss/packs/BossAttack1.pack");
		atlasAttack2 = new TextureAtlas("Boss/packs/BossAttack2.pack");
		atlasAttack3 = new TextureAtlas("Boss/packs/BossAttack3.pack");
		atlasStanding = new TextureAtlas("Boss/packs/BossIdle.pack");
		atlasRunning = new TextureAtlas("Boss/packs/BossRun.pack");
		atlasDie = new TextureAtlas("Boss/packs/BossDie.pack");
		atlasHurt = new TextureAtlas("Boss/packs/BossHurt.pack");
		
		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
		attack3 = new Animation<TextureRegion>(0.1f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.1f, atlasRunning.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.1f, atlasHurt.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDie.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
		
	}
	
	public Boss(World world, PlayScreen screen, float x, float y) {		
		super(world, screen,x,y,true);
		Collision.setCategoryFilter(monsterDef, Collision.BOSS_BITS,null);
	}
	
	@Override
	public void removeMonster() {
		// TODO Auto-generated method stub
		
	}
	
	public void movement() {
		
//		Vector2 po = b2body.getPosition();
//		float pox;
//		if(System.currentTimeMillis() - lastTeleTime >= TeleCd) {
//			if(rd.nextInt(2) == 0) {
//				pox = po.x - rd.nextInt(5);
//			}
//			else pox = po.x + rd.nextInt(5);
//			
//			b2body.setTransform(new Vector2(pox,po.y), 0);			
//			lastTeleTime = System.currentTimeMillis();
//		}	
	}
	
	
	double t = 1000;
	boolean isAttacking = false;
	boolean isHurting = false;
	
	public State getFrameState(float dt) {
		
		double currentTime = System.currentTimeMillis();
		
		if(isAttacking) {
			isHurt = false;
			if(!attack1.isAnimationFinished(stateTime)) return State.ATTACKING1;
			else {
				t = (rd.nextInt(5)+1)*1000;
				lastAttackTime = System.currentTimeMillis();
				isAttacking = false;
			}
		}
		if(isHurting) {
			if(!hurt.isAnimationFinished(stateTime)) return State.HURT;
			else isHurting = false;
		}
		
		if(currentTime - lastAttackTime >= t) {
			isAttacking = true;
			return State.ATTACKING1;
		}
		if(isHurt && !isAttacking) {
			onHit();
			isHurting = true;
			isHurt = false;
			return State.HURT;
		}
		return State.STANDING;
	}
	
	void onHit() {
		this.Health --;
	}
	
}
