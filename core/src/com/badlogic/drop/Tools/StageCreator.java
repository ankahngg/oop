package com.badlogic.drop.Tools;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Boss;
import com.badlogic.drop.Sprites.Boss1;
import com.badlogic.drop.Sprites.Boss2;
import com.badlogic.drop.Sprites.DragonBallMonster1;
import com.badlogic.drop.Sprites.DragonBallMonster2;
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

	static public ObjectSet<Item> items = new ObjectSet<Item>();
	static public ObjectSet<Item> itemsRemove = new ObjectSet<Item>();

	static public ObjectSet<Monster> monsters = new ObjectSet<Monster>();
	static public ObjectSet<Monster> monstersRemove = new ObjectSet<Monster>();
	static public World world;
	static public TiledMap map;
	static public PlayScreen screen;
	static public boolean isOnStage = false;
	
	static public void setup(World worldd, PlayScreen screenn) {
		world = worldd;
		screen = screenn;
	}
	
	static public boolean isStageClear() {
		if(monsters.isEmpty() && isOnStage) {
			isOnStage = false;
			return true;
		}
		return false;
	}
	
	static public void update(float dt) {
		for(Item x : StageCreator.items) {
			if(x!=null)
			x.update(dt);
		}
		
		if(!StageCreator.itemsRemove.isEmpty()) {
			for(Item x : StageCreator.itemsRemove) {
				if(x!=null)
				world.destroyBody(x.b2body);
				StageCreator.items.remove(x);
			}
			StageCreator.itemsRemove.clear();
		}
		
		for(Monster x : StageCreator.monsters) {
			if(x!=null) x.update(dt);
		}
		for(Monster x : StageCreator.monstersRemove) {
			if(x!=null && x.b2body!=null) world.destroyBody(x.b2body);
			StageCreator.monsters.remove(x);
		}
		StageCreator.monstersRemove.clear();
	}
	
	// add default monster
	static public void addMonster(String type, float x, float y, int maxHealth, boolean isDynamic, boolean isSensor ) {
		if(type == "Skeleton") {
			Skeleton xx = new Skeleton(world, screen, x,y,maxHealth,isDynamic,isSensor);
			monsters.add(xx);
			
		}
		else if(type == "FlyingEye") {
				FlyingEye xx = new FlyingEye(world, screen, x,y,maxHealth,isDynamic,isSensor);
				monsters.add(xx);
			}
		else if(type == "HellBeast") {
			HellBeast xx = new HellBeast(world, screen, x,y,maxHealth,isDynamic,isSensor);
			monsters.add(xx);
		}
		else if(type == "Boss") {
			Boss1 xx = new Boss1(world, screen, x,y,maxHealth,isDynamic,isSensor);
			monsters.add(xx);
		}
		else if(type == "DragonBallMonster1") {
			DragonBallMonster1 xx = new DragonBallMonster1(world, screen, x,y,maxHealth,isDynamic,isSensor);
			monsters.add(xx);
		}
		else if(type == "DragonBallMonster2") {
			DragonBallMonster2 xx = new DragonBallMonster2(world, screen, x,y,maxHealth,isDynamic,isSensor);
			monsters.add(xx);
		}
		else if(type == "Boss2") {
			System.out.println("boss");
			Boss2 xx = new Boss2(world, screen, x,y,maxHealth,isDynamic,isSensor);
			monsters.add(xx);
		}
	}
	
	//add movement monster
	static public void addMonster(String type, float x, float y, int maxHealth, boolean isDynamic, boolean isSensor,
			int direction, float speed, float angle,float lifeTime) {
		if(type == "Skeleton") {
			Skeleton xx = new Skeleton(world, screen, x,y,maxHealth,isDynamic,isSensor);
			monsters.add(xx);
			xx.setUp(direction, speed, angle, lifeTime);
		}
		else if(type == "FlyingEye") {
			System.out.println("add");
				FlyingEye xx = new FlyingEye(world, screen, x,y,maxHealth,isDynamic,isSensor);
				xx.setUp(direction, speed, angle, lifeTime);
				monsters.add(xx);
			}
		else if(type == "HellBeast") {
			HellBeast xx = new HellBeast(world, screen, x,y,maxHealth,isDynamic,isSensor);
			monsters.add(xx);
			xx.setUp(direction, speed, angle, lifeTime);
		}
		else if(type == "Boss") {
			Boss1 xx = new Boss1(world, screen, x,y,maxHealth,isDynamic,isSensor);
			monsters.add(xx);
			xx.setUp(direction, speed, angle, lifeTime);
		}
		else if(type == "DragonBallMonster1") {
			DragonBallMonster1 xx = new DragonBallMonster1(world, screen, x,y,maxHealth,isDynamic,isSensor);
			xx.setUp(direction, speed, angle, lifeTime);
			monsters.add(xx);
		}
		else if(type == "DragonBallMonster2") {
			DragonBallMonster2 xx = new DragonBallMonster2(world, screen, x,y,maxHealth,isDynamic,isSensor);
			xx.setUp(direction, speed, angle, lifeTime);
			monsters.add(xx);
		}
		else if(type == "Boss2") {
			Boss2 xx = new Boss2(world, screen, x,y,maxHealth,isDynamic,isSensor);
			xx.setUp(direction, speed, angle, lifeTime);
			monsters.add(xx);
		}
	}
	
	//add default item
	static public void addItems(String type, float x, float y) {
		
		if(type == "Heart") {
			Heart xx = new Heart(world, screen, x,y);
			items.add(xx);

		}
		else if(type == "Shield") {
			Shield xx = new Shield(world, screen, x,y);
			items.add(xx);
		}
		else if(type == "Strength") {
			Strength xx = new Strength(world, screen, x,y);
			items.add(xx);
		}
	}
	
	//add movement item
	static public void addItems(String type, float x, float y,int direction, float speed, float angle, float lifeTime) {
		if(type == "Heart") {
			Heart xx = new Heart(world, screen, x,y);
			xx.setUp(direction, speed, angle, lifeTime);
			items.add(xx);

		}
		else if(type == "Shield") {
			Shield xx = new Shield(world, screen, x,y);
			xx.setUp(direction, speed, angle, lifeTime);
			items.add(xx);
		}
		else if(type == "Strength") {
			Strength xx = new Strength(world, screen, x,y);
			xx.setUp(direction, speed, angle, lifeTime);
			items.add(xx);
		}
	}
	
	
	
	static public void removeItems(Item x) {
		itemsRemove.add(x);
	}
	
	static public void removeMonster(Monster x) {
		monstersRemove.add(x);
	}
	static public void clearMonster() {
		
		for(Monster x : monsters) {
			world.destroyBody(x.b2body);
		}
		
		for(Item x : items) {
			world.destroyBody(x.b2body);
		}
		
		monstersRemove.clear();
		monsters.clear();
		itemsRemove.clear();
		items.clear();
		isOnStage = false;
	}
	
	
	static public void Creator(TiledMap map, int y) {
		if(y == 8) {
			float posX = (y+1)*35-2;
			float posY = 3;
			addMonster("Boss", posX, posY,30,true,true);
		}
		
		int o = 11;
		isOnStage = true;
		if(o+y-1 >= map.getLayers().getCount()) return;
		MapGroupLayer xx = (MapGroupLayer) map.getLayers().get(o+y-1);
		
		if(xx.getLayers().get("Skeleton") != null)
		for(MapObject object : xx.getLayers().get("Skeleton").getObjects()) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			addMonster("Skeleton", ((rect.x+rect.width/2)/CuocChienSinhTon.PPM), ((rect.y+rect.height/2)/CuocChienSinhTon.PPM),10,true,false);
		}
		if(xx.getLayers().get("FlyingEye") != null)
		for(MapObject object : xx.getLayers().get("FlyingEye").getObjects()) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			addMonster("FlyingEye", ((rect.x+rect.width/2)/CuocChienSinhTon.PPM), ((rect.y+rect.height/2)/CuocChienSinhTon.PPM),5,false,false);
		}
		if(xx.getLayers().get("HellBeast") != null)
		for(MapObject object : xx.getLayers().get("HellBeast").getObjects()) {
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			addMonster("HellBeast", ((rect.x+rect.width/2)/CuocChienSinhTon.PPM), ((rect.y+rect.height/2)/CuocChienSinhTon.PPM),8,false,false);			
			
		}
		if(xx.getLayers().get("Heart") != null)
			for(MapObject object : xx.getLayers().get("Heart").getObjects()) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				addItems("Heart", ((rect.x+rect.width/2)/CuocChienSinhTon.PPM), ((rect.y+rect.height/2)/CuocChienSinhTon.PPM));							
			}
		
		if(xx.getLayers().get("Shield") != null)
			for(MapObject object : xx.getLayers().get("Shield").getObjects()) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				addItems("Shield", ((rect.x+rect.width/2)/CuocChienSinhTon.PPM), ((rect.y+rect.height/2)/CuocChienSinhTon.PPM));							
			}
		if(xx.getLayers().get("Strength") != null)
			for(MapObject object : xx.getLayers().get("Strength").getObjects()) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				addItems("Strength", ((rect.x+rect.width/2)/CuocChienSinhTon.PPM), ((rect.y+rect.height/2)/CuocChienSinhTon.PPM));							
			}
		
		
	}
}
