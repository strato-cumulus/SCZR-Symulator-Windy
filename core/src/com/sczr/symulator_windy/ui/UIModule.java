package com.sczr.symulator_windy.ui;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.sczr.symulator_windy.packets.GUIpackets.GUIRegisterPacket;
import com.sczr.symulator_windy.packets.GUIpackets.InitializeGUIPacket;
import com.sczr.symulator_windy.serialization.SerializationList;


public class UIModule
{
	Client client;
	private final SkinAtlas skinAtlas;
	private final ConnectionStage connectionStage;
	private final MainStage mainStage;
	private Stage currentStage;
	
	public final int windowWidth;
	public final int windowHeight;
		
	public UIModule(int tcpPort, int windowWidth, int windowHeight) throws IOException
	{
		this.client = new Client();
		client.start();
		try {
			client.connect(50, "127.0.0.1", 1234);
		} catch (IOException e) { e.printStackTrace(); }
		
		SerializationList.register(client.getKryo());
		client.sendTCP(new GUIRegisterPacket());
		
		ArrayBlockingQueue<InitializeGUIPacket> bq = new ArrayBlockingQueue<>(1);
		
		Listener l = new Listener() {
			@Override public void received(Connection c, Object o) {
				if(o instanceof InitializeGUIPacket) {
					InitializeGUIPacket packet = (InitializeGUIPacket)o;
					try{bq.put(packet);}catch(InterruptedException e){}
				}
			}
		};
		
		client.addListener(l);
		
		InitializeGUIPacket packet = bq.poll();
		System.out.println(packet.storeyHeight + " " + packet.storeyNumber);
		client.removeListener(l);
		
		this.skinAtlas = new SkinAtlas();
		this.mainStage = new MainStage(skinAtlas.getSkin(), this);
		this.connectionStage = new ConnectionStage(skinAtlas, this);
		
		client.addListener(mainStage.listener);
		SerializationList.register(client.getKryo());
		
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
