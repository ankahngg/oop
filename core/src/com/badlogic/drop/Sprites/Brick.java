package com.badlogic.drop.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Brick extends InteractiveTileObject{
	private FixtureDef leftFixtureDef,rightFixtureDef;
	private PolygonShape leftShape = new PolygonShape();
	private PolygonShape rightShape = new PolygonShape();
	
	public Brick(World world, TiledMap map, Rectangle bounds, boolean isSensor) {
		super(world,map,bounds,isSensor);
		fixture.setUserData(this);
		Collision.setCategoryFilter(fixture, Collision.GROUND_BITS,null);
		
	}

	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		Gdx.app.log("Collison","lol");
	}
}
