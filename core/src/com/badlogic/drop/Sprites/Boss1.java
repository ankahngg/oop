
package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Monster.State;
import com.badlogic.drop.Tools.StageCreator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Boss1 extends Boss{
	
	

	public void prepareAnimation() {
		BossHealthBar = new Texture("HealthBar/bg.png");
		BossHealth =  new Texture("HealthBar/green.png");
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
	
	public Boss1(World world, PlayScreen screen, float x, float y, int maxHealth ,boolean isDynamic, boolean isSensor) {		
		super(world, screen,x,y,maxHealth,isDynamic,isSensor);
		monsterDef.setUserData(this);
		
	}
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
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
	

	public void movement() {
		TeleCd = 3000;
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
	
	
	double attackCd = 2000;
	boolean isAttacking = false;
	boolean isHurting = false;
	
	public State getFrameState(float dt) {
		if(b2body==null) {
			isDieing = false;
			stageCreator.removeMonster(this);
			isDieFinish = true;
			((FirstMap)screen).map1Music.stop();
			screen.game.setScreen(new FlappyMap(screen.game));
		}
		if(isDieing) {
			if(!die.isAnimationFinished(stateTime)) return State.DIE;
			else {
				isDieing = false;
				stageCreator.removeMonster(this);
				isDieFinish = true;
				((FirstMap)screen).map1Music.stop();
				screen.game.setScreen(new FlappyMap(screen.game));
			}
		}
		
		if(isAttacking) {
			isHurting = false;
			if(!attack1.isAnimationFinished(stateTime)) return State.ATTACKING1;
			else {
				int rnd = (rd.nextInt(6));
				if(rnd == 5) {
					bulletManage.addBullet("BossBullet1", b2body.getPosition().x, b2body.getPosition().y, -1);
					
				}else if((rnd == 4) && stageCreator.monsters.size <= 5) {
					stageCreator.addMonster("Skeleton", b2body.getPosition().x-5, 3,6,true,false);
				}
				else if((rnd == 3) && stageCreator.monsters.size <= 5) {
					
					stageCreator.addMonster("FlyingEye", b2body.getPosition().x-3,b2body.getPosition().y ,6,false,false);
				}
				else {
					bulletManage.addBullet("BossBullet2", b2body.getPosition().x, b2body.getPosition().y, -1,20,0,-1);
					bulletManage.addBullet("BossBullet2", b2body.getPosition().x, b2body.getPosition().y, -1,20,15,-1);
					bulletManage.addBullet("BossBullet2", b2body.getPosition().x, b2body.getPosition().y, -1,20,-15,-1);
				}
				
				lastAttackTime = System.currentTimeMillis();
				isAttacking = false;
			}
		}
		if(System.currentTimeMillis() - lastAttackTime >= attackCd) {
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
	

	
	
	
	
}
