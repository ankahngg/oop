package com.badlogic.drop.Tools;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.FlyingEye;
import com.badlogic.drop.Sprites.HellBeast;
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
	public ObjectSet<HellBeast> hellBeast = new ObjectSet<HellBeast>();
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
			world.destroyBody(x.b2body);
		}
		for(Skeleton x : skeMonsters) {
			world.destroyBody(x.b2body);
		}
		for(HellBeast x : hellBeast) {
			world.destroyBody(x.b2body);
		}
		hellBeast.clear();
		eyeMonsters.clear();
		skeMonsters.clear();
		isOnStage = false;
	}
	
public void clearMonsterForDebug() {
		
		for(FlyingEye x : eyeMonsters) {
			x.removeMonster();
		}
		for(Skeleton x : skeMonsters) {
			
			x.removeMonster();
		}
		
		for(HellBeast x : hellBeast) {
			x.removeMonster();
		}
		hellBeast.clear();
		eyeMonsters.clear();
		skeMonsters.clear();
	
	}
	
	
	public void Creator(int y) {
		int o = 8;
		isOnStage = true;
		if(o+y-1 >= map.getLayers().getCount()) return;
		MapGroupLayer xx = (MapGroupLayer) map.getLayers().get(o+y-1);
		
		if(xx.getLayers().get("Skeleton") != null)
		for(MapObject object : xx.getLayers().get("Skeleton").getObjects()) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			Skeleton x = new Skeleton(world, screen, (int)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(int)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
			skeMonsters.add(x);
		}
		if(xx.getLayers().get("FlyingEye") != null)
		for(MapObject object : xx.getLayers().get("FlyingEye").getObjects()) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			FlyingEye x = new FlyingEye(world, screen, (Float)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(Float)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
			
			eyeMonsters.add(x);
		}
		if(xx.getLayers().get("HellBeast") != null)
		for(MapObject object : xx.getLayers().get("HellBeast").getObjects()) {
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			HellBeast x = new HellBeast(world, screen, (int)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(int)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
			
			hellBeast.add(x);
		}
		
		
		System.out.println(xx.getName());
		
	}
}
