package com.badlogic.drop.Tools;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManagement {
	public static AssetManager manager;
	
	public static Music lastMusic;
//	public static String nhacMenu = "Music/NhacMenu.mp3";
//	public static String map1Music = "Music/Map1Music.mp3";
//	public static String map2Music = "Music/Map2Music.mp3";
	
	public static String nhacMenu = "Music/NhacMenu.mp3";
	public static String map1Music = "Music/Exploring The Unknown.wav";
	public static String map2Music = "Music/Prepare for Battle! .wav";
	public static String pauseMusic = "Music/Take some rest and eat some food!.wav";
	public static String gameOverMusic = "Music/GameOver.mp3";
	
	public static String heroBulletSound = "Music/Laze.wav";
	public static String heroSwordSound = "Music/sword2.wav";
	public static String heroHurtSound = "Music/heroHurt.wav";
	public static String monsterHurtSound = "sound/hero/heroHurt.wav";
	public static String collectItemSound = "Music/CollectItem.wav";
	static public void setUp() {
		manager = new AssetManager();
		manager.load(nhacMenu,Music.class);
		manager.load(map1Music,Music.class);
		manager.load(map2Music,Music.class);
		manager.load(pauseMusic,Music.class);
		manager.load(gameOverMusic,Music.class);
		//manager.load("Music/Confirm.wav",Sound.class);
		manager.load("Music/footstep.mp3",Sound.class);
		manager.load("Music/Jump.wav",Sound.class);
		manager.load(heroBulletSound,Sound.class);
		manager.load(heroSwordSound,Sound.class);
		manager.load(heroHurtSound,Sound.class);
		manager.load(monsterHurtSound,Sound.class);
		manager.load(collectItemSound,Sound.class);
		manager.finishLoading();
	}
	static public void setLastMusic(Music music) {
		lastMusic = music;
	}
}
