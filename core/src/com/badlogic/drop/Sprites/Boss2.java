package com.badlogic.drop.Sprites;

import java.util.Currency;
import java.util.IllegalFormatFlagsException;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Screens.WinScreen;
import com.badlogic.drop.Sprites.Hero.State;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

public class Boss2 extends Boss{
	private static int knightHeath = 200;
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
    
	public Boss2(World world, PlayScreen screen,float x, float y,boolean isDynamic, boolean isSensor) {		
		super(world, screen, x, y,knightHeath, isDynamic,isSensor);
		bossAura = new Aura(world, screen,this.getWidth());
		monsterDef.setUserData(this);
		lastAttackTime = System.currentTimeMillis();
		attackCd = 2000;
		bossAura= new Aura(world, screen);
	}
	@Override
	
	public void update(float dt) {
		// TODO Auto-generated method stub
		if(this.b2body!=null)
		bossAura.update(this.getX()+1.5f, this.getY()+1.5f, dt);
		super.update(dt);
		batch.begin();
		float x = screen.getCamera().position.x-screen.getGamePort().getWorldWidth()/2;
		float y = screen.getCamera().position.y+screen.getGamePort().getWorldHeight()/2;
		batch.draw(BossHealthBar, x+20,y-2,BossHealthBar.getWidth()/CuocChienSinhTon.PPM/2+5,BossHealthBar.getHeight()/CuocChienSinhTon.PPM/2, 0,0,BossHealthBar.getWidth(),BossHealthBar.getHeight(),false,false);
		
		float ratio = 1.0f*Health/HealthMax;
		float HealthX = x+20+(BossHealthBar.getWidth()-BossHealth.getWidth())/2/CuocChienSinhTon.PPM;
		float HealthY = y-2+(BossHealthBar.getHeight()-BossHealth.getHeight())/2/CuocChienSinhTon.PPM;
		
		batch.draw(BossHealth, HealthX,HealthY,
				(BossHealth.getWidth()/CuocChienSinhTon.PPM/2+5)*ratio,BossHealth.getHeight()/CuocChienSinhTon.PPM/2, 
				0,0,(int)(BossHealth.getWidth()*ratio),BossHealth.getHeight(),
				false,false);
		batch.end();
	}
	@Override
	public State getFrameState(float dt) {
		// TODO Auto-generated method stub
		if(isDieing) {
			if(!die.isAnimationFinished(stateTime)) {
				return State.DIE;
			}
			else {
				isDieing = false;
				
				System.out.println("die");
				((FlappyMap)screen).setWinScreen();
				stageCreator.removeMonster(this);
			}
		}
		if(isDisapearing) {
			if(!disappear.isAnimationFinished(stateTime)) return State.DISAPPEARED;
			else {
				Health = Math.min(Health, Health+50);
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
					bulletManage.addBullet("BossBullet1", posX, posY, -1);
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
			System.out.println(isDie + " "+isDied);
			if(isDie) {
				System.out.println("chet");
				isDieing = true;
				isDie = false;
				return State.DIE;
			}
			
			
			if(isHurt) {
				isHurting = true;
				isHurt = false;
				return State.HURT;
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
	public void prepareAnimation() {
		// TODO Auto-generated method stub
		//vertical attack
		BossHealthBar = new Texture("HealthBar/bg.png");
		BossHealth =  new Texture("HealthBar/green.png");

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
 