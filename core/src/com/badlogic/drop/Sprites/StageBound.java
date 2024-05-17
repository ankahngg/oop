package com.badlogic.drop.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;

public class StageBound extends InteractiveTileObject{
	public int id;
	public StageBound(World world, TiledMap map, Rectangle bounds,boolean isSensor) {
		super(world,map,bounds,false);
		
//		fdef.filter.maskBits = Collision.MONSTER_BITS; 
//		fixture = body.createFixture(fdef);
//		body.destroyFixture(fixture);
//		fdef.friction = 0.1f;
//		fixture = body.createFixture(fdef;);
		fixture.setFriction(0.1f);
		Collision.setCategoryFilter(fixture, Collision.STAGEBOUND_BITS,null);	
		fixture.setUserData(this);
		
	}

	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		Gdx.app.log("Collison","lol");
	}
}
