package com.badlogic.drop.Scenes;

import com.badlogic.drop.CuocChienSinhTon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
	public Stage stage;
	private Viewport viewport;
	
	private Integer worldTimer;
	private float timeCount;
	private Integer score;
	
	Label countdownLabel;
	Label scoreLabel;
	Label levelLabel;
	Label worldLabel;
	Label marioLabel;
	
	public Hud(SpriteBatch sb) {
		
		viewport = new FitViewport(CuocChienSinhTon.V_WIDTH/CuocChienSinhTon.PPM, CuocChienSinhTon.V_HEIGHT/CuocChienSinhTon.PPM, new OrthographicCamera());
		stage = new Stage(viewport,sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		countdownLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		scoreLabel = new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		 
		table.add(countdownLabel).expandX().padTop(10);
		table.row();
		table.add(scoreLabel).expandX();
		
		stage.addActor(table);
	}
}
