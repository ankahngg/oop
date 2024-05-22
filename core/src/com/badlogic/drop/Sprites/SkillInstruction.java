package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class SkillInstruction extends Instruction  {
	public SkillInstruction(World world, TiledMap map, Rectangle bounds, PlayScreen screen) {
		super(world,map,bounds,screen);
		texture = new Texture("skill_instruction.png");
	}
}
