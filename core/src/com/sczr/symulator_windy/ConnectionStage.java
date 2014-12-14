package com.sczr.symulator_windy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

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
		shapeRenderer = new ShapeRenderer();
		table = new Table();
		table.setFillParent(true);

		skin = new Skin();
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		skin.add("default", new BitmapFont());


		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.GREEN);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.background = skin.newDrawable("white", Color.WHITE);
		textFieldStyle.cursor = skin.newDrawable("white", Color.DARK_GRAY);
		textFieldStyle.disabledBackground = skin.newDrawable("white", Color.LIGHT_GRAY);
		textFieldStyle.selection = skin.newDrawable("white", Color.PURPLE);
		textFieldStyle.font = new BitmapFont();
		textFieldStyle.fontColor = Color.BLACK;
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = new BitmapFont();
		labelStyle.fontColor = Color.WHITE;
		
		skin.add("default", textFieldStyle);
		skin.add("default", labelStyle);
		
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
	
	
	
	





