package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Boss2 extends Boss{
	TextureAtlas disappearAtlas;
	TextureAtlas appearAtlas;
	Animation<TextureRegion> disappear;
	Animation<TextureRegion> appear;
	public Boss2(World world, PlayScreen screen, float x, float y) {		
		super(world, screen,x,y,40,true);
		
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
		return State.STANDING;
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
		appear = new Animation<TextureRegion>(0.2f,appearAtlas.getRegions());
		disappear = new Animation<TextureRegion>(0.2f,disappearAtlas.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		MonsterHeight = getRegionHeight();
		MonsterWidth = getRegionWidth();
	}
	
}
