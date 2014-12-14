package com.sczr.symulator_windy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SCZRApplication extends ApplicationAdapter {
	ConnectionStage connectionStage;
	MainStage mainStage;
	Stage currentStage;
	
	
	@Override
	public void create () {
		connectionStage = new ConnectionStage(this);
		mainStage = new MainStage();
		setStage(connectionStage);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    currentStage.act(Gdx.graphics.getDeltaTime());
	    currentStage.draw();
	}
	
	@Override
	public void resize(int width, int height){
		currentStage.getViewport().update(width, height);
	}
	
	public void setMainStage(){
		setStage(mainStage);
	}
	
	private void setStage(Stage stage){
		this.currentStage = stage;
		Gdx.input.setInputProcessor(stage);
	}
	

}
