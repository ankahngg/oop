package com.badlogic.drop.Tools;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Collision;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Items extends Sprite{
	public Texture texture;
	public PlayScreen screen;
	public SpriteBatch batch;
	public World world;
	public float posX;
	public float posY;
	public float regionW;
	public float regionH;
	BodyDef bdef = new BodyDef();
	FixtureDef fdef = new FixtureDef();
	public Body b2body;
	public float radius;
	public float ItemScaleX=1;
	public float ItemScaleY=1;
	

	private Fixture itemDef;
	public Items(World worldd, PlayScreen screenn,float x, float y) {
		this.world = worldd;
		this.screen = screenn;
		batch = ((FirstMap) screenn).game.getBatch();
		posX = x;
		posY = y;
		prepairTexture();
		setRegion(texture);
		defineBody();
		Collision.setCategoryFilter(itemDef, Collision.ITEM_BITS, null);
		itemDef.setUserData(this);
	}
	
	public abstract void prepairTexture();
	
	public abstract void effect();
		
	

	public void defineBody() {
		// TODO Auto-generated method stub
		CircleShape shape = new CircleShape();
		 bdef.position.set(posX,posY);
		 
		 b2body = world.createBody(bdef);
		 bdef.type = BodyDef.BodyType.StaticBody;
		 shape.setRadius(regionW/2);
		
		 //radius = getRegionHeight()/CuocChienSinhTon.PPM/2;
		
		 fdef.shape = shape;
		 fdef.isSensor = true;
		 itemDef = b2body.createFixture(fdef);
	}

	public void update(float dt) {
		if(texture != null) {
			setRegion(texture);
			
			setBounds(posX-regionW/2,posY-regionH/2, regionW,regionH);
			batch.begin();
			this.draw(batch);
			batch.end();
			
		}
		
	}
	
	
}
