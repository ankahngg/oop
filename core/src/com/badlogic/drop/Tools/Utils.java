package com.badlogic.drop.Tools;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;

public class Utils {
	public static TextureRegion cutRegion(TextureRegion region,float x, float y, float width ,float height) {
//		TextureRegion newRegion = new TextureRegion(region.getTexture(), 0, 0, width, height);

		Texture originalTexture = region.getTexture();
		originalTexture.getTextureData().prepare();
	

		// Create a new Texture using the extracted region
		
		Pixmap pixmap = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
		Pixmap originalPixmap = originalTexture.getTextureData().consumePixmap();
		pixmap.drawPixmap(originalPixmap, 0, 0, (int)x, (int)y, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		pixmap.dispose();
		originalPixmap.dispose();
		return new TextureRegion(newTexture);
	}
//	public static Animation<TextureRegion> scaleAnimation(Animation<TextureRegion> animation){
//		Animation<TextureRegion> scaledAnimation;
//		Array<TextureRegion> newRegionSet = new Array<TextureRegion>();
//		for (TextureRegion region : animation.getKeyFrames()) {
//			TextureRegion region2 = region.
//			newRegionSet.add(region);
//		}
//		
//	}
}
