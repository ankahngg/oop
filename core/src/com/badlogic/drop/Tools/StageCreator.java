package com.badlogic.drop.Tools;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.FlyingEye;
import com.badlogic.drop.Sprites.HellBeast;
import com.badlogic.drop.Sprites.Monster;
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
	public ObjectSet<Items> items = new ObjectSet<Items>();
	public ObjectSet<Items> itemsRemove = new ObjectSet<Items>();
	public ObjectSet<Monster> monsters = new ObjectSet<Monster>();
	public ObjectSet<Monster> monstersRemove = new ObjectSet<Monster>();
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
		if(monsters.isEmpty() && isOnStage) {
			isOnStage = false;
			return true;
		}
		return false;
	}
	public void clearMonster() {
		
		for(Monster x : monsters) {
			world.destroyBody(x.b2body);
		}
		
		for(Items x : items) {
			world.destroyBody(x.b2body);
		}
//		for(Shield x : shields) {
//			world.destroyBody(x.b2body);
//		}
//		for(Strength x : strengths) {
//			world.destroyBody(x.b2body);
//		}
		monstersRemove.clear();
		monsters.clear();
		itemsRemove.clear();
		items.clear();
		isOnStage = false;
	}
	
	
	public void Creator(int y) {
		int o = 11;
		isOnStage = true;
		if(o+y-1 >= map.getLayers().getCount()) return;
		MapGroupLayer xx = (MapGroupLayer) map.getLayers().get(o+y-1);
		
		if(xx.getLayers().get("Skeleton") != null)
		for(MapObject object : xx.getLayers().get("Skeleton").getObjects()) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			Skeleton x = new Skeleton(world, screen, (int)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(int)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
			monsters.add(x);
		}
		if(xx.getLayers().get("FlyingEye") != null)
		for(MapObject object : xx.getLayers().get("FlyingEye").getObjects()) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			FlyingEye x = new FlyingEye(world, screen, (Float)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(Float)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
			
			monsters.add(x);
		}
		if(xx.getLayers().get("HellBeast") != null)
		for(MapObject object : xx.getLayers().get("HellBeast").getObjects()) {
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			HellBeast x = new HellBeast(world, screen, (int)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(int)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
			
			monsters.add(x);
		}
		if(xx.getLayers().get("Heart") != null)
			for(MapObject object : xx.getLayers().get("Heart").getObjects()) {
				
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				Heart x = new Heart(world, screen, (int)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(int)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
				
				items.add(x);
			}
		
		if(xx.getLayers().get("Shield") != null)
			for(MapObject object : xx.getLayers().get("Shield").getObjects()) {
				
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				Shield x = new Shield(world, screen, (int)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(int)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
				
				items.add(x);
			}
		if(xx.getLayers().get("Strength") != null)
			for(MapObject object : xx.getLayers().get("Strength").getObjects()) {
				
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				Strength x = new Strength(world, screen, (int)((rect.x+rect.width/2)/CuocChienSinhTon.PPM),(int)((rect.y+rect.height/2)/CuocChienSinhTon.PPM));
				
				items.add(x);
			}
		
		
	}
}
