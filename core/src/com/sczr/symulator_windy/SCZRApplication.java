package com.sczr.symulator_windy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.sczr.symulator_windy.ui.SkinAtlas;

public class SCZRApplication extends ApplicationAdapter {
	public static int windowWidth, windowHeight;
	public SkinAtlas skinAtlas;
	
	ConnectionStage connectionStage;
	MainStage mainStage;
	Stage currentStage;
	
	
	public SCZRApplication(int windowWidth, int windowHeight) {
		SCZRApplication.windowWidth = windowWidth;
		SCZRApplication.windowHeight = windowHeight;
	}
	
	@Override
	public void create () {
		skinAtlas = new SkinAtlas();
		connectionStage = new ConnectionStage(this);
		mainStage = new MainStage(skinAtlas.getSkin());
		
		setStage(connectionStage);
		//setMainStage(); //tu powinno byc connection stage ale na czas debugu to wylaczam bo wkurwia wpisywanie cyferek za kazdym razem
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
        Vector2 size = Scaling.fit.apply(windowWidth, windowHeight, width, height);
        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;
        //Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        currentStage.getViewport().update(viewportWidth, viewportHeight);	
	}
	
	public void setMainStage(){
		setStage(mainStage);
	}
	
	private void setStage(Stage stage){
		this.currentStage = stage;
		Gdx.input.setInputProcessor(stage);
	}
	

}
