package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Fire extends Sprite{
	Animation<TextureRegion> fireAnimation;
SpriteBatch batch;
	
	public World world;
	public PlayScreen screen;
	
	public float stateTime;
	private float height;
	private float width;
	float x;
	float y;
	public boolean isFinished;
	public Fire(World world, PlayScreen playScreen,float x,float y) {
		prepareAnimation();
		this.world = world;
		this.screen=playScreen;
		this.x = x;
		this.y=y;
		stateTime=0;
		batch = screen.game.getBatch();
	}
	private void prepareAnimation() {
		TextureAtlas atlas = new TextureAtlas("fire/fire.atlas");
		fireAnimation = new Animation<TextureRegion>(0.05f,atlas.getRegions());
	}
	public void update(float dt) {
		if(fireAnimation.isAnimationFinished(stateTime)) isFinished=true;
		stateTime += dt;

		setRegion(fireAnimation.getKeyFrame(stateTime,true));
		height = getRegionWidth()/CuocChienSinhTon.PPM;
		width = getRegionHeight()/CuocChienSinhTon.PPM;
		setBounds(x, y, width,height);
		batch.begin();
		this.draw(batch);
		batch.end();
	}

}
