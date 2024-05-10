package com.badlogic.drop.Tools;

import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Brick;
import com.badlogic.drop.Sprites.Instruction;
import com.badlogic.drop.Sprites.MonsterBound;
import com.badlogic.drop.Sprites.Spine;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class B2WorldCreator {
	public static Instruction startInstruc;
	public static Spine spine;

	public B2WorldCreator(World world,TiledMap map, PlayScreen screen) {
		if (screen instanceof FirstMap) {
			for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Brick(world, map, rect,false);
		}
		for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			startInstruc = new Instruction(world, map, rect, screen);
		}
		for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new Spine(world, map, rect,false);
		}
		for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			new MonsterBound(world, map, rect,true);
		}	
//		for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
//			Rectangle rect = ((RectangleMapObject) object).getRectangle();
//			new Spine(world, map, rect, screen);
//		}	
	}
		
	}
}
