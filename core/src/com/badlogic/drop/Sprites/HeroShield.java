package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class HeroShield extends Sprite{
	
	public TextureAtlas atlasSheild;
	public Animation<TextureRegion> shield;
	
	SpriteBatch batch;
	
	public World world;
	public PlayScreen screen;
	public float stateTime;
	private float regionH;
	private float regionW;
	public HeroShield(World worldd, PlayScreen screenn) {
		this.world = worldd;
		this.screen = screenn;
		batch = screenn.game.getBatch();
		atlasSheild = new TextureAtlas("Shield/shield.pack");
		shield = new Animation<TextureRegion>(0.2f, atlasSheild.getRegions());
	}
	public void update(float x, float y,float dt) {
		stateTime += dt;
		setRegion(shield.getKeyFrame(stateTime,true));
		regionH = getRegionHeight()/CuocChienSinhTon.PPM;
		regionW = getRegionHeight()/CuocChienSinhTon.PPM;
		setBounds(x-regionW/2, y-regionH/2, regionW,regionH);
		batch.begin();
		this.draw(batch);
		batch.end();
	}
}