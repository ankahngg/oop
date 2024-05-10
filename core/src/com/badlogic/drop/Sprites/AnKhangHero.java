package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class AnKhangHero extends Hero{
	
	public AnKhangHero(World world, PlayScreen screen) {	
		super(world, screen);
		this.Health = 5;
		this.HealthMax = 10;
		this.currentRank = 0;

		prepareAnimation();
		
		
		if (screen instanceof FirstMap) {
			defineHero(30,10);
		}else {
			System.out.print("FlappyMap");
			defineHero(0,10);
		}
		
		
		setBounds(0, 0, getRegionWidth()/CuocChienSinhTon.PPM, getRegionHeight()/CuocChienSinhTon.PPM);
		currentState = State.STANDING;
		previousState = State.STANDING;
		Collision.setCategoryFilter(normalDef, Collision.HERO_BITS);
		normalDef.setUserData(this);
	}
	
	public void prepareAnimation() {
		atlasAttack1 = new TextureAtlas("Hero2/packs/Attack1.pack");
		atlasAttack2 = new TextureAtlas("Hero2/packs/Attack2.pack");
		atlasAttack3 = new TextureAtlas("Hero2/packs/Attack3.pack");
		atlasStanding = new TextureAtlas("Hero2/packs/Idle.pack");
		atlasRunning = new TextureAtlas("Hero2/packs/Run.pack");
		atlasJumping = new TextureAtlas("Hero2/packs/Jump.pack");
		atlasHurting = new TextureAtlas("Hero2/packs/Hurt2.pack");
		atlasDieing = new TextureAtlas("Hero2/packs/Die.pack");
		
		attack1 = new Animation<TextureRegion>(0.05f, atlasAttack1.getRegions());
		attack2 = new Animation<TextureRegion>(0.05f, atlasAttack2.getRegions());
		attack3 = new Animation<TextureRegion>(0.05f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.1f, atlasRunning.getRegions());
		jumping = new Animation<TextureRegion>(0.1f, atlasJumping.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.1f, atlasHurting.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDieing.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		HeroHeight = getRegionHeight();
	}
	
	
	public void update(float dt) {
		super.update(dt);
		attackFix();
	}
	
	
	private void attackFix() {
		PolygonShape hitbox = new PolygonShape();
		if(runningRight != currentDirection) {
			if(attackFixture != null) body.destroyFixture(attackFixture);
			if(runningRight) hitbox.setAsBox(2, 2,new Vector2(2,1),0);
			else hitbox.setAsBox(2, 2,new Vector2(-2,1),0);
			fdef.shape = hitbox;
			fdef.isSensor = true;
			attackFixture = body.createFixture(fdef);
			attackFixture.setUserData("DamageRange");
			Collision.setCategoryFilter(attackFixture, Collision.HEROATTACK_BITS);
			currentDirection = runningRight;
		}
	}

	protected void defineHero(int x,int y) {
		// TODO Auto-generated method stub
		 bdef.position.set(x,y);
		 super.defineHero();
		 
	}
}	
