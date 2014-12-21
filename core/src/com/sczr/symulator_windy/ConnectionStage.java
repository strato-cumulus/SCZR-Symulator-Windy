package com.sczr.symulator_windy;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;

public class ConnectionStage extends Stage {
	SCZRApplication app;
	ShapeRenderer shapeRenderer;
	Table table;
	Skin skin;
	
	TextButton connectButton;
	TextField portField;
	
	public ConnectionStage(SCZRApplication app) {
		super();
		this.app = app;
		skin = app.skinAtlas.getSkin();
		shapeRenderer = new ShapeRenderer();
		table = new Table();
		table.setFillParent(true);		
		
		connectButton = new TextButton("polacz", skin);
		portField = new TextField("", skin);
		portField.setMaxLength(4);
		portField.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
		
		table.add(connectButton);
		table.add(new Label("port: ", skin));
		table.add(portField).width(35);		
		addActor(table);
		
		addListeners();
	}
	
	@Override
	public void draw(){
		super.draw();
		table.drawDebug(shapeRenderer);	
	}
	
	private void addListeners(){
		connectButton.addListener(new InputListener(){
		    @Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		    	if(portField.getText().equals(""))
		    		return false;
		        int port = Integer.parseInt(portField.getText());
		        System.out.println(port);	//TODO polaczenie 
		        
		        //if(polaczenie==ok)
		        app.setMainStage();
		        return true;
		    }
		});
		
		
	}
	
	
}
	
	
	
	





