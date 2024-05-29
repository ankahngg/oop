package com.badlogic.drop.Tools;

import java.util.Set;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Collision;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Item extends Sprite{
	public Texture texture;
	public PlayScreen screen;
	public SpriteBatch batch;
	public World world;
	public float posX;
	public float posY;
	public float regionW;
	public float regionH;
	public float speed;
	public float angle;
	public float lifeTime=-1;
	public float direction=0;
	BodyDef bdef = new BodyDef();
	FixtureDef fdef = new FixtureDef();
	public Body b2body;
	public float radius;
	public boolean isRemoved=false;
	public float ItemScaleX=1;
	public float ItemScaleY=1;
	

	private Fixture itemDef;
	private int stateTime;
	public Item(World worldd, PlayScreen screenn,float x, float y) {
		this.world = worldd;
		this.screen = screenn;
		batch =  screenn.game.getBatch();
		this.posX = x;
		this.posY = y;
		
		prepairTexture();
		setRegion(texture);
		defineBody();
		Collision.setCategoryFilter(itemDef, Collision.ITEM_BITS, null);
		itemDef.setUserData(this);
	}
	
	public void setUp(int direction, float speed, float angle, float lifeTime)
	{
		
		this.speed = speed;
		this.angle = angle;
		this.lifeTime = lifeTime;
		this.direction = direction;
	}
	
	public abstract void prepairTexture();
	
	public abstract void effect();
		
	


	public void update(float dt) {
		
		if(texture != null) {
			stateTime ++;
			if(lifeTime != -1) {
				if(stateTime > lifeTime) removeItem();
			}
			else {
				if((posX>screen.getCamera().position.x+screen.getCamera().viewportWidth/2)
						||(posX<screen.getCamera().position.x-screen.getCamera().viewportWidth/2)) {
					removeItem();
				}			
			}
			
			setRegion(texture);
			
			if((posX>screen.getCamera().position.x+screen.getCamera().viewportWidth/2)
					||(posX<screen.getCamera().position.x-screen.getCamera().viewportWidth/2)) {
				removeItem();
			}	
			if(!isRemoved) {
				movement();
				setBounds(b2body.getPosition().x-regionW/2,b2body.getPosition().y-regionH/2, regionW,regionH);
				batch.begin();
				this.draw(batch);
				batch.end();
				
			}
			
		}
		
	}
	public void movement() {
		if(direction != 0) {
			double sin = Math.sin(Math.toRadians(angle));
			double cos =  Math.cos(Math.toRadians(angle));
			float vecX = (float) (speed*cos*direction);
			float vecY = (float) (speed*sin); 
			
			b2body.setLinearVelocity(new Vector2(vecX,vecY));				
		}
		else {
			b2body.setLinearVelocity(new Vector2(0,0));	
		}
	}

	private void removeItem() {
		StageCreator.removeItems(this);
		isRemoved = true;
		
	}
	
	
	public void defineBody() {
		// TODO Auto-generated method stub
		CircleShape shape = new CircleShape();
		bdef.position.set(posX,posY);
		
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.gravityScale = 0;
		b2body = world.createBody(bdef);
		
		shape.setRadius(regionW/2);
		
		//radius = getRegionHeight()/CuocChienSinhTon.PPM/2;
		
		fdef.shape = shape;
		fdef.isSensor = true;
		itemDef = b2body.createFixture(fdef);
	}
}
