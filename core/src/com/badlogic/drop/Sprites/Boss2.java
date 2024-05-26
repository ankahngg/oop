package com.badlogic.drop.Sprites;

import java.util.Currency;

import com.badlogic.drop.CuocChienSinhTon;
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
	
	
	private float disappearTime = 10.0f; // Thời gian biến mất (giây)
    private float reappearTime = 20.0f; // Thời gian xuất hiện lại (giây)
    private float elapsedTime = 0.0f;
    private boolean isVisible = true;
    float dt;
    private boolean isAppearing=true;
	public Boss2(World world, PlayScreen screen, float x, float y) {		
		super(world, screen,x,y,200,true);
		monsterDef.setUserData(this);
		this.dt = (1f/60);
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
            isVisible = false;
            isAppearing=false;
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
			System.out.println(appear.isAnimationFinished(stateTime));
			if(appear.isAnimationFinished(stateTime)||isVisible) {
				isAppearing = true;
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
				}
				if(isHurt) {
					isHurting = true;
					isAttacking = false;
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
					
				}
			}
			else if (elapsedTime<=appear.getAnimationDuration()) {
				
	           return State.APPEARED;
			}
			
			
		}
		return State.STANDING;
	}
	@Override
	public TextureRegion getFrame(float dt) {
		TextureRegion region;
		currentState = getFrameState(dt);
		System.out.println(currentState);
		stateTime = (currentState == previousState ? stateTime + dt : 0);
		switch (currentState) {
		    case STANDING:
		        region = standing.getKeyFrame(stateTime, true);
		        break;
		    case ATTACKING1:
		    	
		    	region = attack1.getKeyFrame(stateTime, false);
		    	break;
		    case ATTACKING2:
		    	region = attack2.getKeyFrame(stateTime, false);
		    	break;
		    	
		    case DIE:
		    	region = die.getKeyFrame(stateTime, false);
		    	break;
		    case HURT:
		    	System.out.println("hurt");
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
		hurt = new Animation<TextureRegion>(0.5f, atlasHurt.getRegions());
		appear = new Animation<TextureRegion>(0.8f,appearAtlas.getRegions());
		disappear = new Animation<TextureRegion>(0.8f,disappearAtlas.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}
	
}
