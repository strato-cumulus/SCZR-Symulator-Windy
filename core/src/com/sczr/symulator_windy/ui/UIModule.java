package com.sczr.symulator_windy.ui;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.DispatchElevatorPacket;
import com.sczr.symulator_windy.packets.Packet;

public class UIModule
{
	private final SkinAtlas       skinAtlas;
	private final ConnectionStage connectionStage;
	private final MainStage       mainStage;
	private Stage                 currentStage;
	
	public final int              windowWidth;
	public final int              windowHeight;
	
	public UIModule(int tcpPort, int windowWidth, int windowHeight) throws IOException
	{
		this.skinAtlas = new SkinAtlas();
		this.connectionStage = new ConnectionStage(skinAtlas, this);
		this.mainStage = new MainStage(skinAtlas.getSkin(), this);
		this.setStage(mainStage);
		
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}
	
	void setMainStage()
	{
		setStage(mainStage);
	}
	
	private void setStage(Stage stage)
	{
		this.currentStage = stage;
		Gdx.input.setInputProcessor(stage);
	}
	
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    currentStage.act(Gdx.graphics.getDeltaTime());
	    currentStage.draw();
	}
	
	public void resize(int width, int height)
	{
        Vector2 size = Scaling.fit.apply(windowWidth, windowHeight, width, height);
        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;
        //Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        currentStage.getViewport().update(viewportWidth, viewportHeight);
	}
}
