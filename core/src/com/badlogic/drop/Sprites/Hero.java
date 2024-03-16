package com.badlogic.drop.Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Hero extends Sprite{
	public World world;
	public Body b2body;
	
	
	public Hero(World world) {
		this.world = world;
		defineMario();
	}
	private void defineMario() {
		// TODO Auto-generated method stub
		 BodyDef bdef = new BodyDef();
		 bdef.position.set(32/Drop.PPM,48/Drop.PPM);
		 bdef.type = BodyDef.BodyType.DynamicBody;
		 b2body = world.createBody(bdef);
		 
		 FixtureDef fdef = new FixtureDef();
		 CircleShape shape = new CircleShape();
		 shape.setRadius(5/Drop.PPM);
		 fdef.shape = shape;
		 b2body.createFixture(fdef);

	}
	
}	
