package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractiveTileObject {
	public World world;
	public TiledMap map;
	public TiledMapTile tile;
	public Rectangle bounds;
	public Body body;
	public BodyDef bdef = new BodyDef();
	public PolygonShape shape = new PolygonShape();
	public FixtureDef fdef = new FixtureDef();
	
	protected Fixture fixture;
	
	public InteractiveTileObject(World world, TiledMap map, Rectangle bounds, boolean isSensor) {
		this.world = world;
		this.map = map;
		this.bounds = bounds;
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX()+bounds.getWidth()/2)/CuocChienSinhTon.PPM,(bounds.getY()+bounds.getHeight()/2)/CuocChienSinhTon.PPM);
		body = world.createBody(bdef);
		
		shape.setAsBox(bounds.getWidth()/2/CuocChienSinhTon.PPM, bounds.getHeight()/2/CuocChienSinhTon.PPM);
		fdef.shape = shape;
		fdef.filter.maskBits = Collision.MONSTER_BITS; 
		if(isSensor) fdef.isSensor = true;
		
		fixture = body.createFixture(fdef);
	}
	
	public abstract void onHit();
		
	
}
