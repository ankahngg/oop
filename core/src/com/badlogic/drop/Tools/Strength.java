package com.badlogic.drop.Tools;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Strength extends Items{
	public Strength(World worldd, PlayScreen screenn,float x, float y) {
		
		super(worldd, screenn, x, y);
		//this.texture = new Texture("Items/Heart.png");
	}

	@Override
	public void prepairTexture() {
		this.ItemScaleX = this.ItemScaleY = 0.5f;
		this.texture = new Texture("Items/Strength.png");
		setRegion(texture);
		regionH = getRegionHeight()/CuocChienSinhTon.PPM*ItemScaleX;
		regionW = getRegionHeight()/CuocChienSinhTon.PPM*ItemScaleY;
	}

	@Override
	public void effect() {
		
		screen.getPlayer().damage += 1;
		((FirstMap) screen).StageCreator.itemsRemove.add(this);
		
	}
}
