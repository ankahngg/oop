package com.badlogic.drop.Scenes;

import com.badlogic.drop.Drop;
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
		worldTimer = 300;
		timeCount = 0;
		score = 0;
		
		viewport = new FitViewport(Drop.V_WIDTH, Drop.V_HEIGHT, new OrthographicCamera());
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
