package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Spine extends InteractiveTileObject{
	public Spine(World world, TiledMap map, Rectangle bounds,boolean isSensor) {
		super(world,map,bounds,isSensor);
		fixture.setUserData(this);
		Collision.setCategoryFilter(fixture, Collision.SPINE_BITS,null);
	}

	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		
	}
}
