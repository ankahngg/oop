package com.badlogic.drop.Sprites;

import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectSet;

public class BulletManage {
	public static ObjectSet<Bullet> bullets =  new ObjectSet<Bullet>();
	static World world;
	static PlayScreen screen;
	
	public static void setup(World worldd, PlayScreen screenn) {
		world = worldd;
		screen = screenn;
	}
	
	public static void addBullet(String kind, float x, float y, int direction) {
		Bullet bl;
		if(kind == "FlyingEye") {
			bl = new EyeBullet(world,screen,x,y,direction);
			bullets.add(bl);
		}
	}
	
	public static void update(float dt) {
		for(Bullet bl : bullets) {
			bl.update(dt);
		}
	}
	
	public static void remove(Bullet bl) {
		System.out.println("wtfff");
		world.destroyBody(bl.b2body);
		bullets.remove(bl);
	}
}
