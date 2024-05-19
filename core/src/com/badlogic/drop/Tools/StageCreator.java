package com.badlogic.drop.Tools;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.FlyingEye;
import com.badlogic.drop.Sprites.Skeleton;
import com.badlogic.drop.Sprites.StageBound;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectSet;

public class StageCreator {
	public ObjectSet<FlyingEye> eyeMonsters = new ObjectSet<FlyingEye>();
	public ObjectSet<Skeleton> skeMonsters = new ObjectSet<Skeleton>();
	public World world;
	public TiledMap map;
	public PlayScreen screen;
	public boolean isOnStage = false;
	
	public StageCreator(World world,TiledMap map, PlayScreen screen) {
		this.world = world;
		this.map = map;
		this.screen = screen;
	}
	
	public boolean isStageClear() {
		if(eyeMonsters.isEmpty() && skeMonsters.isEmpty() && isOnStage) {
			isOnStage = false;
			return true;
		}
		return false;
	}
	public void clearMonster() {
		
		for(FlyingEye x : eyeMonsters) {
			x.removeMonster();
		}
		for(Skeleton x : skeMonsters) {
			
			x.removeMonster();
		}
		eyeMonsters.clear();
		skeMonsters.clear();
		isOnStage = false;
	}
	
	
	public void Creator(int y) {
		int o = 8;
		if(o+y-1 >= map.getLayers().getCount()) return;
		MapGroupLayer xx = (MapGroupLayer) map.getLayers().get(o+y-1);
		
		for(MapObject object : xx.getLayers().get("Skeleton").getObjects()) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			Skeleton x = new Skeleton(world, screen, (int)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(int)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
			skeMonsters.add(x);
		}
		
		for(MapObject object : xx.getLayers().get("FlyingEye").getObjects()) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			FlyingEye x = new FlyingEye(world, screen, (int)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(int)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
			
			eyeMonsters.add(x);
		}
		
		
		System.out.println(xx.getName());
		isOnStage = true;
	}
}
