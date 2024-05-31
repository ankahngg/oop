package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Aura extends Sprite{
	private TextureAtlas auraAtlas;
	public Animation<TextureRegion> aura;
	
	SpriteBatch batch;
	
	public World world;
	public PlayScreen screen;
	
	public float stateTime;
	private float height;
	private float width;
	
	public Aura (World world,PlayScreen screen) {
		this.world = world;
		this.screen = screen;
		
		batch = screen.game.getBatch();
		
		auraAtlas = new TextureAtlas("aura/aura.pack");
		aura = new Animation<TextureRegion>(0.1f,auraAtlas.getRegions());
		aura.setPlayMode(PlayMode.LOOP);
		
	}
	public Aura(World world,PlayScreen screen,float rWidth) {
		this(world,screen);
		this.scale(0.5f*rWidth/aura.getKeyFrame(0).getRegionHeight());
		
	}
	public void scaleByWidth(float width) {
		this.setScale(0.5f*width/this.width);
	}
	public void update(float x,float y,float dt) {
		stateTime += dt;
		setRegion(aura.getKeyFrame(stateTime,true));
		height = getRegionHeight()/CuocChienSinhTon.PPM;
		width = getRegionHeight()/CuocChienSinhTon.PPM;
		setBounds(x-width/2, y-height/4, width,height);
		batch.begin();
		this.draw(batch);
		batch.end();
	}
}
