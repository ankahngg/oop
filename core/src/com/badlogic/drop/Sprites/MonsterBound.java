package com.badlogic.drop.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class MonsterBound extends InteractiveTileObject{
	public MonsterBound(World world, TiledMap map, Rectangle bounds,boolean isSensor) {
		super(world,map,bounds,isSensor);
		fixture.setUserData(this);
		Collision.setCategoryFilter(fixture, Collision.MONSTERBOUND_BITS);
	}

	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		Gdx.app.log("Collison","lol");
	}
}
