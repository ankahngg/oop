package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectSet;

public class BulletManage {
	public static ObjectSet<Bullet> bullets =  new ObjectSet<Bullet>();

	public static ObjectSet<Bullet> removeBullet = new ObjectSet<Bullet>();
	static World world;
	static PlayScreen screen;
	
	public static void setup(World worldd, PlayScreen screenn) {
		world = worldd;
		screen = screenn;
	}
	
	// bullet default
	public static void addBullet(String kind, float x, float y, int direction) {
		Bullet bl;
	
		if(kind == "FlyingEye") {
			bl = new EyeBullet(world,screen,x,y,direction);
			bullets.add(bl);
		}else if (kind.equals("EnergyBall")) {
			bl = new EnergyBall(world, screen, x, y, direction);
			bullets.add(bl);
		}else if (kind == "HeroBullet1") {
			System.out.println("bullet");
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
	public static void addBullet(String kind, float x, float y, int direction,float speed, float angleInDegrees, float lifeTime) {
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
	

	public static void update(float dt) {
		for(Bullet bl : bullets) {
			bl.update(dt);
		}

		for (Bullet bl : removeBullet) {
			if (bl!=null) {
				world.destroyBody(bl.b2body);
				bl.b2body=null;
				bullets.remove(bl);
			}	
		}

		removeBullet.clear();
	}
	
	public static void removeBullet(Bullet bl) {
		System.out.println("remove");
		removeBullet.add(bl);
	}
	
}
	

