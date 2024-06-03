package com.badlogic.drop.Tools;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManagement {
	public static AssetManager manager;

	static public void setUp() {
		manager = new AssetManager();
		manager.load("Music/NhacMenu.mp3",Music.class);
		manager.load("Music/Map1Music.mp3",Music.class);
		manager.load("Music/Map2Music.mp3",Music.class);
		//manager.load("Music/Confirm.wav",Sound.class);
		manager.load("Music/footstep.mp3",Sound.class);
		manager.load("Music/Jump.wav",Sound.class);
		manager.finishLoading();
	}
}
