package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class HungKing extends Hero{
	
	public HungKing(World world, PlayScreen screen) {
		super(world, screen, 20,"Thunder");
	}

	
	public void prepareAnimation() {
		atlasAttack1 = new TextureAtlas("Hero/HungKing/attack1/hung-king.atlas");
		atlasAttack2 = new TextureAtlas("Hero/HungKing/attack2/hung-king.atlas");
		atlasAttack3 = new TextureAtlas("Hero/HungKing/attack3/hung-king.atlas");
		atlasStanding = new TextureAtlas("Hero/HungKing/idle/hung-king.atlas");
		atlasRunning = new TextureAtlas("Hero/HungKing/run/hung-king.atlas");
		atlasJumping = new TextureAtlas("Hero/HungKing/jump/hung-king.atlas");
		atlasHurting = new TextureAtlas("Hero/HungKing/take hit/hung-king.atlas");
		atlasDieing = new TextureAtlas("Hero/HungKing/death/hung-king.atlas");
		
		attack1 = new Animation<TextureRegion>(0.08f, atlasAttack1.getRegions());
		attack2 = new Animation<TextureRegion>(0.08f, atlasAttack2.getRegions());
		attack3 = new Animation<TextureRegion>(0.08f, atlasAttack3.getRegions());
		running = new Animation<TextureRegion>(0.1f, atlasRunning.getRegions());
		jumping = new Animation<TextureRegion>(0.1f, atlasJumping.getRegions());
		standing = new Animation<TextureRegion>(0.1f, atlasStanding.getRegions());
		hurt = new Animation<TextureRegion>(0.05f, atlasHurting.getRegions());
		die = new Animation<TextureRegion>(0.1f, atlasDieing.getRegions());
		setRegion(atlasStanding.getRegions().get(1));
		HeroHeight = getRegionHeight();
	}
}
