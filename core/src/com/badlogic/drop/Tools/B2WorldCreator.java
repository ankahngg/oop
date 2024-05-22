package com.badlogic.drop.Tools;

import java.util.ArrayList;
import java.util.HashSet;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Brick;
import com.badlogic.drop.Sprites.FlyingEye;
import com.badlogic.drop.Sprites.Instruction;
import com.badlogic.drop.Sprites.Monster;
import com.badlogic.drop.Sprites.StageBound;
import com.badlogic.drop.Sprites.StartInstruction;
import com.badlogic.drop.Sprites.Skeleton;
import com.badlogic.drop.Sprites.SkillInstruction;
import com.badlogic.drop.Sprites.Spine;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectSet;

public class B2WorldCreator {
	public static Instruction startInstruc;
	public static Instruction skillInstruc;
	public static Spine spine;
	
	public ArrayList<Vector2> checkpoints = new ArrayList<Vector2>();
	public ArrayList<StageBound> stageBounds = new ArrayList<StageBound>();

	public B2WorldCreator(World world,TiledMap map, PlayScreen screen) {
		if (screen instanceof FirstMap) {
			for(MapObject object : map.getLayers().get("GroundObject").getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				new Brick(world, map, rect,false);
			}
			
			for(MapObject object : map.getLayers().get("SensorObject").getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				Vector2 pos = new Vector2(rect.x,rect.y);
				checkpoints.add(pos);
				int stage = (int) (rect.x/CuocChienSinhTon.PPM/35);
				if(stage == 0) startInstruc = new StartInstruction(world, map, rect, screen);
				if(stage == 5) skillInstruc = new SkillInstruction(world, map, rect, screen);
				
			}
			
			for(MapObject object : map.getLayers().get("StageBound").getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				StageBound x = new StageBound(world, map, rect,false);
				
				stageBounds.add(x);
			}
		}
	}
}
