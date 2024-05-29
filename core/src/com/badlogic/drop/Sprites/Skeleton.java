package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Skeleton extends Monster{
	float posXMonster;
	float posXHero;
	float widthMonster;
	float widthHero ;
	private float posYHero;
	private float posYMonster;
	
	
	
	public void prepareAnimation() {
		atlasAttack1 = new TextureAtlas("Monster/SkeletonV2/Attack.pack");
//		atlasAttack2 = new TextureAtlas("Boss/packs/BossAttack2.pack");
//		atlasAttack3 = new TextureAtlas("Boss/packs/BossAttack3.pack");
//		atlasStanding = new TextureAtlas("Boss/packs/BossIdle.pack");
		atlasRunning = new TextureAtlas("Monster/SkeletonV2/Run.pack");
		atlasDie = new TextureAtlas("Monster/SkeletonV2/Die.pack");
		atlasHurt = new TextureAtlas("Monster/SkeletonV2/Hurt.pack");
		
		attack1 = new Animation<TextureRegion>(0.05f, atlasAttack1.getRegions());
//		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
//		attack3 = new Animation<TextureRegion>(0.1f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.1f, atlasRunning.getRegions());
//		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.1f, atlasHurt.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDie.getRegions());
		setRegion(atlasRunning.getRegions().get(1));
		this.MonsterScaleX = this.MonsterScaleY = 1.5f;
		isGravityScale = true;
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}
	
	public Skeleton(World world, PlayScreen screen,float x, float y,int maxHealth,boolean isDynamic, boolean isSensor) {		
		super(world, screen, x, y,maxHealth, isDynamic,isSensor);
		
		isIntialLeft = false;
		Collision.setCategoryFilter(monsterDef,Collision.MONSTER_BITS,(short) ((32767)-Collision.HERO_BITS));
		monsterDef.setUserData(this);
	}
	
	public void movement() {
		if(b2body == null) return;
		Vector2 vel = b2body.getLinearVelocity();
		if(!isHurting) {
			if(screen.getPlayer().getBody().getPosition().x-0.1 < b2body.getPosition().x
				&& 	screen.getPlayer().getBody().getPosition().x+0.1 > b2body.getPosition().x)
				b2body.setLinearVelocity(new Vector2(0,vel.y));
			else {
				if(screen.getPlayer().getBody().getPosition().x-0.1 > b2body.getPosition().x)
					b2body.setLinearVelocity(new Vector2(+4,vel.y));
				else if(screen.getPlayer().getBody().getPosition().x+0.1 < b2body.getPosition().x)
					b2body.setLinearVelocity(new Vector2(-4,vel.y));
			}
		}
	}
	
	double t = 1000;
	private boolean attacked;
	
	public void HurtKnockBack() {
		double crTime = System.currentTimeMillis();
		
		
		double t = 200;
		if(System.currentTimeMillis()-crTime <t) {
			if(screen.getPlayer().getBody().getPosition().x < b2body.getPosition().x)
				b2body.applyLinearImpulse(new Vector2(15,0), b2body.getWorldCenter(),true);
			else 
				b2body.applyLinearImpulse(new Vector2(-15,0), b2body.getWorldCenter(),true);		
		}
		
	}
	
	public State getFrameState(float dt) {
		if(b2body != null) {
			posYHero = screen.getPlayer().body.getPosition().y;
			posYMonster = b2body.getPosition().y;
			posXMonster = b2body.getPosition().x;
			posXHero = screen.getPlayer().body.getPosition().x;
			widthMonster = getRegionWidth()/CuocChienSinhTon.PPM;
			widthHero = screen.getPlayer().getRegionWidth()/CuocChienSinhTon.PPM;
		}
		
		
		if(isDie) {
			isAttacking1 = false;
			isDieing = true;
			isDie = false;
			isHurt = false;
			isHurting = false;
			world.destroyBody(b2body);
			b2body = null;
			
			
			return State.DIE;
		}
		if(isHurt) {
			isAttacking1 = false;
			isHurting = true;
			isHurt = false;
			stateTime = 0;
			HurtKnockBack();
			return State.HURT;
		}
		
		if(isHurting) {

			if(!hurt.isAnimationFinished(stateTime)) return State.HURT;
			else isHurting = false;
		}
		
		if(isDieing) {
			if(!die.isAnimationFinished(stateTime)) return State.DIE;
			else {
				isDieing = false;
				removeMonster();
				isDied = true;
			}
		}
		
		
		
		if(isAttacking1) {
			if(!attack1.isAnimationFinished(stateTime)) {
				
				if(attack1.getKeyFrameIndex(stateTime) == 7 && !attacked) {
					if(posXHero >= posXMonster-widthMonster/2-widthHero 
						&& posXHero <= posXMonster+widthMonster/2+widthHero
						&& Math.abs(posYHero-posYMonster) <= 3d) {
						screen.getPlayer().handleHurt(monsterDef);
						attacked = true;
					}
				}
				return State.ATTACKING1;
			}
			else {
				isAttacking1 = false;
				attacked = false;
				lastAttackTime = System.currentTimeMillis();
			}
		}
		
			
		
		if(posXHero >= posXMonster-widthMonster/2-widthHero 
			&& posXHero <= posXMonster+widthMonster/2+widthHero
			&& Math.abs(posYHero-posYMonster) <= 3 ) {
			if(System.currentTimeMillis()-lastAttackTime > attackCd) {
				isAttacking1 = true;

				return State.ATTACKING1;				
			}
		}
		return State.RUNNING;
	}
	
	
	
}
