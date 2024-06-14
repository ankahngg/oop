package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectSet;

public class BulletManage {
	public ObjectSet<Bullet> bullets =  new ObjectSet<Bullet>();

	public ObjectSet<Bullet> removeBullet = new ObjectSet<Bullet>();
	static World world;
	static PlayScreen screen;
	
	public BulletManage(World worldd, PlayScreen screenn) {
		world = worldd;
		screen = screenn;
	}
	
	public void clearBullet() {
		for(Bullet x : bullets) {
			removeBullet(x);
		}
	}

	// bullet default
	public void addBullet(String kind, float x, float y, int direction) {
		Bullet bl;
	
		if(kind == "FlyingEye") {
			bl = new EyeBullet(world,screen,x,y,direction);
			bullets.add(bl);
		}else if (kind.equals("EnergyBall")) {
			bl = new EnergyBall(world, screen, x, y, direction);
			bullets.add(bl);
		}else if (kind == "HeroBullet1") {
			bl = new HeroBullet1(world, screen, x, y, direction);
			bullets.add(bl);
		}
		else if (kind == "FireBullet") {
			bl = new FireBullet(world, screen, x, y, direction);
			bullets.add(bl);
		}else if(kind == "Thunder") {
			bl = new HeroBullet2(world, screen, x, y, direction);
			bullets.add(bl);
		}
		else if(kind == "BossBullet1") {
			bl = new BossBullet1(world, screen, x, y, direction);
			bullets.add(bl);
		}
		else if(kind == "BossBullet2") {
			bl = new BossBullet2(world, screen, x, y, direction);
			bullets.add(bl);
		}
		else if(kind == "BossBullet3") {
			bl = new BossBullet3(world, screen, x, y, direction);
			bullets.add(bl);
		}
	}
	
	// bullet with custom properties
	public void addBullet(String kind, float x, float y, int direction,float speed, float angleInDegrees, float lifeTime) {
		Bullet bl;
	
		if(kind == "FlyingEye") {
			bl = new EyeBullet(world,screen,x,y,direction);
			
			bullets.add(bl);
		}else if (kind.equals("EnergyBall")) {
			bl = new EnergyBall(world, screen, x, y, direction);
			bullets.add(bl);
		}else if (kind == "HeroBullet1") {
			bl = new HeroBullet1(world, screen, x, y, direction);
			bullets.add(bl);
		}else if(kind == "Thunder") {
			bl = new HeroBullet2(world, screen, x, y, direction);
			bullets.add(bl);
		}
		else if (kind == "FireBullet") {
			bl = new FireBullet(world, screen, x, y, direction);
			bullets.add(bl);
		}
		else if(kind == "BossBullet1") {
			bl = new BossBullet1(world, screen, x, y, direction);
			bullets.add(bl);
		}
		else if(kind == "BossBullet2") {
			bl = new BossBullet2(world, screen, x, y, direction);
			bullets.add(bl);
		}
		else if(kind == "BossBullet3") {
			bl = new BossBullet3(world, screen, x, y, direction);
			bullets.add(bl);
		}
		else {
			bl = new HeroBullet1(world, screen, x, y, direction);
		}
		bl.setUp(direction, speed, angleInDegrees, lifeTime);
		
	}
	
	public float getAngle(float x1,float y1, float x2, float y2) {
		float yWidth = y2-y1 ;
		float xWidth = x2-x1;
		return (float) Math.toDegrees(Math.atan(-yWidth/xWidth));
	}
	

	public void update(float dt) {
		for(Bullet bl : bullets) {
			bl.update(dt);
		}

		if(!removeBullet.isEmpty() && !world.isLocked()) {
			for (Bullet bl : removeBullet) 
				if (bl!=null) bullets.remove(bl);
			removeBullet.clear();
		}
	}
	
	public void removeBullet(Bullet bl) {
		removeBullet.add(bl);
	}
	
}
	

