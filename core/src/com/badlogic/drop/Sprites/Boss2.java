package com.badlogic.drop.Sprites;

import java.util.Currency;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Hero.State;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

public class Boss2 extends Boss{
	int knightHeath = 200;
	TextureAtlas disappearAtlas;
	TextureAtlas appearAtlas;
	Animation<TextureRegion> disappear;
	Animation<TextureRegion> appear;
	
	private double disapearCd = 20000;
	private double disappearTime = 5000; // visble time (s)
   
    private boolean isDisapear = false;
    private boolean isDisapearing = false;
    float dt;
	
	
    private int maxBulletsPerAttack = 50; // Giới hạn số lượng đạn mỗi lần tấn công
    private int currentBulletCount = 0; // Đếm số lượng đạn đã bắn trong một lần tấn công
	private double disappearTimeBegin;
	private double appearTimeBegin = System.currentTimeMillis();
	private double appearTime = 20000;
	private boolean isAppearing;
	private Aura bossAura;
    
	public Boss2(World world, PlayScreen screen,float x, float y,int maxHealth,boolean isDynamic, boolean isSensor) {		
		super(world, screen, x, y,maxHealth, isDynamic,isSensor);
		bossAura = new Aura(world, screen,this.getWidth());
		monsterDef.setUserData(this);
		lastAttackTime = System.currentTimeMillis();
		attackCd = 2000;
	}

	@Override
	public State getFrameState(float dt) {
		// TODO Auto-generated method stub
		if(isDisapearing) {
			if(!disappear.isAnimationFinished(stateTime)) return State.DISAPPEARED;
			else {
				isDisapearing = false;
				b2body.setTransform(33,-10f, 0);
				disappearTimeBegin = System.currentTimeMillis();
			}
		}
		if(isAppearing) {
			if(!appear.isAnimationFinished(stateTime)) return State.APPEARED;
			else {
				isAppearing = false;
				appearTimeBegin = System.currentTimeMillis();
				 float newY = MathUtils.random(1,18);
				 b2body.setTransform(33, newY , 0);
			}
		}
		if(!isVisible) {
			((FlappyMap)screen).spawnMonsters();
		}
		
		if(isVisible) {
			if(isDieing) {
				if(!die.isAnimationFinished(stateTime)) {
					return State.DIE;
				}
				else {
					isDieing = false;
					stageCreator.removeMonster(this);
				}
			}
			if(isAttacking1) {
				
				if(!attack1.isAnimationFinished(stateTime)) return State.ATTACKING1;
				else {
					isAttacking1 = false;
					lastAttackTime = System.currentTimeMillis();
				}
			}
			
			if(isAttacking2) {
				if(!attack2.isAnimationFinished(stateTime)) return State.ATTACKING2;
				else {
					bulletManage.addBullet("BossBullet1", b2body.getPosition().x, b2body.getPosition().y, -1);
					lastAttackTime = System.currentTimeMillis();
					isAttacking2 = false;
				}
			}
			if(isHurting) {
				if(!hurt.isAnimationFinished(stateTime)) {
					return State.HURT;
				}
				else isHurting = false;
			}
			
			
			if(isHurt) {
				isHurting = true;
				isHurt = false;
				return State.HURT;
			}
			if(isDie) {
				isDieing = true;
				isDie = false;
				return State.DIE;
			}
			
			if(System.currentTimeMillis()-lastAttackTime > attackCd) {
				int rnd = MathUtils.random(2);
				int rnd2 = MathUtils.random(1);
				
				if(rnd==1) {
					isAttacking1=true;
					if(rnd2 == 1) ((FlappyMap)screen).spawnMonsters();
					return State.ATTACKING1;
				}
				else if(rnd==2){
					isAttacking2=true;
					if(rnd2 == 1) ((FlappyMap)screen).spawnMonsters();
					return State.ATTACKING2;
				}
				
			}
		}
		
		if(System.currentTimeMillis() - appearTimeBegin > appearTime && isVisible) {
			isDisapearing = true;isVisible = false;
			return State.DISAPPEARED;
		}
		
		if(System.currentTimeMillis() - disappearTimeBegin > disappearTime && !isVisible) {
			isAppearing = true;isVisible = true;
			return State.APPEARED;
		}
		
		return State.STANDING;
	}
	@Override
	public TextureRegion getFrame(float dt) {
		TextureRegion region;
		currentState = getFrameState(dt);
		stateTime = (currentState == previousState ? stateTime + dt : 0);
		switch (currentState) {
		    case STANDING:
		    	currentBulletCount=0;
		        region = standing.getKeyFrame(stateTime, true);
		        break;
		    case ATTACKING1:
		    	if (currentBulletCount < maxBulletsPerAttack) {
		    		int angleDegree = MathUtils.random(-45, 45);
		    		bulletManage.addBullet("FireBullet", getX(), getY(),-1,15,(float)angleDegree,-1);
	                currentBulletCount++;
	            }

		    	region = attack1.getKeyFrame(stateTime, false);
		    	break;
		    case ATTACKING2:
		    	region = attack2.getKeyFrame(stateTime, false);
		    	
		    	break;
		    	
		    case DIE:
		    	region = die.getKeyFrame(stateTime, false);
		    	break;
		    case HURT:
		    	currentBulletCount=0;
		    	region = hurt.getKeyFrame(stateTime, false);
		    	break;
		    case DISAPPEARED:
		    	region = disappear.getKeyFrame(stateTime,false);
		    	break;
		    case APPEARED:
		    	region = appear.getKeyFrame(stateTime, false);
		    	break;
		    default:
		        region = standing.getKeyFrame(stateTime, true);
		        break;
		        
		    
		}
		previousState = currentState;
		return region;
	}
	@Override
	public void update(float dt) {
		setRegion(getFrame(dt));
		if(b2body != null) {
			posX = b2body.getPosition().x;
			posY = b2body.getPosition().y;		
		}
		if(isDied) {
			removeMonster();
		}
		if(!isRemoved) {
			bossAura.update(posX, posY, dt);
			setBounds(posX-MonsterWidth/CuocChienSinhTon.PPM/2,posY-MonsterHeight/CuocChienSinhTon.PPM/2,getRegionWidth()/CuocChienSinhTon.PPM*MonsterScaleX,getRegionHeight()/CuocChienSinhTon.PPM*MonsterScaleY);
			HealthBar.update(Health, HealthMax, posX, posY+radius);
			movement();
			batch.begin();
			
			this.draw(batch);
			batch.end();
		}
		
	}
	@Override
	public void prepareAnimation() {
		// TODO Auto-generated method stub
		//vertical attack
		atlasAttack1 = new TextureAtlas("Boss2/vertical-attack/crystal-knight.atlas");
		atlasAttack2 = new TextureAtlas("Boss2/horizontal-attack/crystal-knight.atlas");
		appearAtlas = new TextureAtlas("Boss2/appear/crystal-knight.atlas");
		disappearAtlas = new TextureAtlas("Boss2/disappear/crystal-knight.atlas");
		atlasHurt = new TextureAtlas("Boss2/hurt/crystal-knight.atlas");
		atlasStanding = new TextureAtlas("Boss2/idle/crystal-knight.atlas");
		atlasDie = new TextureAtlas("Monster/FlyingEye/Die.pack");
		
		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.1f, atlasHurt.getRegions());
		appear = new Animation<TextureRegion>(0.2f,appearAtlas.getRegions());
		disappear = new Animation<TextureRegion>(0.2f,disappearAtlas.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDie.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}


	


	@Override
	public void movement() {
		// TODO Auto-generated method stub
		
	}
	

}
 