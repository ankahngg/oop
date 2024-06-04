package com.badlogic.drop.Tools;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.drop.Screens.FirstMap;
import com.badlogic.drop.Screens.FlappyMap;
import com.badlogic.drop.Screens.PlayScreen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class Heart extends Item{
	public Heart(World worldd, PlayScreen screenn,float x, float y) {
		
		super(worldd, screenn, x, y);
		//this.texture = new Texture("Items/Heart.png");
	}

	@Override
	public void prepairTexture() {
		this.ItemScaleX = this.ItemScaleY = 0.5f;
		this.texture = new Texture("Items/Heart.png");
		setRegion(texture);
		regionH = getRegionHeight()/CuocChienSinhTon.PPM*ItemScaleX;
		regionW = getRegionHeight()/CuocChienSinhTon.PPM*ItemScaleY;
	}

	@Override
	public void effect() {
		AudioManagement.manager.get(AudioManagement.collectItemSound,Sound.class).play();

		isDied = true;
		screen.getPlayer().Health = Math.min(screen.getPlayer().getHealthMax(), screen.getPlayer().getHealth()+2);
		
	}
}
