package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Monster.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Boss1 extends Boss{
	
	
	public void prepareAnimation() {
		atlasAttack1 = new TextureAtlas("Boss/packs/BossAttack1.pack");
		atlasAttack2 = new TextureAtlas("Boss/packs/BossAttack2.pack");
		atlasAttack3 = new TextureAtlas("Boss/packs/BossAttack3.pack");
		atlasStanding = new TextureAtlas("Boss/packs/BossIdle.pack");
		atlasRunning = new TextureAtlas("Boss/packs/BossRun.pack");
		atlasDie = new TextureAtlas("Boss/packs/BossDie.pack");
		atlasHurt = new TextureAtlas("Boss/packs/BossHurt.pack");
		
		attack1 = new Animation<TextureRegion>(0.01f, atlasAttack1.getRegions());
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
	
	public Boss1(World world, PlayScreen screen, float x, float y) {		
		super(world, screen,x,y,20,false);
		monsterDef.setUserData(this);
		
	}
	

	public void removeMonster() {
		((FirstMap) screen).StageCreator.bossesRemove.add(this);
	}
	public void movement() {
		
		if(b2body != null) {
			
			Vector2 po = b2body.getPosition();
			float poy;
			if(System.currentTimeMillis() - lastTeleTime >= TeleCd) {
				if(rd.nextInt(2) == 0) {
					poy = Math.max(po.y-rd.nextInt(15), 3);
				}
				else poy = Math.min(po.y+rd.nextInt(15), 18);
				
				b2body.setTransform(new Vector2(po.x,poy), 0);			
				lastTeleTime = System.currentTimeMillis();
			}	
		}
	}
	
	
	double t = 2000;
	boolean isAttacking = false;
	boolean isHurting = false;
	
	public State getFrameState(float dt) {
		
		double currentTime = System.currentTimeMillis();
		
		
		
		//System.out.println(currentTime - lastAttackTime);
		if(isDieing) {
			if(!die.isAnimationFinished(stateTime)) return State.DIE;
			else {
				isDieing = false;
				removeMonster();
				isDied = true;
				screen.game.setScreen(new FlappyMap(screen.game));
			}
		}
		if(isAttacking) {
			
			isHurting = false;
			if(!attack1.isAnimationFinished(stateTime)) return State.ATTACKING1;
			else {
				int rnd = (rd.nextInt(5));
				if(rnd == 4) {
					BulletManage.addBullet("BossBullet1", b2body.getPosition().x, b2body.getPosition().y, -1);
				}
				BulletManage.addBullet("BossBullet2", b2body.getPosition().x, b2body.getPosition().y, -1);
//				t = (rd.nextInt(5)+1)*1000;
				lastAttackTime = System.currentTimeMillis();
				isAttacking = false;
			}
		}
		if(currentTime - lastAttackTime >= t) {
			System.out.println("attack");
			isAttacking = true;
			return State.ATTACKING1;
		}
		
		if(isHurting) {

			if(!hurt.isAnimationFinished(stateTime)) return State.HURT;
			else isHurting = false;
		}
		
		if(isDie) {
			isAttacking1 = false;
			isDieing = true;
			isDie = false;
			isHurt = false;
			isHurting = false;
			world.destroyBody(this.b2body);
			b2body = null;
			return State.DIE;
		}
		if(isHurt) {
			isAttacking1 = false;
			isHurting = true;
			isHurt = false;
			stateTime = 0;
			return State.HURT;
		}
		if(isHurting) {
			if(!hurt.isAnimationFinished(stateTime)) return State.HURT;
			else isHurting = false;
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
		
		this.Health -= screen.getPlayer().damage;
		if(this.Health <= 0) {
			isDie = true;
		}
		else {
			isHurt = true;
		}
	}
	

	
	
	
	
}
