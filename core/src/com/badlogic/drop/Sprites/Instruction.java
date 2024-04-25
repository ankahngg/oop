package com.badlogic.drop.Sprites;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Filter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Instruction extends SensorObject {
	private SpriteBatch batch;
	private Texture texture;
	public Instruction(World world, TiledMap map, Rectangle bounds, PlayScreen screen) {
		super(world,map,bounds);
		fixture.setUserData(this);
		Collision.setCategoryFilter(fixture, Collision.INSTRUCTION_BITS);
		texture = new Texture("Instruction.png");
		batch = screen.game.getBatch();
	}
	
	public void onHit() {
		batch.begin();
		batch.draw(texture, 
				this.body.getPosition().x-texture.getWidth()/CuocChienSinhTon.PPM/2, 
				this.body.getPosition().y+3 ,
				texture.getWidth()/CuocChienSinhTon.PPM,texture.getHeight()/CuocChienSinhTon.PPM);
		batch.end();
		
	}
	public void onLeave() {
		
	}
}
