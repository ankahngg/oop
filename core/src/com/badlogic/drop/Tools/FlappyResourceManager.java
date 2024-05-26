package com.badlogic.drop.Tools;

import java.util.LinkedList;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.drop.Sprites.Monster;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveAction;

public class FlappyResourceManager {
	private  LinkedList<Monster> monsters;
	private LinkedList<Monster> markRemoveMonsters;
	private  LinkedList<Item> items;
	private  LinkedList<Item> markRemovedItems;
	private World world;
	private PlayScreen screen;
	public boolean isDisposed = false;
	public FlappyResourceManager(World world, PlayScreen screen) {
		monsters= new LinkedList<Monster>();
		items = new LinkedList<Item>();
		markRemovedItems = new LinkedList<Item>();
		markRemoveMonsters = new LinkedList<Monster>();
		this.world = world;
		this.screen = screen;
	}

	public  void addItems(Item item) {

		items.add(item);
	}
	public void addMonster(Monster monster) {

		monsters.add(monster);
	}
	public void update(float dt) {
		
		//update monster
		for (int i = 0; i< monsters.size();i++) {
			Monster monster = monsters.get(i);
			if(FlappyMap.isMonstetOutOfScreen(monster,screen.getPlayer().getX()-10)) removeMonster(monster);
			if (monster.getX()<screen.getPlayer().getX()+CuocChienSinhTon.V_WIDTH/CuocChienSinhTon.PPM) {
				monster.update(dt);
			}
			if(monster.isDied) {
				monsters.remove(i);
			}
			if (monster.getX()<screen.getCamera().position.x-screen.getGamePort().getWorldWidth()) {
				monsters.remove(monster);
				
			}
		}
		// items
		for (Item item : items) {
			item.update(dt);
		}
		for (Item item : markRemovedItems) {
			world.destroyBody(item.b2body);
			items.remove(item);
		}
		markRemovedItems.clear();
		
	}
	public void removeItem(Item item) {
		markRemovedItems.add(item);
	}
	public void removeAll() {
		for (Monster monster : monsters) {
			removeMonster(monster);
		}
		for (Monster monster : markRemoveMonsters) {
			screen.world.destroyBody(monster.b2body);
			monster.b2body =null;
			monsters.remove(monster);
		}
		for (Item item : items) {
			removeItem(item);
		}
		for (Item item : markRemovedItems) {
			world.destroyBody(item.b2body);
			item.b2body=null;
			items.remove(item);
		}
		markRemovedItems.clear();
		markRemoveMonsters.clear();
	}
	
	public void removeMonster(Monster monster) {
		markRemoveMonsters.add(monster);
	}
	public void dispose() {
		isDisposed = true;
		markRemovedItems.clear();
		markRemoveMonsters.clear();


	}
}
