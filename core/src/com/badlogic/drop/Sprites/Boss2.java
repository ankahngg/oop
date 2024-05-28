package com.badlogic.drop.Sprites;

import java.util.Currency;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Hero.State;
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
	
	
	private float disappearTime = 30.0f; // visble time (s)
    private float reappearTime = 10.0f; // invisible time (s)
    private float elapsedTime = 0.0f;
    private boolean isVisible = true;
    float dt;
	private float timeSinceLastAttack;
	private float attackInterval=3f;
	
    private int maxBulletsPerAttack = 50; // Giới hạn số lượng đạn mỗi lần tấn công
    private int currentBulletCount = 0; // Đếm số lượng đạn đã bắn trong một lần tấn công
    
	public Boss2(World world, PlayScreen screen, float x, float y) {		
		super(world, screen,x,y,200,true);
		monsterDef.setUserData(this);
		this.dt = (1f/60);
		timeSinceLastAttack = 0;
	}

	@Override
	public void removeMonster() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movement() {
		// TODO Auto-generated method stub
		 
	}

	@Override
	public State getFrameState(float dt) {
		// TODO Auto-generated method stub
		elapsedTime += dt;
        if (isVisible && elapsedTime >= disappearTime) {
            // Boss biến mất
        	((FlappyMap) screen).spawnMonsterWave(getX()+4);
            isVisible = false;
            elapsedTime = 0.0f;
        } else if (!isVisible && elapsedTime >= reappearTime) {
            // Boss xuất hiện lại
            isVisible = true;
            float newY = MathUtils.random(1,18); // Vị trí Y ngẫu nhiên trong màn hình
            b2body.setTransform(screen.getPlayer().getX()+23, newY , 0); // PPM là Pixels Per Meter	
            elapsedTime = 0.0f;
        }

		if(!isVisible) {
			if(!disappear.isAnimationFinished(stateTime)) return State.DISAPPEARED;
			
			else {
				b2body.setTransform(this.getX(),-10f, 0);
			}
		}
		if (isVisible) {
			if(appear.isAnimationFinished(stateTime)||isVisible) {
				isVisible = true;
				if(isHurting) {
					if(!hurt.isAnimationFinished(stateTime)) {
						return State.HURT;
					}
					else isHurting = false;
				}
				if(isDieing) {
					if(!die.isAnimationFinished(stateTime)) {
						return State.DIE;
					}
					else {
						isDieing = false;
						screen.handleDie();
					}
				}if(isHurt) {
					
					isHurting = true;
//					isAttacking = false;
					isFiring = false;
					isHurt = false;
					return State.HURT;
				}
				if(isDie) {
					isDieing = true;
					isDie = false;
					return State.DIE;
				}

				if(isAttacking) {
					
					if(!(attack1.isAnimationFinished(stateTime)&&attack2.isAnimationFinished(stateTime))) {
						if (attack1.isAnimationFinished(dt))
						isAttacking1=false;
						else if (attack1.isAnimationFinished(dt))
						isAttacking2=false;
						return previousState;
					}
					else {
						if (isAttacking1&!isAttacking2) {
							previousState = currentState;
							return State.ATTACKING1;
						}else if (!isAttacking1 && isAttacking2){
							previousState = currentState;
							return State.ATTACKING2;
						}
							
					
					}
					
				}else {
					isAttacking1=false;
					isAttacking2=false;
				}
				
								
			}
			else if (elapsedTime<=appear.getAnimationDuration()) {
				
	           return State.APPEARED;
			}
			
			
		}
		isAttacking1=false;
		isAttacking2=false;
		return State.STANDING;
	}
	@Override
	public TextureRegion getFrame(float dt) {
		TextureRegion region;
		currentState = getFrameState(dt);
//		System.out.println(currentState+"-"+isAttacking1+"-"+isAttacking2);
		stateTime = (currentState == previousState ? stateTime + dt : 0);
		switch (currentState) {
		    case STANDING:
		    	currentBulletCount=0;
		        region = standing.getKeyFrame(stateTime, true);
		        break;
		    case ATTACKING1:
		    	if (currentBulletCount < maxBulletsPerAttack) {
		    		int angleDegree = MathUtils.random(90, 270);
	                BulletManage.addBullet("FireBullet", getX(), getY(),-1,15,(float)angleDegree,-1);
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
		if(!isDied) {
			setBounds(posX-MonsterWidth/CuocChienSinhTon.PPM/2,posY-MonsterHeight/CuocChienSinhTon.PPM/2,getRegionWidth()/CuocChienSinhTon.PPM*MonsterScaleX,getRegionHeight()/CuocChienSinhTon.PPM*MonsterScaleY);
			HealthBar.update(Health, HealthMax, posX, posY+radius);
		}
		if(isDied) {
			removeMonster();
		}
		movement();
		handleAttack(dt);
		batch.begin();
		this.draw(batch);
		batch.end();
		
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
		
		attack1 = new Animation<TextureRegion>(0.1f, atlasAttack1.getRegions());
		attack2 = new Animation<TextureRegion>(0.1f, atlasAttack2.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.1f, atlasHurt.getRegions());
		appear = new Animation<TextureRegion>(0.8f,appearAtlas.getRegions());
		disappear = new Animation<TextureRegion>(0.8f,disappearAtlas.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}
	
	//attack after 5s
	private void handleAttack(float dt) {
        if (isVisible) {
            timeSinceLastAttack += dt;
            if (timeSinceLastAttack >= attackInterval) {
                isAttacking = true;
				int type = MathUtils.random(1);
				switch (type) {
				case 0:
					isAttacking1=true;
					break;

				default:
					isAttacking2=true;
					break;
				}
                timeSinceLastAttack = 0.0f;
            }
        }
    }
}
