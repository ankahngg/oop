package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectSet;

public class FireManage {
	public ObjectSet<Fire> fireObjectSet = new ObjectSet<Fire>();
	
	public ObjectSet<Fire> removeObjectSet = new ObjectSet<Fire>();
	
	World world;
	PlayScreen playScreen;
	
	public FireManage(World world, PlayScreen playScreen) {
		this.world = world;
		this.playScreen = playScreen;
	}
	public void addFire(float x,float y) {
		Fire fire = new Fire(world, playScreen, x, y);
		fireObjectSet.add(fire);
	}
	public void update(float dt) {
		for(Fire fire : fireObjectSet) {
			fire.update(dt);
			if (fire.isFinished) removeObjectSet.add(fire);
		}
		if(!removeObjectSet.isEmpty() && !world.isLocked()) {
			for (Fire bl : removeObjectSet) 
				if (bl!=null) fireObjectSet.remove(bl);
			removeObjectSet.clear();
		}
	}
	public void removeFires(Fire fire) {
		removeObjectSet.add(fire);
	}
	public void clearBullet() {
		for(Fire x : fireObjectSet) {
			removeFires(x);
		}
	}

	
}
