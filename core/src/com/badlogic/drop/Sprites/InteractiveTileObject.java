package com.badlogic.drop.Sprites;

import com.badlogic.drop.Drop;
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
	
	protected Fixture fixture;
	
	public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
		this.world = world;
		this.map = map;
		this.bounds = bounds;
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX()+bounds.getWidth()/2)/Drop.PPM,(bounds.getY()+bounds.getHeight()/2)/Drop.PPM);
		body = world.createBody(bdef);
		
		shape.setAsBox(bounds.getWidth()/2/Drop.PPM, bounds.getHeight()/2/Drop.PPM);
		fdef.shape = shape;
		
		fixture = body.createFixture(fdef);
	}
	
	public abstract void onHit();
		
	
}
