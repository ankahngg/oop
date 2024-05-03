package com.badlogic.drop.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Brick extends InteractiveTileObject{
	public Brick(World world, TiledMap map, Rectangle bounds) {
		super(world,map,bounds);
		fixture.setUserData(this);
		Collision.setCategoryFilter(fixture, Collision.GROUND_BITS);
		
	}

	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		Gdx.app.log("Collison","lol");
	}
}
